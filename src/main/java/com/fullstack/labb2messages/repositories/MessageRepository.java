package com.fullstack.labb2messages.repositories;

import com.fullstack.labb2messages.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    // Custom query to find all messages received by a user
    List<Message> findByRecipient_UserId(Long userId);

    // Custom query to find all messages by conversationId
    List<Message> findByConversation_ConversationId(Long conversationId);
}