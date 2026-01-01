package br.com.buildrun.springsecurity.service;

import br.com.buildrun.springsecurity.controller.dto.user.UserCreateRequest;
import br.com.buildrun.springsecurity.controller.dto.user.UserResponse;
import br.com.buildrun.springsecurity.entities.Role;
import br.com.buildrun.springsecurity.entities.User;
import br.com.buildrun.springsecurity.repository.RoleRepository;
import br.com.buildrun.springsecurity.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    public void create(UserCreateRequest createUserDto) {
        var userFromDb = userRepository.findByUsername(createUserDto.username());
        if (userFromDb.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_CONTENT);
        }
        
        var basicRole = roleRepository.findByName(Role.Values.BASIC.name());
        var user = new User();
        user.setUsername(createUserDto.username());
        user.setPassword(bCryptPasswordEncoder.encode(createUserDto.password()));
        user.setRoles(Set.of(basicRole));
        userRepository.save(user);
    }

    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponse(user.getUsername(), user.getRoles()))
                .toList();
    }

}
