package com.univade.ai.domain.service;

import com.univade.ai.domain.model.PromptContext;
import org.springframework.stereotype.Service;

@Service
public class PromptRefiner {

    public String buildUserPrompt(PromptContext context) {
        return "User Request: " + context.getUserInput() + "\n\n" +
               "Generate comprehensive unit tests based on this request. " +
               "Determine the appropriate component type and create production-ready tests " +
               "using JUnit 5, Mockito, and AssertJ. Follow the JSON response format " +
               "specified in the system prompt.";
    }

    public String refinePrompt(PromptContext context, String refinementInstructions) {
        return "Refine the previously generated tests based on this feedback: " +
               refinementInstructions + "\n\n" +
               "Original request: " + context.getUserInput() + "\n\n" +
               "Maintain the same JSON response format.";
    }
}
