package com.univade.ai.interfaces.dto;

public class TestRequestDTO {
    private String userInput;
    private String classSourceCode;
    private String sessionId;
    private String conversationId;

    public String getUserInput() {
        return userInput;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    public String getClassSourceCode() {
        return classSourceCode;
    }

    public void setClassSourceCode(String classSourceCode) {
        this.classSourceCode = classSourceCode;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public boolean isContinuingConversation() {
        return conversationId != null && !conversationId.trim().isEmpty();
    }

    public boolean hasClassSourceCode() {
        return classSourceCode != null && !classSourceCode.trim().isEmpty();
    }
}
