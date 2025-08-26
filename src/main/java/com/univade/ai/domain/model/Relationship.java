package com.univade.ai.domain.model;

public class Relationship {
    private String name;
    private String type;
    private String targetEntity;
    private String mappedBy;
    private boolean cascade;
    private boolean fetch;

    public Relationship() {}

    public Relationship(String name, String type, String targetEntity) {
        this.name = name;
        this.type = type;
        this.targetEntity = targetEntity;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getTargetEntity() { return targetEntity; }
    public void setTargetEntity(String targetEntity) { this.targetEntity = targetEntity; }

    public String getMappedBy() { return mappedBy; }
    public void setMappedBy(String mappedBy) { this.mappedBy = mappedBy; }

    public boolean isCascade() { return cascade; }
    public void setCascade(boolean cascade) { this.cascade = cascade; }

    public boolean isFetch() { return fetch; }
    public void setFetch(boolean fetch) { this.fetch = fetch; }
}
