package com.betrybe.agrix.controllers.dto;

/**
 * DTO para autenticação.
 *
 * @param username nome de usuário.
 * @param password senha.
 */
public record AuthDto(String username, String password) {

}
