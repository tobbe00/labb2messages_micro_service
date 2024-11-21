package com.fullstack.labb2messages.services;

import com.fullstack.labb2messages.dto.MessageDTO;
import com.fullstack.labb2messages.entities.Message;
import com.fullstack.labb2messages.mappers.Mapper;
import com.fullstack.labb2messages.repositories.ConversationRepository;
import com.fullstack.labb2messages.repositories.MessageRepository;
import com.fullstack.labb2messages.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;
    private final Mapper<Message,MessageDTO> messageMapper;

    @Autowired
    public MessageService(MessageRepository messageRepository, ConversationRepository conversationRepository, UserRepository userRepository, Mapper<Message, MessageDTO> mapper) {
        this.messageRepository = messageRepository;
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
        this.messageMapper = mapper;
    }

    // Method to get all received messages for a specific user
    public List<MessageDTO> getReceivedMessages(Long receiverId) {
        List<Message> messages=messageRepository.findByRecipient_UserId(receiverId);
        List<MessageDTO> dtos=new ArrayList<>();
        for(Message message:messages){
            dtos.add(messageMapper.mapToDTO(message));
        }
        return dtos;
    }

    public MessageDTO createMessage(MessageDTO messageDTO) {
        Message m= messageMapper.mapToEntity(messageDTO);
        m.setConversation(conversationRepository.getOne(messageDTO.getConversationId()));
        m.setRecipient(userRepository.getOne(messageDTO.getRecipientId()));
        m.setSender(userRepository.getOne(messageDTO.getSenderId()));
        m.setText(messageDTO.getText());
        m.setTime(LocalDateTime.now());
        Message message=messageRepository.save(m);
        MessageDTO mDTO= messageMapper.mapToDTO(message);
        return mDTO;
    }
    public MessageDTO createMessageInConvo(MessageDTO messageDTO) {
        System.out.println(messageDTO.getText());
        System.out.println(messageDTO.getSenderId());
        System.out.println(messageDTO.getRecipientId());//det är denna vi inte har
        System.out.println(messageDTO.getConversationId());
        int id1=conversationRepository.findUserId1ByConversationId(messageDTO.getConversationId());
        int id2=conversationRepository.findUserId2ByConversationId(messageDTO.getConversationId());
        if (id1==messageDTO.getSenderId()) {
            messageDTO.setRecipientId(id2);
        }else {
            messageDTO.setRecipientId(id1);
        }
        messageDTO.setTimestamp(LocalDateTime.now());
        System.out.println(messageDTO.getText());
        System.out.println(messageDTO.getSenderId());
        System.out.println(messageDTO.getRecipientId());//det är denna vi inte har
        System.out.println(messageDTO.getConversationId());

        Message m=messageRepository.save(messageMapper.mapToEntity(messageDTO));
        return messageMapper.mapToDTO(m);
    }

    public List<MessageDTO> getMessagesFromConversation(Long conversationId) {
        List<MessageDTO> messageDTOList=new ArrayList<>();
        List<Message>messages=messageRepository.findByConversation_ConversationId(conversationId);
        for (Message message:messages) {
            messageDTOList.add(messageMapper.mapToDTO(message));
        }

        return messageDTOList;
    }

}
