package com.univade.ai.interfaces.dto;

import java.time.LocalDateTime;

public class TestResponseDTO {
    private String sessionId;
    private String conversationId;
    private String generatedTestCode;
    private String className;
    private String targetLayer;
    private String status;
    private String errorMessage;
    private LocalDateTime generatedAt;
    private boolean isNewConversation;

    public TestResponseDTO() {}

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getConversationId() { return conversationId; }
    public void setConversationId(String conversationId) { this.conversationId = conversationId; }

    public String getGeneratedTestCode() { return generatedTestCode; }
    public void setGeneratedTestCode(String generatedTestCode) { this.generatedTestCode = generatedTestCode; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public String getTargetLayer() { return targetLayer; }
    public void setTargetLayer(String targetLayer) { this.targetLayer = targetLayer; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    public LocalDateTime getGeneratedAt() { return generatedAt; }
    public void setGeneratedAt(LocalDateTime generatedAt) { this.generatedAt = generatedAt; }

    public boolean isNewConversation() { return isNewConversation; }
    public void setNewConversation(boolean newConversation) { isNewConversation = newConversation; }
}
