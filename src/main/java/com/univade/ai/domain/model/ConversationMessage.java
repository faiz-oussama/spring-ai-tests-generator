package com.univade.ai.domain.model;

import java.time.LocalDateTime;
import java.util.Map;


public class ConversationMessage {
    
    private String messageId;
    private String conversationId;
    private MessageType messageType;
    private String content;
    private String role; // "user", "assistant", "system"
    private LocalDateTime timestamp;
    private Map<String, Object> metadata;
    private int tokenCount;

    public ConversationMessage() {
        this.timestamp = LocalDateTime.now();
    }

    public ConversationMessage(String conversationId, MessageType messageType, String content, String role) {
        this();
        this.conversationId = conversationId;
        this.messageType = messageType;
        this.content = content;
        this.role = role;
    }


    public static ConversationMessage userMessage(String conversationId, String content) {
        return new ConversationMessage(conversationId, MessageType.USER_INPUT, content, "user");
    }


    public static ConversationMessage assistantMessage(String conversationId, String content) {
        return new ConversationMessage(conversationId, MessageType.AI_RESPONSE, content, "assistant");
    }

 
    public static ConversationMessage systemMessage(String conversationId, String content) {
        return new ConversationMessage(conversationId, MessageType.SYSTEM, content, "system");
    }

    public boolean isUserMessage() {
        return MessageType.USER_INPUT.equals(this.messageType);
    }

  
    public boolean isAiMessage() {
        return MessageType.AI_RESPONSE.equals(this.messageType);
    }

  
    public boolean isSystemMessage() {
        return MessageType.SYSTEM.equals(this.messageType);
    }

    // Getters and Setters
    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public int getTokenCount() {
        return tokenCount;
    }

    public void setTokenCount(int tokenCount) {
        this.tokenCount = tokenCount;
    }

    @Override
    public String toString() {
        return "ConversationMessage{" +
                "messageId='" + messageId + '\'' +
                ", conversationId='" + conversationId + '\'' +
                ", messageType=" + messageType +
                ", role='" + role + '\'' +
                ", tokenCount=" + tokenCount +
                ", timestamp=" + timestamp +
                '}';
    }
}
