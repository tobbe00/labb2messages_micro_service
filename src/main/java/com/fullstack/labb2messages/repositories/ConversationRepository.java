package com.fullstack.labb2messages.repositories;

import com.fullstack.labb2messages.entities.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConversationRepository extends JpaRepository<Conversation, Integer> {
    List<Conversation> findAllByuser1_userId(int userId);
    List<Conversation> findAllByuser2_userId(int userId);

    @Query("SELECT u.userId FROM Conversation c JOIN c.user1 u WHERE c.conversationId = :conversationId")
    Integer findUserId1ByConversationId(@Param("conversationId") int conversationId);



    @Query("SELECT u.userId FROM Conversation c JOIN c.user2 u WHERE c.conversationId = :conversationId")
    Integer findUserId2ByConversationId(@Param("conversationId") int conversationId);

}
