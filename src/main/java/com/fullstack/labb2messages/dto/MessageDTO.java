package com.fullstack.labb2messages.dto;

import java.time.LocalDateTime;

public class MessageDTO {
    private String text;
    private boolean isRead;
    private int senderId;
    private int recipientId;
    private LocalDateTime timestamp;
    private int conversationId;

    public MessageDTO() {

    }
    public MessageDTO(String text, int senderId, int recipientId, int conversationId) {
        this.text = text;
        this.senderId = senderId;
        this.recipientId = recipientId;
        this.conversationId = conversationId;
        timestamp = LocalDateTime.now();
        isRead = false;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(int recipientId) {
        this.recipientId = recipientId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getConversationId() {
        return conversationId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }
}