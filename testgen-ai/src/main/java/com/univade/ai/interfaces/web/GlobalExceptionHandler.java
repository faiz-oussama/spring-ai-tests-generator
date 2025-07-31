package com.univade.ai.interfaces.web;

import com.univade.ai.domain.exception.SessionNotFoundException;
import com.univade.ai.domain.exception.TestGenerationException;
import com.univade.ai.interfaces.dto.TestResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    @ExceptionHandler(SessionNotFoundException.class)
    public ResponseEntity<TestResponseDTO> handleSessionNotFound(SessionNotFoundException ex) {
        logger.warn("Session not found: {}", ex.getSessionId());
        
        TestResponseDTO response = new TestResponseDTO();
        response.setSessionId(ex.getSessionId());
        response.setStatus("ERROR");
        response.setErrorMessage(ex.getMessage());
        response.setGeneratedAt(LocalDateTime.now());
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    
    @ExceptionHandler(TestGenerationException.class)
    public ResponseEntity<TestResponseDTO> handleTestGenerationException(TestGenerationException ex) {
        logger.error("Test generation error: {}", ex.getMessage(), ex);
        
        TestResponseDTO response = new TestResponseDTO();
        response.setSessionId(ex.getSessionId());
        response.setStatus("ERROR");
        response.setErrorMessage(ex.getMessage());
        response.setGeneratedAt(LocalDateTime.now());
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<TestResponseDTO> handleIllegalArgument(IllegalArgumentException ex) {
        logger.warn("Invalid request: {}", ex.getMessage());
        
        TestResponseDTO response = new TestResponseDTO();
        response.setStatus("ERROR");
        response.setErrorMessage("Invalid request: " + ex.getMessage());
        response.setGeneratedAt(LocalDateTime.now());
        
        return ResponseEntity.badRequest().body(response);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<TestResponseDTO> handleGenericException(Exception ex) {
        logger.error("Unexpected error", ex);
        
        TestResponseDTO response = new TestResponseDTO();
        response.setStatus("ERROR");
        response.setErrorMessage("Internal server error");
        response.setGeneratedAt(LocalDateTime.now());
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
