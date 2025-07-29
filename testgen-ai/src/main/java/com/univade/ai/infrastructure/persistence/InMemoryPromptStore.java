package com.univade.ai.infrastructure.persistence;

import com.univade.ai.domain.model.PromptContext;
import com.univade.ai.domain.repository.MemoryRepository;
import org.springframework.stereotype.Repository;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryPromptStore implements MemoryRepository {
    
    private final Map<String, PromptContext> store = new ConcurrentHashMap<>();
    
    @Override
    public void save(String sessionId, PromptContext context) {
        store.put(sessionId, context);
    }
    
    @Override
    public Optional<PromptContext> findBySessionId(String sessionId) {
        return Optional.ofNullable(store.get(sessionId));
    }
    
    @Override
    public void deleteBySessionId(String sessionId) {
        store.remove(sessionId);
    }
    
    @Override
    public void updateContext(String sessionId, PromptContext context) {
        store.put(sessionId, context);
    }
}
