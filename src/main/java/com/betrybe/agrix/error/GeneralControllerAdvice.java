package com.betrybe.agrix.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Controller usado como Advice, reunindo todos as exceções lançadas.
 */
@ControllerAdvice
public class GeneralControllerAdvice {

  /**
   * Se CustomError for chamada irá retornar um ResponseEntity contendo status e message.
   *
   * @param error Mensagem de erro personalizada.
   * @return retornar um ResponseEntity contendo status e message.
   */
  @ExceptionHandler(CustomError.class)
  public ResponseEntity<String> handleException(CustomError error) {
    return ResponseEntity
        .status(error.getStatus())
        .body(error.getMessage());
  }

  /**
   * Se receber um erro diferente do lançado será um erro interno.
   *
   * @param error Uma Exception como mensagem de erro.
   * @return retornar um ResponseEntity contendo status e message.
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleException(Exception error) {
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(error.getMessage());
  }

}
