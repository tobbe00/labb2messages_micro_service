package com.fullstack.labb2messages.services;

import com.fullstack.labb2messages.dto.MessageDTO;
import com.fullstack.labb2messages.entities.Conversation;
import com.fullstack.labb2messages.entities.Message;
import com.fullstack.labb2messages.entities.User;
import com.fullstack.labb2messages.mappers.Mapper;
import com.fullstack.labb2messages.repositories.ConversationRepository;
import com.fullstack.labb2messages.repositories.MessageRepository;
import com.fullstack.labb2messages.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MessageServiceTest {

    private MessageService messageService;
    private MessageRepository messageRepository;
    private ConversationRepository conversationRepository;
    private UserRepository userRepository;
    private Mapper<Message, MessageDTO> messageMapper;

    @BeforeEach
    void setUp() {
        messageRepository = mock(MessageRepository.class);
        conversationRepository = mock(ConversationRepository.class);
        userRepository = mock(UserRepository.class);
        messageMapper = mock(Mapper.class);

        messageService = new MessageService(messageRepository, conversationRepository, userRepository, messageMapper);
    }

    @Test
    void testGetReceivedMessages() {
        // Arrange
        Long recipientId = 1L;
        List<Message> messages = new ArrayList<>();
        messages.add(createMockMessage("Hello", 1L, 2L));
        messages.add(createMockMessage("Hi", 1L, 2L));

        when(messageRepository.findByRecipient_UserId(recipientId)).thenReturn(messages);
        when(messageMapper.mapToDTO(any(Message.class))).thenReturn(new MessageDTO());

        // Act
        List<MessageDTO> result = messageService.getReceivedMessages(recipientId);

        // Assert
        assertEquals(2, result.size());
        verify(messageRepository, times(1)).findByRecipient_UserId(recipientId);
        verify(messageMapper, times(2)).mapToDTO(any(Message.class));
    }

    @Test
    void testCreateMessage() {
        // Arrange
        MessageDTO messageDTO = new MessageDTO("Hello", 1, 2, 1);
        Message message = createMockMessage("Hello", 1L, 2L);
        Conversation conversation = new Conversation();
        conversation.setConversationId(1L);

        when(conversationRepository.findById(1)).thenReturn(Optional.of(conversation));
        when(userRepository.findById(1)).thenReturn(Optional.of(createMockUser(1L, "John")));
        when(userRepository.findById(2)).thenReturn(Optional.of(createMockUser(2L, "Jane")));
        when(messageMapper.mapToEntity(messageDTO)).thenReturn(message);
        when(messageRepository.save(message)).thenReturn(message);
        when(messageMapper.mapToDTO(message)).thenReturn(messageDTO);

        // Act
        MessageDTO result = messageService.createMessage(messageDTO);

        // Assert
        assertNotNull(result);
        assertEquals("Hello", result.getText());
        verify(messageRepository, times(1)).save(any(Message.class));
    }

    @Test
    void testCreateMessageInConvo() {
        // Arrange
        MessageDTO messageDTO = new MessageDTO("Hi", 1, 0, 1); // RecipientId = 0
        Conversation conversation = new Conversation();
        conversation.setConversationId(1L);

        when(conversationRepository.findUserId1ByConversationId(1)).thenReturn(1);
        when(conversationRepository.findUserId2ByConversationId(1)).thenReturn(2);
        when(messageMapper.mapToEntity(any(MessageDTO.class))).thenReturn(new Message());
        when(messageRepository.save(any(Message.class))).thenReturn(new Message());
        when(messageMapper.mapToDTO(any(Message.class))).thenReturn(messageDTO);

        // Act
        MessageDTO result = messageService.createMessageInConvo(messageDTO);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getRecipientId()); // Recipient ID ska uppdateras
        verify(messageRepository, times(1)).save(any(Message.class));
    }

    @Test
    void testGetMessagesFromConversation() {
        // Arrange
        Long conversationId = 1L;
        List<Message> messages = new ArrayList<>();
        messages.add(createMockMessage("Hello", 1L, 2L));
        messages.add(createMockMessage("Hi", 1L, 2L));

        when(messageRepository.findByConversation_ConversationId(conversationId)).thenReturn(messages);
        when(messageMapper.mapToDTO(any(Message.class))).thenReturn(new MessageDTO());

        // Act
        List<MessageDTO> result = messageService.getMessagesFromConversation(conversationId);

        // Assert
        assertEquals(2, result.size());
        verify(messageRepository, times(1)).findByConversation_ConversationId(conversationId);
        verify(messageMapper, times(2)).mapToDTO(any(Message.class));
    }

    // Helper methods
    private Message createMockMessage(String text, Long senderId, Long recipientId) {
        Message message = new Message();
        message.setText(text);

        User sender = new User();
        sender.setUserId(senderId.intValue());
        message.setSender(sender);

        User recipient = new User();
        recipient.setUserId(recipientId.intValue());
        message.setRecipient(recipient);

        message.setTime(LocalDateTime.now());
        return message;
    }

    private User createMockUser(Long id, String name) {
        User user = new User();
        user.setUserId(id.intValue());
        user.setName(name);
        return user;
    }
}
