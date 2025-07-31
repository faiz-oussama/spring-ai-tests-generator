package com.univade.ai.application.usecase;

import com.univade.ai.application.service.TestGenerationService;
import com.univade.ai.domain.model.PromptContext;
import com.univade.ai.domain.model.TestGenerationResult;
import com.univade.ai.domain.value.TargetLayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GenerateTestsUseCase {

    private static final Logger logger = LoggerFactory.getLogger(GenerateTestsUseCase.class);

    private final TestGenerationService testGenerationService;

    public GenerateTestsUseCase(TestGenerationService testGenerationService) {
        this.testGenerationService = testGenerationService;
    }

    public TestGenerationResult execute(PromptContext context) {
        logger.info("Executing test generation use case for user input: {}", context.getUserInput());

        validateContext(context);

        return testGenerationService.generateTests(context);
    }

    public TestGenerationResult generateFromUserInput(String userInput) {
        logger.info("Generating tests for user input: {}", userInput);

        validateUserInput(userInput);

        PromptContext context = testGenerationService.buildContext(userInput, null);

        return testGenerationService.generateTests(context);
    }

    public TestGenerationResult refineTests(String sessionId, String refinementPrompt) {
        logger.info("Refining tests for session: {}", sessionId);

        validateRefinementInputs(sessionId, refinementPrompt);

        return testGenerationService.refineExistingTests(sessionId, refinementPrompt);
    }

    private void validateContext(PromptContext context) {
        if (context == null) {
            throw new IllegalArgumentException("PromptContext cannot be null");
        }

        if (context.getUserInput() == null || context.getUserInput().trim().isEmpty()) {
            throw new IllegalArgumentException("User input cannot be null or empty");
        }
    }

    private void validateUserInput(String userInput) {
        if (userInput == null || userInput.trim().isEmpty()) {
            throw new IllegalArgumentException("User input cannot be null or empty");
        }
    }

    private void validateRefinementInputs(String sessionId, String refinementPrompt) {
        if (sessionId == null || sessionId.trim().isEmpty()) {
            throw new IllegalArgumentException("Session ID cannot be null or empty");
        }

        if (refinementPrompt == null || refinementPrompt.trim().isEmpty()) {
            throw new IllegalArgumentException("Refinement prompt cannot be null or empty");
        }
    }
}
