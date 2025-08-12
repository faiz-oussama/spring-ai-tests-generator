package com.univade.ai.domain.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ConversationContext {
    
    private String conversationId;
    private String userId;
    private String sessionId;
    private LocalDateTime startedAt;
    private LocalDateTime lastInteractionAt;
    private ConversationStatus status;
    private Map<String, Object> metadata;
    private ConversationSummary summary;
    private int messageCount;
    private int totalTokens;

    public ConversationContext() {
        this.metadata = new HashMap<>();
        this.status = ConversationStatus.ACTIVE;
        this.startedAt = LocalDateTime.now();
        this.lastInteractionAt = LocalDateTime.now();
        this.messageCount = 0;
        this.totalTokens = 0;
    }

    public ConversationContext(String conversationId, String userId, String sessionId) {
        this();
        this.conversationId = conversationId;
        this.userId = userId;
        this.sessionId = sessionId;
    }

    
    public void markActive() {
        this.status = ConversationStatus.ACTIVE;
        this.lastInteractionAt = LocalDateTime.now();
    }

    
    public void markInactive() {
        this.status = ConversationStatus.INACTIVE;
    }

    
    public void markCompleted() {
        this.status = ConversationStatus.COMPLETED;
    }

   
    public void addMetadata(String key, Object value) {
        this.metadata.put(key, value);
    }

    public void incrementMessageCount() {
        this.messageCount++;
        this.lastInteractionAt = LocalDateTime.now();
    }

   
    public void addTokens(int tokens) {
        this.totalTokens += tokens;
    }

    
    public boolean isActive() {
        return ConversationStatus.ACTIVE.equals(this.status);
    }

    
    public boolean isInactiveFor(long inactiveThresholdMinutes) {
        return lastInteractionAt.isBefore(LocalDateTime.now().minusMinutes(inactiveThresholdMinutes));
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getLastInteractionAt() {
        return lastInteractionAt;
    }

    public void setLastInteractionAt(LocalDateTime lastInteractionAt) {
        this.lastInteractionAt = lastInteractionAt;
    }

    public ConversationStatus getStatus() {
        return status;
    }

    public void setStatus(ConversationStatus status) {
        this.status = status;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public ConversationSummary getSummary() {
        return summary;
    }

    public void setSummary(ConversationSummary summary) {
        this.summary = summary;
    }

    public int getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(int messageCount) {
        this.messageCount = messageCount;
    }

    public int getTotalTokens() {
        return totalTokens;
    }

    public void setTotalTokens(int totalTokens) {
        this.totalTokens = totalTokens;
    }

    @Override
    public String toString() {
        return "ConversationContext{" +
                "conversationId='" + conversationId + '\'' +
                ", userId='" + userId + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", status=" + status +
                ", messageCount=" + messageCount +
                ", totalTokens=" + totalTokens +
                ", startedAt=" + startedAt +
                ", lastInteractionAt=" + lastInteractionAt +
                '}';
    }
}
