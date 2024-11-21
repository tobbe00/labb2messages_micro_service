package com.fullstack.labb2messages.controllers;
import com.fullstack.labb2messages.dto.ConversationDTO;
import com.fullstack.labb2messages.dto.FirstMessageDTO;
import com.fullstack.labb2messages.dto.MessageDTO;
import com.fullstack.labb2messages.services.ConversationService;
import com.fullstack.labb2messages.services.MessageService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;
    private final ConversationService conversationService;

    @Autowired
    public MessageController(MessageService messageService, ConversationService conversationService) {
        this.messageService = messageService;
        this.conversationService = conversationService;
    }

    // Endpoint to get all received messages for a user
    @GetMapping("/received/{userId}")
    public List<MessageDTO> getReceivedMessages(@PathVariable Long userId) {
        return messageService.getReceivedMessages(userId);
    }

    @PostMapping("/sendFirstMessage")
    @Transactional
    public ConversationDTO createFirstMessage(@RequestBody FirstMessageDTO firstMessageDTO) {
        ConversationDTO cDTO=new ConversationDTO(firstMessageDTO.getUserId(),firstMessageDTO.getEmplyeeId(),firstMessageDTO.getMessage());

        //ConversationDTO conversationDTO = new ConversationDTO();
        // conversationDTO.setLatestMessage(firstMessageDTO.getMessage());
        //conversationDTO.setUserId1(firstMessageDTO.getUserId());
        //conversationDTO.setUserId2(firstMessageDTO.getEmplyeeId());

        //en möjlig lösning är att ge conversations titles. för nu tänker jag mer i chatt banor så det kan va en användning för title.

        ConversationDTO c=conversationService.createConversation(cDTO);
        MessageDTO messageDTO=new MessageDTO(firstMessageDTO.getMessage(), firstMessageDTO.getUserId(), firstMessageDTO.getEmplyeeId(), c.getConversationId());
        messageService.createMessage(messageDTO);

        return c;
    }

    @GetMapping("/getConvoMessages/{conversationId}")
    public List<MessageDTO> getConvoMessages(@PathVariable Long conversationId) {
        return messageService.getMessagesFromConversation(conversationId);
    }
    @PostMapping("/sendMessageInConvo")
    public MessageDTO createMessageInConvo(@RequestBody MessageDTO messageDTO) {

        return messageService.createMessageInConvo(messageDTO);
    }
}