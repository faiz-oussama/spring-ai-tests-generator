package com.univade.ai.domain.service;

import com.univade.ai.domain.model.PromptContext;
import org.springframework.stereotype.Service;

@Service
public class PromptRefiner {

    public String buildUserPrompt(PromptContext context) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("User Request: ").append(context.getUserInput()).append("\n\n");

        if (hasSourceCode(context)) {
            prompt.append("Class Source Code to Test:\n```java\n")
                  .append(context.getClassSourceCode())
                  .append("\n```\n\n")
                  .append("Generate comprehensive unit tests for the provided class source code based on the user request. ");
        } else {
            prompt.append("Generate comprehensive unit tests based on this request. ")
                  .append("Determine the appropriate component type and ");
        }

        prompt.append("Create production-ready tests using JUnit 5, Mockito, and AssertJ. ")
              .append("Follow the JSON response format specified in the system prompt.");

        return prompt.toString();
    }

    public String refinePrompt(PromptContext context, String refinementInstructions) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Refine the previously generated tests based on this feedback: ")
              .append(refinementInstructions).append("\n\n")
              .append("Original request: ").append(context.getUserInput()).append("\n\n");

        if (hasSourceCode(context)) {
            prompt.append("Original Class Source Code:\n```java\n")
                  .append(context.getClassSourceCode())
                  .append("\n```\n\n");
        }

        prompt.append("Maintain the same JSON response format.");
        return prompt.toString();
    }

    private boolean hasSourceCode(PromptContext context) {
        return context.getClassSourceCode() != null && !context.getClassSourceCode().trim().isEmpty();
    }
}
