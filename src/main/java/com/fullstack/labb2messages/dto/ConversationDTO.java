package com.fullstack.labb2messages.dto;

import java.time.LocalDateTime;

public class ConversationDTO {
    private int conversationId;
    private String latestMessage;
    private int userId1;
    private int userId2;
    private LocalDateTime time;


    public ConversationDTO( int userId1, int userId2,String latestMessage ) {

        this.latestMessage =latestMessage ;
        this.userId1 = userId1;
        this.userId2 = userId2;
        this.time = LocalDateTime.now();
    }
    public ConversationDTO( int userId1, int userId2,String latestMessage, int conversationId ) {

        this.latestMessage =latestMessage ;
        this.conversationId = conversationId;
        this.userId1 = userId1;
        this.userId2 = userId2;
        this.time = LocalDateTime.now();
    }
    public ConversationDTO() {
        super();
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

    public int getUserId1() {
        return userId1;
    }

    public void setUserId1(int userId1) {
        this.userId1 = userId1;
    }

    public int getUserId2() {
        return userId2;
    }

    public void setUserId2(int userId2) {
        this.userId2 = userId2;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}