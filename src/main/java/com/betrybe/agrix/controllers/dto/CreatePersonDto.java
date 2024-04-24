package com.betrybe.agrix.controllers.dto;

import com.betrybe.agrix.models.entities.Person;
import com.betrybe.agrix.security.Role;

/**
 * DTO de criação do person, a ser passado como exemplo para usuário.
 *
 * @param username Username a ser criado.
 * @param password Senha a ser criado.
 * @param role Nível de acesso a ser criado.
 */
public record CreatePersonDto(String username, String password, String role) {
  public Person dtoToEntity() {
    return new Person(username, password, role);
  }

}
