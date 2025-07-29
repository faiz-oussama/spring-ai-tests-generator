package com.univade.ai.infrastructure.ai;

import com.univade.ai.domain.model.EntityMetaModel;
import com.univade.ai.domain.value.TargetLayer;
import org.springframework.stereotype.Component;

@Component
public class OpenAiClient {

    public String generateTests(String prompt) {
        return null;
    }

    public String generateTestsForLayer(EntityMetaModel entityMetaModel, TargetLayer targetLayer) {
        return null;
    }

    public String refineTests(String originalTests, String refinementPrompt) {
        return null;
    }

    public EntityMetaModel analyzeSourceCode(String sourceCode) {
        return null;
    }
}
