package com.betrybe.agrix;

import static  org.junit.jupiter.api.Assertions.*;

import com.betrybe.agrix.error.PersonNotFoundException;
import com.betrybe.agrix.models.entities.Person;
import com.betrybe.agrix.services.PersonService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Teste Person da camada de logica usando BD H2.")
public class PersonServiceTest {

  @Autowired
  PersonService personService;

  @Test
  @Transactional
  @DirtiesContext
  @DisplayName("Deveria criar um person corretamente")
  public void createPersonTest() {
    Person person = new Person();
    person.setUsername("Ludson");
    person.setPassword("123456");
    person.setRole("USER");

    Person savedPerson = personService.create(person);

    assertNotNull(savedPerson.getId());
    assertEquals(person.getUsername(), savedPerson.getUsername());
    assertEquals(person.getPassword(), savedPerson.getPassword());
    assertEquals(person.getRole(), savedPerson.getRole());
  }

  @Test
  @DisplayName("Deveria retornar um Person pelo Username")
  @Transactional
  @DirtiesContext
  public void getPersonByUsernameTest() {
    Person person = new Person();
    person.setUsername("Ludson");
    person.setPassword("123456");
    person.setRole("USER");

    Person savedPerson = personService.create(person);

    Person personByUsername = personService.getPersonByUsername(savedPerson.getUsername());

    assertEquals(savedPerson.getId(), personByUsername.getId());
    assertEquals(savedPerson.getUsername(), personByUsername.getUsername());
    assertEquals(savedPerson.getPassword(), personByUsername.getPassword());
    assertEquals(savedPerson.getRole(), personByUsername.getRole());
  }

  @Test
  @DisplayName("Deveria retornar um Person pelo id")
  @Transactional
  @DirtiesContext
  public void getPersonByIdTest() {
    Person person = new Person();
    person.setUsername("Ludson");
    person.setPassword("123456");
    person.setRole("USER");

    Person savedPerson = personService.create(person);

    Person personById = personService.getPersonById(savedPerson.getId());

    assertEquals(savedPerson.getId(), personById.getId());
    assertEquals(savedPerson.getUsername(), personById.getUsername());
    assertEquals(savedPerson.getPassword(), personById.getPassword());
    assertEquals(savedPerson.getRole(), personById.getRole());
  }

  @Test
  @DisplayName("Deveria lançar uma exceção caso não encontre um id fornecido")
  public void getPersonByIdThrowsTest() {
    assertThrows(PersonNotFoundException.class, () -> personService.getPersonById(123L));
  }

  @Test
  @DisplayName("Deveria lançar uma exceção caso não encontre um nome fornecido")
  public void getPersonByUsernameThrowsTest() {
    assertThrows(PersonNotFoundException.class,
        () -> personService.getPersonByUsername("não fornecido"));
  }
}
