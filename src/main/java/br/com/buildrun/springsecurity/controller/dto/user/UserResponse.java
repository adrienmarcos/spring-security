package br.com.buildrun.springsecurity.controller.dto.user;

import br.com.buildrun.springsecurity.entities.Role;
import java.util.Set;

public record UserResponse(String username, Set<Role> roles) {

}
