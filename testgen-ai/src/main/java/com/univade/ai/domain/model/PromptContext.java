package com.univade.ai.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PromptContext {
    private String sessionId;
    private String conversationId;
    private String userInput;
    private String classSourceCode;
    private Map<String, Object> metadata;
    private List<ConversationMessage> conversationHistory;
    private ConversationSummary summary;
    private boolean useConversationMemory;

    public PromptContext() {
        this.conversationHistory = new ArrayList<>();
        this.useConversationMemory = false;
    }

    public PromptContext(String sessionId, String userInput) {
        this();
        this.sessionId = sessionId;
        this.userInput = userInput;
    }

    public PromptContext(String sessionId, String userInput, String classSourceCode) {
        this();
        this.sessionId = sessionId;
        this.userInput = userInput;
        this.classSourceCode = classSourceCode;
    }

    public PromptContext(String sessionId, String conversationId, String userInput, String classSourceCode) {
        this();
        this.sessionId = sessionId;
        this.conversationId = conversationId;
        this.userInput = userInput;
        this.classSourceCode = classSourceCode;
        this.useConversationMemory = true;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserInput() {
        return userInput;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    public String getClassSourceCode() {
        return classSourceCode;
    }

    public void setClassSourceCode(String classSourceCode) {
        this.classSourceCode = classSourceCode;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public List<ConversationMessage> getConversationHistory() {
        return conversationHistory;
    }

    public void setConversationHistory(List<ConversationMessage> conversationHistory) {
        this.conversationHistory = conversationHistory;
    }

    public ConversationSummary getSummary() {
        return summary;
    }

    public void setSummary(ConversationSummary summary) {
        this.summary = summary;
    }

    public boolean isUseConversationMemory() {
        return useConversationMemory;
    }

    public void setUseConversationMemory(boolean useConversationMemory) {
        this.useConversationMemory = useConversationMemory;
    }
    public void addToHistory(ConversationMessage message) {
        if (this.conversationHistory == null) {
            this.conversationHistory = new ArrayList<>();
        }
        this.conversationHistory.add(message);
    }

    public List<ConversationMessage> getRecentHistory(int count) {
        if (this.conversationHistory == null || this.conversationHistory.isEmpty()) {
            return new ArrayList<>();
        }

        int size = this.conversationHistory.size();
        int fromIndex = Math.max(0, size - count);
        return new ArrayList<>(this.conversationHistory.subList(fromIndex, size));
    }

    public boolean hasConversationHistory() {
        return this.conversationHistory != null && !this.conversationHistory.isEmpty();
    }
    public boolean shouldUseConversationMemory() {
        return this.useConversationMemory && this.conversationId != null;
    }

    public int getConversationHistorySize() {
        return this.conversationHistory != null ? this.conversationHistory.size() : 0;
    }

    @Override
    public String toString() {
        return "PromptContext{" +
                "sessionId='" + sessionId + '\'' +
                ", conversationId='" + conversationId + '\'' +
                ", useConversationMemory=" + useConversationMemory +
                ", historySize=" + getConversationHistorySize() +
                ", hasClassSourceCode=" + (classSourceCode != null && !classSourceCode.trim().isEmpty()) +
                '}';
    }
}
