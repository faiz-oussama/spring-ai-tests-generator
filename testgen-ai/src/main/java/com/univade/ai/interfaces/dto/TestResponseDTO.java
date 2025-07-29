package com.univade.ai.interfaces.dto;

import com.univade.ai.domain.value.TargetLayer;
import java.time.LocalDateTime;

public class TestResponseDTO {
    private String sessionId;
    private String generatedTestCode;
    private String className;
    private TargetLayer targetLayer;
    private String status;
    private String errorMessage;
    private LocalDateTime generatedAt;

    public TestResponseDTO() {}

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public String getGeneratedTestCode() { return generatedTestCode; }
    public void setGeneratedTestCode(String generatedTestCode) { this.generatedTestCode = generatedTestCode; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public TargetLayer getTargetLayer() { return targetLayer; }
    public void setTargetLayer(TargetLayer targetLayer) { this.targetLayer = targetLayer; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    public LocalDateTime getGeneratedAt() { return generatedAt; }
    public void setGeneratedAt(LocalDateTime generatedAt) { this.generatedAt = generatedAt; }
}
