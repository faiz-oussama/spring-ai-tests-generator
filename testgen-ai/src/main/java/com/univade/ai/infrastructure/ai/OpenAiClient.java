package com.univade.ai.infrastructure.ai;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.univade.ai.domain.model.TestGenerationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

@Component
public class OpenAiClient {

    private static final Logger logger = LoggerFactory.getLogger(OpenAiClient.class);

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;

    public OpenAiClient(ChatClient chatClient,
                       ObjectMapper objectMapper) {
        this.chatClient = chatClient;
        this.objectMapper = objectMapper;
    }

    public TestGenerationResult generateTests(String systemPrompt, String userPrompt) {
        try {
            String response = chatClient.prompt()
                .system(systemPrompt)
                .user(userPrompt)
                .call()
                .content();

            if (response == null || response.trim().isEmpty()) {
                return createErrorResult("Empty response from AI");
            }

            return parseAiResponse(response);

        } catch (Exception e) {
            logger.error("Error generating tests with AI", e);
            return createErrorResult("Failed to generate tests: " + e.getMessage());
        }
    }

    public TestGenerationResult refineTests(String systemPrompt, String refinementPrompt) {
        try {
            String response = chatClient.prompt()
                .system(systemPrompt)
                .user(refinementPrompt)
                .call()
                .content();

            if (response == null || response.trim().isEmpty()) {
                return createErrorResult("Empty response from AI");
            }

            return parseAiResponse(response);

        } catch (Exception e) {
            logger.error("Error refining tests with AI", e);
            return createErrorResult("Failed to refine tests: " + e.getMessage());
        }
    }

    public TestGenerationResult generateTestsWithMemory(String systemPrompt, String userPrompt, String conversationId) {
        try {
            String response = chatClient.prompt()
                .system(systemPrompt)
                .user(userPrompt)
                .advisors(advisorSpec -> advisorSpec.param("conversationId", conversationId))
                .call()
                .content();

            if (response == null || response.trim().isEmpty()) {
                return createErrorResult("Empty response from AI");
            }

            return parseAiResponse(response);

        } catch (Exception e) {
            logger.error("Error generating tests with memory for conversation: {}", conversationId, e);
            return createErrorResult("Failed to generate tests: " + e.getMessage());
        }
    }

    public TestGenerationResult refineTestsWithMemory(String systemPrompt, String refinementPrompt, String conversationId) {
        try {
            String response = chatClient.prompt()
                .system(systemPrompt)
                .user(refinementPrompt)
                .advisors(advisorSpec -> advisorSpec.param("conversationId", conversationId))
                .call()
                .content();

            if (response == null || response.trim().isEmpty()) {
                return createErrorResult("Empty response from AI");
            }

            return parseAiResponse(response);

        } catch (Exception e) {
            logger.error("Error refining tests with memory for conversation: {}", conversationId, e);
            return createErrorResult("Failed to refine tests: " + e.getMessage());
        }
    }

    private TestGenerationResult parseAiResponse(String response) {
        try {
            String jsonResponse = extractJsonFromResponse(response);

            if (jsonResponse == null) {
                return createErrorResult("Invalid response format from AI");
            }

            return objectMapper.readValue(jsonResponse, TestGenerationResult.class);

        } catch (JsonProcessingException e) {
            logger.error("Error parsing AI response JSON", e);
            return createErrorResult("Failed to parse AI response: " + e.getMessage());
        }
    }

    private String extractJsonFromResponse(String response) {
        int jsonStart = response.indexOf("{");
        if (jsonStart == -1) {
            return null;
        }

        int braceCount = 0;
        int jsonEnd = -1;

        for (int i = jsonStart; i < response.length(); i++) {
            char c = response.charAt(i);
            if (c == '{') {
                braceCount++;
            } else if (c == '}') {
                braceCount--;
                if (braceCount == 0) {
                    jsonEnd = i;
                    break;
                }
            }
        }

        if (jsonEnd == -1) {
            return null;
        }

        return response.substring(jsonStart, jsonEnd + 1);
    }

    private TestGenerationResult createErrorResult(String errorMessage) {
        TestGenerationResult result = new TestGenerationResult();
        result.setStatus("ERROR");
        result.setErrorMessage(errorMessage);
        return result;
    }
}
