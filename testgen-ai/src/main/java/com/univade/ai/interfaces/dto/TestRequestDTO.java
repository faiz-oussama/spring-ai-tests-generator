package com.univade.ai.interfaces.dto;

import com.univade.ai.domain.value.TargetLayer;

public class TestRequestDTO {
    private String sourceCode;
    private String className;
    private TargetLayer targetLayer;
    private String additionalInstructions;
    private String sessionId;

    public TestRequestDTO() {}

    public String getSourceCode() { return sourceCode; }
    public void setSourceCode(String sourceCode) { this.sourceCode = sourceCode; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public TargetLayer getTargetLayer() { return targetLayer; }
    public void setTargetLayer(TargetLayer targetLayer) { this.targetLayer = targetLayer; }

    public String getAdditionalInstructions() { return additionalInstructions; }
    public void setAdditionalInstructions(String additionalInstructions) { this.additionalInstructions = additionalInstructions; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }
}
