package com.univade.ai.interfaces.dto;

import com.univade.ai.domain.value.DifficultyLevel;

public class TestRequestDTO {
    private String sourceCode;
    private String className;
    private DifficultyLevel difficultyLevel;
    private String testType;
    private String additionalInstructions;
    private String sessionId;

    public TestRequestDTO() {}

    public String getSourceCode() { return sourceCode; }
    public void setSourceCode(String sourceCode) { this.sourceCode = sourceCode; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public DifficultyLevel getDifficultyLevel() { return difficultyLevel; }
    public void setDifficultyLevel(DifficultyLevel difficultyLevel) { this.difficultyLevel = difficultyLevel; }

    public String getTestType() { return testType; }
    public void setTestType(String testType) { this.testType = testType; }

    public String getAdditionalInstructions() { return additionalInstructions; }
    public void setAdditionalInstructions(String additionalInstructions) { this.additionalInstructions = additionalInstructions; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }
}
