package com.betrybe.agrix.controllers;

import com.betrybe.agrix.controllers.dto.CreatePersonDto;
import com.betrybe.agrix.controllers.dto.ResponsePersonDto;
import com.betrybe.agrix.models.entities.Person;
import com.betrybe.agrix.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller da entidade Person.
 */
@RestController
@RequestMapping("/persons")
public class PersonController {

  private final PersonService personService;

  @Autowired
  public PersonController(PersonService personService) {
    this.personService = personService;
  }

  /**
   * Método responsável por criar uma nova Person.
   *
   * @param createPersonDto Dados para criação de um Person (username, password e role).
   * @return Retorna status 201 e o novo person, sem password e com o novo id.
   */
  @PostMapping
  public ResponseEntity<ResponsePersonDto> insertPerson(
      @RequestBody CreatePersonDto createPersonDto
  ) {
    Person person = personService.create(createPersonDto.dtoToEntity());

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ResponsePersonDto.toDto(person));
  }
}
