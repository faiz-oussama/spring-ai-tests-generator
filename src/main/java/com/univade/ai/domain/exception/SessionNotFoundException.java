package com.univade.ai.domain.exception;

public class SessionNotFoundException extends TestGenerationException {
    
    public SessionNotFoundException(String sessionId) {
        super("Session not found: " + sessionId, sessionId);
    }
    
    public SessionNotFoundException(String sessionId, Throwable cause) {
        super("Session not found: " + sessionId, sessionId, cause);
    }
}
