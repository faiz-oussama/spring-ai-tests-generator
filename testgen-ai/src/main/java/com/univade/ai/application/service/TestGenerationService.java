package com.univade.ai.application.service;

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

    public TestGenerationService(OpenAiClient openAiClient,
                               PromptLoader promptLoader,
                               PromptRefiner promptRefiner,
                               MemoryRepository memoryRepository,
                               TestFileManager testFileManager,
                               TestResultProcessor testResultProcessor) {
        this.openAiClient = openAiClient;
        this.promptLoader = promptLoader;
        this.promptRefiner = promptRefiner;
        this.memoryRepository = memoryRepository;
        this.testFileManager = testFileManager;
        this.testResultProcessor = testResultProcessor;
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
}
