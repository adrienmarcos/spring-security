package br.com.buildrun.springsecurity.controller.dto.response;

import br.com.buildrun.springsecurity.entities.Role;
import java.util.Set;

public record UserResponseDto(String username, Set<Role> roles) {

}
