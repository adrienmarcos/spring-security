package br.com.buildrun.springsecurity.service;

import br.com.buildrun.springsecurity.dto.user.UserCreateRequest;
import br.com.buildrun.springsecurity.dto.user.UserResponse;
import br.com.buildrun.springsecurity.entities.Role;
import br.com.buildrun.springsecurity.entities.User;
import br.com.buildrun.springsecurity.repository.RoleRepository;
import br.com.buildrun.springsecurity.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Should throw an error when user already exists in DB")
    void shouldThrowErrorWhenUserAlreadyExists() {
        // Arrange
        var userDto = new UserCreateRequest("testUser", "123");
        var existingUser = new User();
        existingUser.setUsername("testUser");

        Mockito.when(userRepository.findByUsername(userDto.username())).thenReturn(Optional.of(existingUser));

        // Act
        assertThrows(ResponseStatusException.class, () -> {
            userService.create(userDto);
        });

        // Assert
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(userDto.username());
        Mockito.verify(userRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    @DisplayName("Should create an user with success when provided valid data")
    void shouldCreateAnUserWithSuccessWhenProvidedValidData() {
        // Arrange
        var userDto = new UserCreateRequest("newUser", "password123");
        var basicRole = new Role();
        basicRole.setName(Role.Values.BASIC.name());

        Mockito.when(userRepository.findByUsername(userDto.username())).thenReturn(Optional.empty());
        Mockito.when(roleRepository.findByName(Role.Values.BASIC.name())).thenReturn(basicRole);
        Mockito.when(bCryptPasswordEncoder.encode(userDto.password())).thenReturn("senha_criptografada");

        // Act
        userService.create(userDto);

        // Assert
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(userDto.username());
        Mockito.verify(userRepository, Mockito.times(1)).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals("newUser", savedUser.getUsername());
        assertEquals("senha_criptografada", savedUser.getPassword());
        assertTrue(savedUser.getRoles().contains(basicRole));
    }

    @Test
    @DisplayName("Should return an UserResponse List")
    void shoudlReturnUserResponseList() {
        // Arrange
        var basicRole = new Role();
        basicRole.setName(Role.Values.BASIC.name());

        var adminRole = new Role();
        adminRole.setName(Role.Values.ADMIN.name());

        var user1 = new User();
        user1.setUsername("testeUser");
        user1.setRoles(Set.of(basicRole));

        var user2 = new User();
        user2.setUsername("anotherUser");
        user2.setRoles(Set.of(basicRole));

        Mockito.when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        // Act
        List<UserResponse> responseList = userService.findAll();

        // Assert
        assertNotNull(responseList);
        assertEquals(2, responseList.size());
        assertEquals(user1.getUsername(), responseList.get(0).username());
        assertEquals(1, responseList.get(0).roles().size());
        assertEquals(user2.getUsername(), responseList.get(1).username());
        assertEquals(1, responseList.get(1).roles().size());

        Mockito.verify(userRepository, Mockito.times(1)).findAll();
    }

}