package com.univade.ai.interfaces.web;

import com.univade.ai.application.usecase.GenerateTestsUseCase;
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

    private static final Logger logger = LoggerFactory.getLogger(TestGenerationController.class);

    private final GenerateTestsUseCase generateTestsUseCase;

    public TestGenerationController(GenerateTestsUseCase generateTestsUseCase) {
        this.generateTestsUseCase = generateTestsUseCase;
    }

    @PostMapping("/generate")
    public ResponseEntity<TestResponseDTO> generateTest(@RequestBody TestRequestDTO request) {
        try {
            TestGenerationResult result = generateTestsUseCase.generateFromUserInput(request.getUserInput());
            TestResponseDTO response = convertToResponseDTO(result);

            if ("ERROR".equals(result.getStatus())) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            logger.warn("Invalid request: {}", e.getMessage());
            TestResponseDTO errorResponse = createErrorResponse(null, e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);

        } catch (Exception e) {
            logger.error("Error generating tests", e);
            TestResponseDTO errorResponse = createErrorResponse(null, "Internal server error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
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
