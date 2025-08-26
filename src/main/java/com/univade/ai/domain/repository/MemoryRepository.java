package com.univade.ai.domain.repository;

import com.univade.ai.domain.model.PromptContext;
import java.util.Optional;

public interface MemoryRepository {
    void save(String sessionId, PromptContext context);
    Optional<PromptContext> findBySessionId(String sessionId);
    void deleteBySessionId(String sessionId);
    void updateContext(String sessionId, PromptContext context);
}
