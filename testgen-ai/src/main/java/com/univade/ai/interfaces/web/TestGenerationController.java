package com.univade.ai.interfaces.web;

import com.univade.ai.interfaces.dto.TestRequestDTO;
import com.univade.ai.interfaces.dto.TestResponseDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test-generation")
public class TestGenerationController {
    
    @PostMapping("/generate")
    public TestResponseDTO generateTest(@RequestBody TestRequestDTO request) {
        return null;
    }
    
    @PostMapping("/refine/{sessionId}")
    public TestResponseDTO refineTest(@PathVariable String sessionId, @RequestBody String refinementPrompt) {
        return null;
    }
    
    @GetMapping("/session/{sessionId}")
    public TestResponseDTO getTestBySession(@PathVariable String sessionId) {
        return null;
    }
    
    @DeleteMapping("/session/{sessionId}")
    public void deleteSession(@PathVariable String sessionId) {
        
    }
}
