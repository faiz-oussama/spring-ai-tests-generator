package com.univade.ai.domain.service;

import com.univade.ai.domain.model.TestGenerationResult;
import org.springframework.stereotype.Service;

@Service
public class TestResultProcessor {    
    public boolean isValidTestResult(TestGenerationResult result) {
        return result != null &&
               "SUCCESS".equals(result.getStatus()) &&
               result.getTestClass() != null &&
               result.getTestClass().getSourceCode() != null &&
               result.getTestClass().getSourceCode().contains("class");
    }

}
