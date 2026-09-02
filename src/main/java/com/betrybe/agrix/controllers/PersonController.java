package com.betrybe.agrix.controllers;

import com.betrybe.agrix.controllers.dto.CreatePersonDto;
import com.betrybe.agrix.controllers.dto.ResponsePersonDto;
import com.betrybe.agrix.models.entities.Person;
import com.betrybe.agrix.services.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Pessoas (Usuários)", description = "Endpoints de gerenciamento e cadastro de pessoas/usuários")
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
  @Operation(summary = "Cadastrar usuário", description = "Cria um novo usuário no sistema com role (ADMIN, MANAGER ou USER)")
  @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso")
  @PostMapping
  public ResponseEntity<ResponsePersonDto> insertPerson(
      @RequestBody CreatePersonDto createPersonDto
  ) {
    Person person = personService.create(createPersonDto.dtoToEntity());

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ResponsePersonDto.toDto(person));
  }
}
