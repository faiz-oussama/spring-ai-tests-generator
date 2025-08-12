package com.univade.ai.interfaces.web;

import com.univade.ai.application.service.TestGenerationService;
import com.univade.ai.application.usecase.GenerateTestsUseCase;
import com.univade.ai.domain.model.PromptContext;
import com.univade.ai.domain.model.TestGenerationResult;
import com.univade.ai.interfaces.dto.TestRequestDTO;
import com.univade.ai.interfaces.dto.TestResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test-generation")
public class TestGenerationController {
    private final TestGenerationService testGenerationService;

    public TestGenerationController(TestGenerationService testGenerationService) {
        this.testGenerationService = testGenerationService;
    }

    @PostMapping("/generate")
    public ResponseEntity<TestResponseDTO> generateTest(@RequestBody TestRequestDTO request) {
        try {
           
            PromptContext context = buildContextAwarePromptContext(request);
            TestGenerationResult result = testGenerationService.generateTestsWithMemory(context);
            TestResponseDTO response = convertToContextAwareResponseDTO(result);

            return "ERROR".equals(result.getStatus())
                ? ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response)
                : ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(createErrorResponse(null, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(createErrorResponse(null, "Internal server error"));
        }
    }

    private PromptContext buildContextAwarePromptContext(TestRequestDTO request) {
        if (request.isContinuingConversation()) {
            PromptContext context = new PromptContext(
                    request.getSessionId(),
                    request.getConversationId(),
                    request.getUserInput(),
                    request.getClassSourceCode());
            context.setUseConversationMemory(true);
            return context;
        } else {
            PromptContext context = testGenerationService.buildConversationContext(
                    request.getUserInput(),
                    request.getClassSourceCode(),
                    true);

            if (request.getSessionId() != null) {
                context.setSessionId(request.getSessionId());
            }

            return context;
        }
    }

    private TestResponseDTO convertToContextAwareResponseDTO(TestGenerationResult result) {
        TestResponseDTO response = convertToResponseDTO(result);
        response.setConversationId(result.getConversationId());
        response.setNewConversation(result.isNewConversation());
        return response;
    }




    private TestResponseDTO convertToResponseDTO(TestGenerationResult result) {
        TestResponseDTO response = new TestResponseDTO();
        response.setSessionId(result.getSessionId());
        response.setStatus(result.getStatus());
        response.setErrorMessage(result.getErrorMessage());
        response.setGeneratedAt(result.getGeneratedAt());

        if (result.getTestClass() != null && result.getTestClass().getSourceCode() != null) {
            response.setGeneratedTestCode(result.getTestClass().getSourceCode());
        }

        if (result.getMetadata() != null) {
            response.setClassName(result.getMetadata().getEntityName());
            response.setTargetLayer(result.getMetadata().getComponentType());
        }

        return response;
    }

    private TestResponseDTO createErrorResponse(String sessionId, String errorMessage) {
        TestResponseDTO response = new TestResponseDTO();
        response.setSessionId(sessionId);
        response.setStatus("ERROR");
        response.setErrorMessage(errorMessage);
        return response;
    }


}
