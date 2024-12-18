package com.fullstack.labb2messages.services;

import com.fullstack.labb2messages.dto.ConversationDTO;
import com.fullstack.labb2messages.entities.Conversation;
import com.fullstack.labb2messages.entities.User;
import com.fullstack.labb2messages.mappers.Mapper;
import com.fullstack.labb2messages.repositories.ConversationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ConversationServiceTest {

    private ConversationService conversationService;

    @Mock
    private ConversationRepository conversationRepository;

    @Mock
    private Mapper<Conversation, ConversationDTO> conversationMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        conversationService = new ConversationService(conversationRepository, conversationMapper);
    }

    @Test
    void testGetAllConversationsByUserId() {
        // Arrange
        int userId = 1;
        User user1 = new User();
        user1.setUserId(userId);

        User user2 = new User();
        user2.setUserId(2);

        Conversation conversation1 = new Conversation("Hello", user1, user2, LocalDateTime.now());
        conversation1.setConversationId(101L);

        Conversation conversation2 = new Conversation("Hi", user2, user1, LocalDateTime.now());
        conversation2.setConversationId(102L);

        List<Conversation> conversations1 = List.of(conversation1);
        List<Conversation> conversations2 = List.of(conversation2);

        when(conversationRepository.findAllByuser1_userId(userId)).thenReturn(conversations1);
        when(conversationRepository.findAllByuser2_userId(userId)).thenReturn(conversations2);
        when(conversationMapper.mapToDTO(any(Conversation.class)))
                .thenAnswer(invocation -> {
                    Conversation convo = invocation.getArgument(0);
                    return new ConversationDTO(
                            convo.getUser1().getUserId(),
                            convo.getUser2().getUserId(),
                            convo.getLatestMessage(),
                            convo.getConversationId().intValue()
                    );
                });

        // Act
        List<ConversationDTO> conversationDTOs = conversationService.getAllConversationsByUserId(userId);

        // Assert
        assertEquals(2, conversationDTOs.size());
        verify(conversationRepository, times(1)).findAllByuser1_userId(userId);
        verify(conversationRepository, times(1)).findAllByuser2_userId(userId);
        verify(conversationMapper, times(2)).mapToDTO(any(Conversation.class));
    }

    @Test
    void testCreateConversation() {
        // Arrange
        User user1 = new User();
        user1.setUserId(1);

        User user2 = new User();
        user2.setUserId(2);

        ConversationDTO conversationDTO = new ConversationDTO(1, 2, "Hello");
        Conversation conversation = new Conversation("Hello", user1, user2, LocalDateTime.now());
        conversation.setConversationId(101L);

        when(conversationMapper.mapToEntity(any(ConversationDTO.class))).thenReturn(conversation);
        when(conversationRepository.save(any(Conversation.class))).thenReturn(conversation);
        when(conversationMapper.mapToDTO(any(Conversation.class)))
                .thenAnswer(invocation -> {
                    Conversation convo = invocation.getArgument(0);
                    return new ConversationDTO(
                            convo.getUser1().getUserId(),
                            convo.getUser2().getUserId(),
                            convo.getLatestMessage(),
                            convo.getConversationId().intValue()
                    );
                });

        // Act
        ConversationDTO createdConversation = conversationService.createConversation(conversationDTO);

        // Assert
        assertEquals("Hello", createdConversation.getLatestMessage());
        assertEquals(1, createdConversation.getUserId1());
        assertEquals(2, createdConversation.getUserId2());
        verify(conversationMapper, times(1)).mapToEntity(any(ConversationDTO.class));
        verify(conversationRepository, times(1)).save(any(Conversation.class));
        verify(conversationMapper, times(1)).mapToDTO(any(Conversation.class));
    }
}
