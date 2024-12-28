package com.fullstack.labb2messages.controllers;

import com.fullstack.labb2messages.dto.ConversationDTO;
import com.fullstack.labb2messages.dto.ConvoToViewDTO;
import com.fullstack.labb2messages.dto.UserDTO;
import com.fullstack.labb2messages.services.ConversationService;
import com.fullstack.labb2messages.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {
    private final ConversationService conversationService;
    private final UserService userService;
    @Autowired
    public ConversationController(ConversationService conversationService, UserService userService) {
        this.conversationService = conversationService;
        this.userService = userService;
    }

    // Endpoint to get all received messages for a user
    @PreAuthorize("hasAnyRole('worker', 'doctor', 'patient')")
    @GetMapping("/yourConversations/{userId}")
    public List<ConvoToViewDTO> getYourConversations(@PathVariable int userId) {
        List<ConversationDTO> conversationDTOS= conversationService.getAllConversationsByUserId(userId);
        List<UserDTO> userDTOS=new ArrayList<>();
        List<ConvoToViewDTO> convoToViewDTOS=new ArrayList<>();
        for(ConversationDTO conversationDTO:conversationDTOS){
            UserDTO u;
            if (conversationDTO.getUserId1()==userId){
                u=userService.getUserById(conversationDTO.getUserId2());
            }else {
                u= userService.getUserById(conversationDTO.getUserId1());

            }
            convoToViewDTOS.add(new ConvoToViewDTO(conversationDTO.getConversationId(),conversationDTO.getLatestMessage(),u.getUserId(),u.getName(),conversationDTO.getTime()));

        }

        return convoToViewDTOS;

    }


}