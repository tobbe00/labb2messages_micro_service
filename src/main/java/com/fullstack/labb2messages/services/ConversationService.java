package com.fullstack.labb2messages.services;

import com.fullstack.labb2messages.dto.ConversationDTO;
import com.fullstack.labb2messages.entities.Conversation;
import com.fullstack.labb2messages.mappers.Mapper;
import com.fullstack.labb2messages.repositories.ConversationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Service
public class ConversationService {
    @Autowired
    private final ConversationRepository conversationRepository;

    private final Mapper<Conversation, ConversationDTO> conversationMapper;

    public ConversationService(ConversationRepository conversationRepository, Mapper<Conversation, ConversationDTO> conversationMapper) {
        this.conversationRepository = conversationRepository;
        this.conversationMapper = conversationMapper;
    }
    public List<ConversationDTO> getAllConversationsByUserId(int userId) {
        List<Conversation> conversations=new ArrayList<>();
        conversations.addAll(conversationRepository.findAllByuser1_userId(userId));
        conversations.addAll(conversationRepository.findAllByuser2_userId(userId));
        List<ConversationDTO> conversationDTOS=new ArrayList<>();
        for (Conversation conversation : conversations) {
            conversationDTOS.add(conversationMapper.mapToDTO(conversation));
        }
        return conversationDTOS;
    }

    public ConversationDTO createConversation(ConversationDTO conversationDTO) {

        Conversation conversation=conversationMapper.mapToEntity(conversationDTO);
        conversation.setTime(LocalDateTime.now());
        ConversationDTO c=conversationMapper.mapToDTO(conversationRepository.save(conversation));
        return c;
    }

}