package com.betrybe.agrix;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.betrybe.agrix.services.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testes unitários para TokenService")
public class TokenServiceTest {

  private TokenService tokenService;

  @BeforeEach
  void setUp() {
    tokenService = new TokenService("test_secret_key_1234567890");
  }

  @Test
  @DisplayName("Deveria gerar token válido e recuperar subject (username)")
  void generateAndValidateTokenSuccess() {
    String username = "testuser";

    String token = tokenService.generateToken(username);

    assertNotNull(token);
    String subject = tokenService.validateToken(token);
    assertEquals(username, subject);
  }

  @Test
  @DisplayName("Deveria lançar erro ao validar token inválido")
  void validateInvalidTokenThrows() {
    assertThrows(Exception.class, () -> tokenService.validateToken("token.invalido.aqui"));
  }
}
