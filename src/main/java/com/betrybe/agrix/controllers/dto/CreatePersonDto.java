package com.betrybe.agrix.controllers.dto;

import com.betrybe.agrix.models.entities.Person;
import com.betrybe.agrix.security.Role;

public record CreatePersonDto(String username, String password, Role role) {
  public Person dtoToEntity() {
    return new Person(username, password, role);
  }

}
