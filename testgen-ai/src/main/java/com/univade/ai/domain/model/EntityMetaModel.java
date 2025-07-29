package com.univade.ai.domain.model;

import java.util.List;

public class EntityMetaModel {
    private String className;
    private String packageName;
    private List<Attribute> attributes;
    private List<Relationship> relationships;
    private List<String> annotations;
    private String sourceCode;

    public EntityMetaModel() {}

    public EntityMetaModel(String className, String packageName) {
        this.className = className;
        this.packageName = packageName;
    }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public String getPackageName() { return packageName; }
    public void setPackageName(String packageName) { this.packageName = packageName; }

    public List<Attribute> getAttributes() { return attributes; }
    public void setAttributes(List<Attribute> attributes) { this.attributes = attributes; }

    public List<Relationship> getRelationships() { return relationships; }
    public void setRelationships(List<Relationship> relationships) { this.relationships = relationships; }

    public List<String> getAnnotations() { return annotations; }
    public void setAnnotations(List<String> annotations) { this.annotations = annotations; }

    public String getSourceCode() { return sourceCode; }
    public void setSourceCode(String sourceCode) { this.sourceCode = sourceCode; }
}
