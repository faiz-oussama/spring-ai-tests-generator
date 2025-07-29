package com.univade.ai.domain.model;

import com.univade.ai.domain.value.TargetLayer;
import java.util.Map;

public class PromptContext {
    private String sessionId;
    private EntityMetaModel entityMetaModel;
    private TargetLayer targetLayer;
    private String additionalInstructions;
    private Map<String, Object> metadata;

    public PromptContext() {}

    public PromptContext(String sessionId, EntityMetaModel entityMetaModel, TargetLayer targetLayer) {
        this.sessionId = sessionId;
        this.entityMetaModel = entityMetaModel;
        this.targetLayer = targetLayer;
    }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public EntityMetaModel getEntityMetaModel() { return entityMetaModel; }
    public void setEntityMetaModel(EntityMetaModel entityMetaModel) { this.entityMetaModel = entityMetaModel; }

    public TargetLayer getTargetLayer() { return targetLayer; }
    public void setTargetLayer(TargetLayer targetLayer) { this.targetLayer = targetLayer; }

    public String getAdditionalInstructions() { return additionalInstructions; }
    public void setAdditionalInstructions(String additionalInstructions) { this.additionalInstructions = additionalInstructions; }

    public Map<String, Object> getMetadata() { return metadata; }
    public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }
}
