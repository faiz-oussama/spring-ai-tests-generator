package com.univade.ai.application.service;

import com.univade.ai.domain.model.ConversationContext;
import com.univade.ai.domain.model.ConversationMessage;
import com.univade.ai.domain.model.PromptContext;
import com.univade.ai.domain.model.TestGenerationResult;
import com.univade.ai.domain.repository.MemoryRepository;
import com.univade.ai.domain.service.PromptRefiner;
import com.univade.ai.domain.service.TestResultProcessor;
import com.univade.ai.infrastructure.ai.OpenAiClient;
import com.univade.ai.infrastructure.file.TestFileManager;
import com.univade.ai.infrastructure.prompt.PromptLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TestGenerationService {

    private static final Logger logger = LoggerFactory.getLogger(TestGenerationService.class);

    private final OpenAiClient openAiClient;
    private final PromptLoader promptLoader;
    private final PromptRefiner promptRefiner;
    private final MemoryRepository memoryRepository;
    private final TestFileManager testFileManager;
    private final TestResultProcessor testResultProcessor;
    private final ConversationService conversationService;

    public TestGenerationService(OpenAiClient openAiClient,
                               PromptLoader promptLoader,
                               PromptRefiner promptRefiner,
                               MemoryRepository memoryRepository,
                               TestFileManager testFileManager,
                               TestResultProcessor testResultProcessor,
                               ConversationService conversationService) {
        this.openAiClient = openAiClient;
        this.promptLoader = promptLoader;
        this.promptRefiner = promptRefiner;
        this.memoryRepository = memoryRepository;
        this.testFileManager = testFileManager;
        this.testResultProcessor = testResultProcessor;
        this.conversationService = conversationService;
    }

    public TestGenerationResult generateTests(PromptContext context) {
        try {
            if (context.getSessionId() == null || context.getSessionId().trim().isEmpty()) {
                context.setSessionId(UUID.randomUUID().toString());
            }

            memoryRepository.save(context.getSessionId(), context);

            String systemPrompt = promptLoader.loadSystemPrompt();
            String userPrompt = promptRefiner.buildUserPrompt(context);

            TestGenerationResult result = openAiClient.generateTests(systemPrompt, userPrompt);
            result.setSessionId(context.getSessionId());

            if ("SUCCESS".equals(result.getStatus()) && testResultProcessor.isValidTestResult(result)) {
                try {
                    testFileManager.saveGeneratedTest(result);
                } catch (Exception e) {
                    logger.warn("Failed to save test file for session: {}", context.getSessionId(), e);
                }
            }

            return result;

        } catch (Exception e) {
            logger.error("Error generating tests", e);
            TestGenerationResult errorResult = new TestGenerationResult(context.getSessionId());
            errorResult.setStatus("ERROR");
            errorResult.setErrorMessage("Failed to generate tests: " + e.getMessage());
            return errorResult;
        }
    }

    public List<TestGenerationResult> generateMultipleTests(List<PromptContext> contexts) {
        return contexts.stream()
                .map(this::generateTests)
                .collect(Collectors.toList());
    }

    public TestGenerationResult refineExistingTests(String sessionId, String refinementInstructions) {
        try {
            Optional<PromptContext> contextOpt = memoryRepository.findBySessionId(sessionId);
            if (contextOpt.isEmpty()) {
                TestGenerationResult errorResult = new TestGenerationResult(sessionId);
                errorResult.setStatus("ERROR");
                errorResult.setErrorMessage("Session not found: " + sessionId);
                return errorResult;
            }

            PromptContext context = contextOpt.get();
            String systemPrompt = promptLoader.loadSystemPrompt();
            String refinementPrompt = promptRefiner.refinePrompt(context, refinementInstructions);

            TestGenerationResult result = openAiClient.refineTests(systemPrompt, refinementPrompt);
            result.setSessionId(sessionId);

            if ("SUCCESS".equals(result.getStatus()) && testResultProcessor.isValidTestResult(result)) {
                try {
                    testFileManager.saveGeneratedTest(result);
                } catch (Exception e) {
                    logger.warn("Failed to save refined test file for session: {}", sessionId, e);
                }
            }

            return result;

        } catch (Exception e) {
            logger.error("Error refining tests for session: {}", sessionId, e);
            TestGenerationResult errorResult = new TestGenerationResult(sessionId);
            errorResult.setStatus("ERROR");
            errorResult.setErrorMessage("Failed to refine tests: " + e.getMessage());
            return errorResult;
        }
    }

    public PromptContext buildContext(String userInput) {
        return new PromptContext(UUID.randomUUID().toString(), userInput);
    }

    public PromptContext buildContext(String userInput, String classSourceCode) {
        return new PromptContext(UUID.randomUUID().toString(), userInput, classSourceCode);
    }

    public Optional<PromptContext> getContext(String sessionId) {
        return memoryRepository.findBySessionId(sessionId);
    }

    public void deleteSession(String sessionId) {
        memoryRepository.deleteBySessionId(sessionId);
    }

    public TestGenerationResult generateTestsWithMemory(PromptContext context) {
        try {
            if (context.getSessionId() == null || context.getSessionId().trim().isEmpty()) {
                context.setSessionId(UUID.randomUUID().toString());
            }

            boolean isNewConversation = false;
            if (context.getConversationId() == null && context.isUseConversationMemory()) {
                String conversationId = conversationService.startConversation("default-user", context.getSessionId());
                context.setConversationId(conversationId);
                isNewConversation = true;
            }

            memoryRepository.save(context.getSessionId(), context);

            String systemPrompt = promptLoader.loadSystemPrompt();
            String userPrompt = promptRefiner.buildUserPrompt(context);

            TestGenerationResult result;
            if (context.shouldUseConversationMemory()) {
                result = openAiClient.generateTestsWithMemory(systemPrompt, userPrompt, context.getConversationId());

                conversationService.incrementMessageCount(context.getConversationId());

                context.addToHistory(ConversationMessage.userMessage(context.getConversationId(), userPrompt));
                if (result.getGeneratedTest() != null) {
                    context.addToHistory(ConversationMessage.assistantMessage(context.getConversationId(),
                            "Generated test class: " + result.getTestClass().getClassName()));
                }
            } else {
                result = openAiClient.generateTests(systemPrompt, userPrompt);
            }

            result.setSessionId(context.getSessionId());
            if (context.getConversationId() != null) {
                result.setConversationId(context.getConversationId());
            }
            result.setNewConversation(isNewConversation);

            if ("SUCCESS".equals(result.getStatus()) && testResultProcessor.isValidTestResult(result)) {
                try {
                    testFileManager.saveGeneratedTest(result);
                } catch (Exception e) {
                    logger.warn("Failed to save test file for session: {}", context.getSessionId(), e);
                }
            }

            return result;

        } catch (Exception e) {
            logger.error("Error generating tests with memory", e);
            TestGenerationResult errorResult = new TestGenerationResult(context.getSessionId());
            errorResult.setStatus("ERROR");
            errorResult.setErrorMessage("Failed to generate tests: " + e.getMessage());
            return errorResult;
        }
    }

    public TestGenerationResult continueConversation(String conversationId, String userInput) {
        try {
            Optional<ConversationContext> contextOpt = conversationService.getConversationContext(conversationId);
            if (contextOpt.isEmpty()) {
                TestGenerationResult errorResult = new TestGenerationResult();
                errorResult.setStatus("ERROR");
                errorResult.setErrorMessage("Conversation not found: " + conversationId);
                return errorResult;
            }

            ConversationContext conversationContext = contextOpt.get();
            PromptContext promptContext = new PromptContext(conversationContext.getSessionId(),
                    conversationId, userInput, null);
            promptContext.setUseConversationMemory(true);

            return generateTestsWithMemory(promptContext);

        } catch (Exception e) {
            logger.error("Error continuing conversation: {}", conversationId, e);
            TestGenerationResult errorResult = new TestGenerationResult();
            errorResult.setStatus("ERROR");
            errorResult.setErrorMessage("Failed to continue conversation: " + e.getMessage());
            return errorResult;
        }
    }

    public TestGenerationResult refineTestsWithMemory(String conversationId, String refinementInstructions) {
        try {
            Optional<ConversationContext> contextOpt = conversationService.getConversationContext(conversationId);
            if (contextOpt.isEmpty()) {
                TestGenerationResult errorResult = new TestGenerationResult();
                errorResult.setStatus("ERROR");
                errorResult.setErrorMessage("Conversation not found: " + conversationId);
                return errorResult;
            }

            ConversationContext conversationContext = contextOpt.get();

            Optional<PromptContext> promptContextOpt = memoryRepository.findBySessionId(conversationContext.getSessionId());
            if (promptContextOpt.isEmpty()) {
                TestGenerationResult errorResult = new TestGenerationResult();
                errorResult.setStatus("ERROR");
                errorResult.setErrorMessage("Session context not found: " + conversationContext.getSessionId());
                return errorResult;
            }

            PromptContext context = promptContextOpt.get();
            String systemPrompt = promptLoader.loadSystemPrompt();
            String refinementPrompt = promptRefiner.refinePrompt(context, refinementInstructions);

            TestGenerationResult result = openAiClient.refineTestsWithMemory(systemPrompt, refinementPrompt, conversationId);
            result.setSessionId(conversationContext.getSessionId());
            result.setConversationId(conversationId);

            // Track conversation metrics
            conversationService.incrementMessageCount(conversationId);

            context.addToHistory(ConversationMessage.userMessage(conversationId, refinementInstructions));
            if (result.getGeneratedTest() != null) {
                context.addToHistory(ConversationMessage.assistantMessage(conversationId,
                        "Refined test class: " + result.getTestClass().getClassName()));
            }

            if ("SUCCESS".equals(result.getStatus()) && testResultProcessor.isValidTestResult(result)) {
                try {
                    testFileManager.saveGeneratedTest(result);
                } catch (Exception e) {
                    logger.warn("Failed to save refined test file for conversation: {}", conversationId, e);
                }
            }

            return result;

        } catch (Exception e) {
            logger.error("Error refining tests with memory for conversation: {}", conversationId, e);
            TestGenerationResult errorResult = new TestGenerationResult();
            errorResult.setStatus("ERROR");
            errorResult.setErrorMessage("Failed to refine tests: " + e.getMessage());
            return errorResult;
        }
    }

    public PromptContext buildConversationContext(String userInput, String classSourceCode, boolean useMemory) {
        String sessionId = UUID.randomUUID().toString();
        PromptContext context = new PromptContext(sessionId, null, userInput, classSourceCode);
        context.setUseConversationMemory(useMemory);
        return context;
    }

    public Optional<ConversationContext> getConversationContext(String conversationId) {
        return conversationService.getConversationContext(conversationId);
    }

    public void endConversation(String conversationId) {
        conversationService.endConversation(conversationId);
    }
}
