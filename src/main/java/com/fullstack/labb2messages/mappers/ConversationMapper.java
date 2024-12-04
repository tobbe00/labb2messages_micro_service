package com.fullstack.labb2messages.mappers;

import com.fullstack.labb2messages.dto.ConversationDTO;
import com.fullstack.labb2messages.entities.Conversation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ConversationMapper implements Mapper<Conversation, ConversationDTO>{

    private ModelMapper mapper;
    public ConversationMapper(ModelMapper mapper) {
        this.mapper = mapper;

        // Customize mapping for nested fields like user details
        this.mapper.typeMap(Conversation.class, ConversationDTO.class).addMappings(m -> {
            m.map(src -> src.getUser1().getUserId(), ConversationDTO::setUserId1);
            m.map(src -> src.getUser2().getUserId(), ConversationDTO::setUserId2);
            // Map other specific fields as necessary
        });

        // Optionally add mappings for ConversationDTO to Conversation
        this.mapper.typeMap(ConversationDTO.class, Conversation.class).addMappings(m -> {
            m.skip(Conversation::setUser1);  // Skip or handle explicitly in the service layer
            m.skip(Conversation::setUser2);  // Skip or handle explicitly in the service layer
        });
    }


    @Override
    public ConversationDTO mapToDTO(Conversation conversation) {
        return mapper.map(conversation, ConversationDTO.class);
    }

    @Override
    public Conversation mapToEntity(ConversationDTO conversationDTO) {
        return mapper.map(conversationDTO, Conversation.class);
    }
}
