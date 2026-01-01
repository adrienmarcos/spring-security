package br.com.buildrun.springsecurity.controller.dto.login;

public record LoginResponse(String accessToken, Long expiresIn) {

}
