package com.betrybe.agrix.controllers;

import com.betrybe.agrix.controllers.dto.AuthDto;
import com.betrybe.agrix.controllers.dto.TokenDto;
import com.betrybe.agrix.error.CustomError;
import com.betrybe.agrix.services.TokenService;
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

  @PostMapping("/login")
  public TokenDto login(@RequestBody AuthDto authDto) throws CustomError {
    try {

      UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(
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
