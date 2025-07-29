package com.univade.ai.application.service;

import com.univade.ai.domain.model.EntityMetaModel;
import com.univade.ai.domain.model.PromptContext;
import com.univade.ai.domain.value.TargetLayer;
import java.util.List;

public class TestGenerationService {

    public String generateTests(PromptContext context) {
        return null;
    }

    public List<String> generateMultipleTests(List<PromptContext> contexts) {
        return null;
    }

    public String refineExistingTests(String sessionId, String refinementInstructions) {
        return null;
    }

    public EntityMetaModel parseSourceCode(String sourceCode) {
        return null;
    }

    public PromptContext buildContext(EntityMetaModel entityMetaModel, TargetLayer targetLayer, String sessionId) {
        return null;
    }
}
