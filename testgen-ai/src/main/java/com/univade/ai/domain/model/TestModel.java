package com.univade.ai.domain.model;

import com.univade.ai.domain.value.DifficultyLevel;
import java.time.LocalDateTime;
import java.util.List;

public class TestModel {
    private String id;
    private String sourceCode;
    private String className;
    private String packageName;
    private List<String> methods;
    private List<String> fields;
    private List<String> annotations;
    private String generatedTestCode;
    private DifficultyLevel difficultyLevel;
    private String sessionId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public TestModel() {}

    public TestModel(String id, String sourceCode, String className) {
        this.id = id;
        this.sourceCode = sourceCode;
        this.className = className;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSourceCode() { return sourceCode; }
    public void setSourceCode(String sourceCode) { this.sourceCode = sourceCode; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public String getPackageName() { return packageName; }
    public void setPackageName(String packageName) { this.packageName = packageName; }

    public List<String> getMethods() { return methods; }
    public void setMethods(List<String> methods) { this.methods = methods; }

    public List<String> getFields() { return fields; }
    public void setFields(List<String> fields) { this.fields = fields; }

    public List<String> getAnnotations() { return annotations; }
    public void setAnnotations(List<String> annotations) { this.annotations = annotations; }

    public String getGeneratedTestCode() { return generatedTestCode; }
    public void setGeneratedTestCode(String generatedTestCode) { this.generatedTestCode = generatedTestCode; }

    public DifficultyLevel getDifficultyLevel() { return difficultyLevel; }
    public void setDifficultyLevel(DifficultyLevel difficultyLevel) { this.difficultyLevel = difficultyLevel; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
