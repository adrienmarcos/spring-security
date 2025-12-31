package br.com.buildrun.springsecurity.controller;

import br.com.buildrun.springsecurity.controller.dto.CreateUserDto;
import br.com.buildrun.springsecurity.controller.dto.response.UserResponseDto;
import br.com.buildrun.springsecurity.entities.User;
import br.com.buildrun.springsecurity.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final UserService  userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<Void> create(@RequestBody CreateUserDto createUserDto) {
        userService.create(createUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    public ResponseEntity<List<UserResponseDto>> listUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

}
