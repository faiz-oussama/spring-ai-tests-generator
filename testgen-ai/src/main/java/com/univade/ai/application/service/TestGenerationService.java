package com.univade.ai.application.service;

import com.univade.ai.domain.model.TestModel;
import com.univade.ai.domain.model.PromptContext;
import java.util.List;

public class TestGenerationService {
    
    public TestModel generateTest(PromptContext context) {
        return null;
    }
    
    public List<TestModel> generateMultipleTests(List<PromptContext> contexts) {
        return null;
    }
    
    public TestModel refineExistingTest(String sessionId, String refinementInstructions) {
        return null;
    }
    
    public String parseSourceCode(String sourceCode) {
        return null;
    }
    
    public PromptContext buildContext(String sourceCode, String sessionId) {
        return null;
    }
}
