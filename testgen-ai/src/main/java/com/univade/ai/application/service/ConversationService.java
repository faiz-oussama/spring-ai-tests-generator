package com.univade.ai.application.service;

import com.univade.ai.domain.model.ConversationContext;
import com.univade.ai.domain.model.ConversationStatus;
import com.univade.ai.domain.model.ConversationSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class ConversationService {

    private static final Logger logger = LoggerFactory.getLogger(ConversationService.class);
    private final Map<String, ConversationContext> conversations = new ConcurrentHashMap<>();
    private final Map<String, List<String>> userConversations = new ConcurrentHashMap<>();
    private static final long DEFAULT_CONVERSATION_TIMEOUT_MINUTES = 30;
    private static final int MAX_CONVERSATIONS_PER_USER = 20;

    public String startConversation(String userId) {
        return startConversation(userId, null);
    }

    public String startConversation(String userId, String sessionId) {
        String conversationId = UUID.randomUUID().toString();
        
        ConversationContext context = new ConversationContext(conversationId, userId, sessionId);
        context.addMetadata("startedBy", userId);
        context.addMetadata("sessionId", sessionId);

        conversations.put(conversationId, context);
        userConversations.computeIfAbsent(userId, k -> new java.util.ArrayList<>()).add(conversationId);
        cleanupUserConversations(userId);

        logger.info("Started new conversation: {} for user: {}", conversationId, userId);
        return conversationId;
    }

    public void endConversation(String conversationId) {
        ConversationContext context = conversations.get(conversationId);
        if (context != null) {
            context.markCompleted();
            logger.info("Ended conversation: {}", conversationId);
        }
    }

    public Optional<ConversationContext> getConversationContext(String conversationId) {
        ConversationContext context = conversations.get(conversationId);
        if (context != null) {
            context.markActive();
        }
        return Optional.ofNullable(context);
    }

    public List<ConversationContext> getUserConversations(String userId) {
        List<String> conversationIds = userConversations.get(userId);
        if (conversationIds == null) {
            return List.of();
        }
        
        return conversationIds.stream()
                .map(conversations::get)
                .filter(context -> context != null)
                .collect(Collectors.toList());
    }

    public void updateConversationMetadata(String conversationId, Map<String, Object> metadata) {
        ConversationContext context = conversations.get(conversationId);
        if (context != null) {
            metadata.forEach(context::addMetadata);
            context.markActive();
            logger.debug("Updated metadata for conversation: {}", conversationId);
        }
    }

    public ConversationSummary summarizeConversation(String conversationId) {
        ConversationContext context = conversations.get(conversationId);
        if (context == null) {
            return null;
        }

        ConversationSummary summary = new ConversationSummary(conversationId,
                "Conversation summary for " + conversationId);
        summary.setTotalMessages(context.getMessageCount());
        summary.setTotalTokens(context.getTotalTokens());

        context.setSummary(summary);
        return summary;
    }

    public boolean isConversationActive(String conversationId) {
        ConversationContext context = conversations.get(conversationId);
        return context != null && context.isActive();
    }

    public long getActiveConversationCount() {
        return conversations.values().stream()
                .filter(ConversationContext::isActive)
                .count();
    }

    public void cleanupInactiveConversations() {
        LocalDateTime cutoff = LocalDateTime.now().minusMinutes(DEFAULT_CONVERSATION_TIMEOUT_MINUTES);

        List<String> toRemove = conversations.entrySet().stream()
                .filter(entry -> entry.getValue().getLastInteractionAt().isBefore(cutoff))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        toRemove.forEach(conversationId -> {
            ConversationContext context = conversations.remove(conversationId);
            if (context != null) {
                List<String> userConvs = userConversations.get(context.getUserId());
                if (userConvs != null) {
                    userConvs.remove(conversationId);
                }
                logger.info("Cleaned up inactive conversation: {}", conversationId);
            }
        });
    }

    private void cleanupUserConversations(String userId) {
        List<String> userConvs = userConversations.get(userId);
        if (userConvs != null && userConvs.size() > MAX_CONVERSATIONS_PER_USER) {
            int toRemove = userConvs.size() - MAX_CONVERSATIONS_PER_USER;
            for (int i = 0; i < toRemove; i++) {
                String oldConversationId = userConvs.remove(0);
                conversations.remove(oldConversationId);
                logger.info("Removed old conversation: {} for user: {}", oldConversationId, userId);
            }
        }
    }
    public void incrementMessageCount(String conversationId) {
        ConversationContext context = conversations.get(conversationId);
        if (context != null) {
            context.incrementMessageCount();
        }
    }

    public void addTokens(String conversationId, int tokens) {
        ConversationContext context = conversations.get(conversationId);
        if (context != null) {
            context.addTokens(tokens);
        }
    }
}
