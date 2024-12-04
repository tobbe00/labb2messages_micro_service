package com.fullstack.labb2messages.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "T_message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer messageId;

    @Column(name = "isRead")
    private Boolean isRead;

    @Column(name = "text", nullable = false, columnDefinition = "TEXT")
    private String text;

    @ManyToOne
    @JoinColumn(name = "senderId", referencedColumnName = "userId", nullable = false,
            foreignKey = @ForeignKey(name = "fk_message_sender"))
    private User sender;

    @ManyToOne
    @JoinColumn(name = "recipientId", referencedColumnName = "userId", nullable = false,
            foreignKey = @ForeignKey(name = "fk_message_recipient"))
    private User recipient;

    @Column(name = "time", nullable = false)
    private LocalDateTime time;

    @ManyToOne
    @JoinColumn(name = "conversationId", referencedColumnName = "conversationId", nullable = false,
            foreignKey = @ForeignKey(name = "fk_message_conversation"))
    private Conversation conversation;  // Add foreign key to the conversation

    // Constructors
    public Message() {}

    public Message( String text, User sender, User recipient,Conversation conversation, LocalDateTime time) {

        this.text = text;
        this.sender = sender;
        this.recipient = recipient;
        this.time = time;
        this.isRead = false;
        this.conversation = conversation;
    }

    // Getters and Setters
    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public Integer getMessageId() {
        return messageId;
    }
    public Boolean getRead() {
        return isRead;
    }
    public void setRead(Boolean read) {
        this.isRead = read;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}