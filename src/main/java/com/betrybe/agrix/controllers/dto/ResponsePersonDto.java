package com.betrybe.agrix.controllers.dto;

import com.betrybe.agrix.models.entities.Person;
import com.betrybe.agrix.security.Role;

/**
 * DTO responsável por retirar a senha e retornar o Person para usuário.
 *
 * @param id Id do usuário.
 * @param username Username do usuário.
 * @param role Nível de acesso do usuário.
 */
public record ResponsePersonDto(Long id, String username, String role) {

  /**
   * Método estático responsável por instanciar o DTO para facilidade na chamada.
   *
   * @param person Person completo a ser retirado a senha.
   * @return Retorna um Person com id, mas sem a senha.
   */
  public static ResponsePersonDto toDto(Person person) {
    return new ResponsePersonDto(
        person.getId(),
        person.getUsername(),
        person.getRole()
    );
  }
}
