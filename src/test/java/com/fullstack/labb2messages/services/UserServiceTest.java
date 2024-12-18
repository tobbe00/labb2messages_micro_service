package com.fullstack.labb2messages.services;

import com.fullstack.labb2messages.dto.UserDTO;
import com.fullstack.labb2messages.entities.User;
import com.fullstack.labb2messages.mappers.Mapper;
import com.fullstack.labb2messages.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;
    private Mapper<User, UserDTO> userMapper;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userMapper = mock(Mapper.class);

        userService = new UserService(userMapper);
        // Sätt userRepository via reflection (eftersom den är @Autowired)
        org.springframework.test.util.ReflectionTestUtils.setField(userService, "userRepository", userRepository);
    }

    @Test
    void testGetUserById_UserExists() {
        // Arrange
        User user = createMockUser(1, "John Doe", "john@example.com", 30);
        UserDTO userDTO = new UserDTO(1, "John Doe", "john@example.com", 30);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userMapper.mapToDTO(user)).thenReturn(userDTO);

        // Act
        UserDTO result = userService.getUserById(1);

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        assertEquals("john@example.com", result.getEmail());
        assertEquals(30, result.getAge());
        verify(userRepository, times(1)).findById(1);
        verify(userMapper, times(1)).mapToDTO(user);
    }

    @Test
    void testGetUserById_UserDoesNotExist() {
        // Arrange
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        // Act
        UserDTO result = userService.getUserById(1);

        // Assert
        assertNull(result);
        verify(userRepository, times(1)).findById(1);
        verify(userMapper, never()).mapToDTO(any(User.class));
    }

    // Helper method to create a mock User
    private User createMockUser(int id, String name, String email, int age) {
        User user = new User();
        user.setUserId(id);
        user.setName(name);
        user.setEmail(email);
        user.setAge(age);
        return user;
    }
}
