package com.betrybe.agrix.controllers;

import com.betrybe.agrix.controllers.dto.AuthDto;
import com.betrybe.agrix.controllers.dto.TokenDto;
import com.betrybe.agrix.error.CustomError;
import com.betrybe.agrix.services.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Classe controller da Auth, responsável por autenticação e login.
 */
@Tag(name = "Autenticação", description = "Endpoints para login e obtenção de token JWT")
@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final TokenService tokenService;

  @Autowired
  public AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
    this.authenticationManager = authenticationManager;
    this.tokenService = tokenService;
  }

  /**
   * Método responsável pelo login.
   *
   * @param authDto username e password passado no corpo para login.
   * @return retorna um token para autenticação futura.
   * @throws CustomError Caso o username ou password estejam incorretos retorna uma exceção.
   */
  @Operation(summary = "Realizar login", description = "Autentica usuário existente e retorna o Bearer Token JWT")
  @ApiResponse(responseCode = "200", description = "Login realizado com sucesso")
  @ApiResponse(responseCode = "403", description = "Username ou senha incorretos")
  @PostMapping("/login")
  public TokenDto login(@RequestBody AuthDto authDto) throws CustomError {
    try {

      UsernamePasswordAuthenticationToken usernamePassword =
          new UsernamePasswordAuthenticationToken(
          authDto.username(), authDto.password());

      Authentication auth = authenticationManager.authenticate(usernamePassword);

      UserDetails userDetails = (UserDetails) auth.getPrincipal();

      String token = tokenService.generateToken(userDetails.getUsername());

      return new TokenDto(token);

    } catch (AuthenticationException e) {
      throw new CustomError(
          "Username ou senha incorretos",
          HttpStatus.FORBIDDEN.value());
    }
  }
}
