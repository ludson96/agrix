package com.betrybe.agrix;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Testes de integração para autenticação e rotas públicas/protegidas")
public class AuthIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  @DirtiesContext
  @DisplayName("Deveria registrar novo usuário e realizar login obtendo token JWT")
  void registerAndLoginFlow() throws Exception {
    Map<String, String> userPayload = Map.of(
        "username", "admin_integration",
        "password", "secret123",
        "role", "ADMIN"
    );

    // 1. Cadastrar usuário
    mockMvc.perform(post("/persons")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userPayload)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.username").value("admin_integration"))
        .andExpect(jsonPath("$.role").value("ADMIN"));

    // 2. Realizar login com as credenciais
    Map<String, String> loginPayload = Map.of(
        "username", "admin_integration",
        "password", "secret123"
    );

    mockMvc.perform(post("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(loginPayload)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").isString());
  }

  @Test
  @DisplayName("Deveria retornar 403 Forbidden ao acessar rota protegida sem token")
  void accessProtectedEndpointWithoutToken() throws Exception {
    mockMvc.perform(get("/farms"))
        .andExpect(status().isForbidden());
  }
}
