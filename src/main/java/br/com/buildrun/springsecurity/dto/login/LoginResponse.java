package br.com.buildrun.springsecurity.dto.login;

public record LoginResponse(String accessToken, Long expiresIn) {

}
