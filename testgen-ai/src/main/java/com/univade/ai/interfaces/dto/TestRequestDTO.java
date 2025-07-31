package com.univade.ai.interfaces.dto;

public class TestRequestDTO {
    private String userInput;
    private String sessionId;

    public TestRequestDTO() {}

    public String getUserInput() { return userInput; }
    public void setUserInput(String userInput) { this.userInput = userInput; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }
}
