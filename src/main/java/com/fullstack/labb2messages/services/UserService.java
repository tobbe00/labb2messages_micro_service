package com.fullstack.labb2messages.services;

import com.fullstack.labb2messages.dto.UserDTO;
import com.fullstack.labb2messages.entities.User;
import com.fullstack.labb2messages.mappers.Mapper;
import com.fullstack.labb2messages.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final Mapper<User, UserDTO> userMapper;

    public UserService( Mapper<User, UserDTO> userMapper) {

        this.userMapper = userMapper;

    }

    public UserDTO getUserById(int userId) {
        User user=userRepository.findById(userId).orElse(null);
        return userMapper.mapToDTO(user);
    }


}