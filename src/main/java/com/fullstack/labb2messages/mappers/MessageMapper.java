package com.fullstack.labb2messages.mappers;

import com.fullstack.labb2messages.dto.MessageDTO;
import com.fullstack.labb2messages.entities.Message;
import com.fullstack.labb2messages.entities.User;
import com.fullstack.labb2messages.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MessageMapper implements Mapper<Message, MessageDTO> {
    private ModelMapper mapper;
    private UserRepository userRepository;
    public MessageMapper(ModelMapper mapper, UserRepository userRepository) {
        this.mapper = mapper;
        this.userRepository = userRepository;

        // Customize mapping for nested fields, if necessary
        this.mapper.typeMap(Message.class, MessageDTO.class).addMappings(m -> {
            m.map(src -> src.getSender().getUserId(), MessageDTO::setSenderId);
            m.map(src -> src.getRecipient().getUserId(), MessageDTO::setRecipientId);
            m.map(Message::getTime, MessageDTO::setTimestamp); // Map time to timestamp
            // Map other nested or custom fields as needed
        });

        // Optionally, add mapping from MessageDTO to Message
        this.mapper.typeMap(MessageDTO.class, Message.class).addMappings(m -> {
            m.skip(Message::setSender);  // Skip or map sender explicitly if required
            m.skip(Message::setRecipient);  // Skip or map recipient explicitly if required
            m.map(MessageDTO::getTimestamp, Message::setTime);
            // Add other specific mappings if necessary
        });

    }
    @Override
    public MessageDTO mapToDTO(Message message) {
        return mapper.map(message, MessageDTO.class);
    }

    @Override
    public Message mapToEntity(MessageDTO messageDTO) {
        Message message = mapper.map(messageDTO, Message.class);

        // Find the sender and recipient by ID and set them in the Message entity
        Optional<User> sender = userRepository.findById(messageDTO.getSenderId());

        Optional<User> recipient = userRepository.findById(messageDTO.getRecipientId());
        User s=sender.get();
        User r=recipient.get();

        message.setSender(s);
        message.setRecipient(r);

        return message;
    }
}

