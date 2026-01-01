package br.com.buildrun.springsecurity.service;

import br.com.buildrun.springsecurity.dto.user.UserCreateRequest;
import br.com.buildrun.springsecurity.entities.User;
import br.com.buildrun.springsecurity.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Should throw an error when user already exists in DB")
    void shouldThrowErrorWhenUserAlreadyExists() {
        // Arrange
        var userDto = new UserCreateRequest("adrien", "123");
        var existingUser = new User();
        existingUser.setUsername("adrien");

        Mockito.when(userRepository.findByUsername(userDto.username())).thenReturn(Optional.of(existingUser));

        // Act
        assertThrows(ResponseStatusException.class, () -> {
            userService.create(userDto);
        });

        // Assert
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(userDto.username());
        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any());
    }

}