package com.univade.ai.domain.exception;

public class TestGenerationException extends RuntimeException {
    
    private final String sessionId;
    
    public TestGenerationException(String message) {
        super(message);
        this.sessionId = null;
    }
    
    public TestGenerationException(String message, String sessionId) {
        super(message);
        this.sessionId = sessionId;
    }
    
    public TestGenerationException(String message, Throwable cause) {
        super(message, cause);
        this.sessionId = null;
    }
    
    public TestGenerationException(String message, String sessionId, Throwable cause) {
        super(message, cause);
        this.sessionId = sessionId;
    }
    
    public String getSessionId() {
        return sessionId;
    }
}
