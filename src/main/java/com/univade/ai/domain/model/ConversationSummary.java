package com.univade.ai.domain.model;

import java.time.LocalDateTime;
import java.util.List;

public class ConversationSummary {
    
    private String conversationId;
    private String summaryText;
    private List<String> keyTopics;
    private List<String> generatedTestClasses;
    private int totalMessages;
    private int totalTokens;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;

    public ConversationSummary() {
        this.createdAt = LocalDateTime.now();
        this.lastUpdated = LocalDateTime.now();
    }

    public ConversationSummary(String conversationId, String summaryText) {
        this();
        this.conversationId = conversationId;
        this.summaryText = summaryText;
    }
    public void updateSummary(String newSummaryText) {
        this.summaryText = newSummaryText;
        this.lastUpdated = LocalDateTime.now();
    }
    public void addKeyTopic(String topic) {
        if (this.keyTopics != null && !this.keyTopics.contains(topic)) {
            this.keyTopics.add(topic);
            this.lastUpdated = LocalDateTime.now();
        }
    }
    public void addGeneratedTestClass(String testClassName) {
        if (this.generatedTestClasses != null && !this.generatedTestClasses.contains(testClassName)) {
            this.generatedTestClasses.add(testClassName);
            this.lastUpdated = LocalDateTime.now();
        }
    }

    // Getters and Setters
    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getSummaryText() {
        return summaryText;
    }

    public void setSummaryText(String summaryText) {
        this.summaryText = summaryText;
    }

    public List<String> getKeyTopics() {
        return keyTopics;
    }

    public void setKeyTopics(List<String> keyTopics) {
        this.keyTopics = keyTopics;
    }

    public List<String> getGeneratedTestClasses() {
        return generatedTestClasses;
    }

    public void setGeneratedTestClasses(List<String> generatedTestClasses) {
        this.generatedTestClasses = generatedTestClasses;
    }

    public int getTotalMessages() {
        return totalMessages;
    }

    public void setTotalMessages(int totalMessages) {
        this.totalMessages = totalMessages;
    }

    public int getTotalTokens() {
        return totalTokens;
    }

    public void setTotalTokens(int totalTokens) {
        this.totalTokens = totalTokens;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        return "ConversationSummary{" +
                "conversationId='" + conversationId + '\'' +
                ", totalMessages=" + totalMessages +
                ", totalTokens=" + totalTokens +
                ", keyTopics=" + keyTopics +
                ", generatedTestClasses=" + generatedTestClasses +
                ", createdAt=" + createdAt +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
}
