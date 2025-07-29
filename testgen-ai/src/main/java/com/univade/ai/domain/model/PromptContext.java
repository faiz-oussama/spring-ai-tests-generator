package com.univade.ai.domain.model;

import com.univade.ai.domain.value.DifficultyLevel;
import java.util.Map;

public class PromptContext {
    private String sessionId;
    private String sourceCode;
    private String className;
    private DifficultyLevel difficultyLevel;
    private String testType;
    private Map<String, Object> metadata;
    private String additionalInstructions;

    public PromptContext() {}

    public PromptContext(String sessionId, String sourceCode, String className) {
        this.sessionId = sessionId;
        this.sourceCode = sourceCode;
        this.className = className;
    }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getSourceCode() { return sourceCode; }
    public void setSourceCode(String sourceCode) { this.sourceCode = sourceCode; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public DifficultyLevel getDifficultyLevel() { return difficultyLevel; }
    public void setDifficultyLevel(DifficultyLevel difficultyLevel) { this.difficultyLevel = difficultyLevel; }

    public String getTestType() { return testType; }
    public void setTestType(String testType) { this.testType = testType; }

    public Map<String, Object> getMetadata() { return metadata; }
    public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }

    public String getAdditionalInstructions() { return additionalInstructions; }
    public void setAdditionalInstructions(String additionalInstructions) { this.additionalInstructions = additionalInstructions; }
}
