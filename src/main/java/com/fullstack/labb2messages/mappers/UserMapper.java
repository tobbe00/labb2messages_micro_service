package com.fullstack.labb2messages.mappers;

import com.fullstack.labb2messages.dto.UserDTO;
import com.fullstack.labb2messages.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements Mapper<User, UserDTO>{

    private ModelMapper mapper;
    public UserMapper() {
        mapper = new ModelMapper();
    }

    @Override
    public UserDTO mapToDTO(User user) {
        return mapper.map(user, UserDTO.class);
    }

    @Override
    public User mapToEntity(UserDTO userDTO) {
        return mapper.map(userDTO, User.class);
    }
}
