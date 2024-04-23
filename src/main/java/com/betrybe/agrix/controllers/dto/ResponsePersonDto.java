package com.betrybe.agrix.controllers.dto;

import com.betrybe.agrix.models.entities.Person;
import com.betrybe.agrix.security.Role;

public record ResponsePersonDto(Long id, String username, Role role) {

  public static ResponsePersonDto toDto(Person person) {
    return new ResponsePersonDto(
        person.getId(),
        person.getUsername(),
        person.getRole()
    );
  }
}
