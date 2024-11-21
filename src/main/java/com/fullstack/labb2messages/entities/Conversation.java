package com.fullstack.labb2messages.entities;

import jakarta.persistence.*;

//import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "T_conversation")
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "conversationId")
    private Long conversationId;

    @Column(name = "latestMessage")
    private String latestMessage;

    // Define relationships
    @ManyToOne
    @JoinColumn(name = "userId1", referencedColumnName = "userId", nullable = false)
    private User user1;

    @ManyToOne
    @JoinColumn(name = "userId2", referencedColumnName = "userId", nullable = false)
    private User user2;

    @Column(name = "time")
    private LocalDateTime time;
    public Conversation() {}
    public Conversation(String latestMessage, User user1, User user2, LocalDateTime time) {
        this.latestMessage = latestMessage;
        this.user1 = user1;
        this.user2 = user2;
        this.time = time;
    }

    // Getters and setters
    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }

    public String getLatestMessage() {
        return latestMessage;
    }

    public void setLatestMessage(String latestMessage) {
        this.latestMessage = latestMessage;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}