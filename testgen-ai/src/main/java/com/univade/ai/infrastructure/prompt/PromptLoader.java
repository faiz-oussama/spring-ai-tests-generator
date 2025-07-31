package com.univade.ai.infrastructure.prompt;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class PromptLoader {
    
    private static final String SYSTEM_PROMPT_PATH = "prompts/testgen_system_prompt.md";
    private String cachedSystemPrompt;
    
    public String loadSystemPrompt() {
        if (cachedSystemPrompt == null) {
            cachedSystemPrompt = loadPromptFromResource(SYSTEM_PROMPT_PATH);
        }
        return cachedSystemPrompt;
    }
    
    public String loadPromptFromResource(String resourcePath) {
        try {
            ClassPathResource resource = new ClassPathResource(resourcePath);
            return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load prompt from resource: " + resourcePath, e);
        }
    }
    
    public void clearCache() {
        cachedSystemPrompt = null;
    }
}
