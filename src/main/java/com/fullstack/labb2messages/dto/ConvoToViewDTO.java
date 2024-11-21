package com.fullstack.labb2messages.dto;

import java.time.LocalDateTime;

public class ConvoToViewDTO {
    private int conversationId;
    private String latestMessage;
    private int withUserId;
    private String withUserName;
    private LocalDateTime time;

    public ConvoToViewDTO(int conversationId,String latestMessage, int withUserId, String withUserName, LocalDateTime time) {
        this.conversationId = conversationId;
        this.latestMessage = latestMessage;
        this.withUserId = withUserId;
        this.withUserName = withUserName;
        this.time = time;
    }

    public int getConversationId() {
        return conversationId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }

    public String getLatestMessage() {
        return latestMessage;
    }

    public void setLatestMessage(String latestMessage) {
        this.latestMessage = latestMessage;
    }

    public int getWithUserId() {
        return withUserId;
    }

    public void setWithUserId(int withUserId) {
        this.withUserId = withUserId;
    }

    public String getWithUserName() {
        return withUserName;
    }

    public void setWithUserName(String withUserName) {
        this.withUserName = withUserName;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
