package com.univade.ai.domain.model;

import java.util.Map;

public class PromptContext {
    private String sessionId;
    private String userInput;
    private Map<String, Object> metadata;

    public PromptContext() {}

    public PromptContext(String sessionId, String userInput) {
        this.sessionId = sessionId;
        this.userInput = userInput;
    }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getUserInput() { return userInput; }
    public void setUserInput(String userInput) { this.userInput = userInput; }

    public Map<String, Object> getMetadata() { return metadata; }
    public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }
}
