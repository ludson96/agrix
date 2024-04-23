package com.betrybe.agrix.error;

/**
 * Exceção usada como erro de customização onde passo a mensagem e o status desejado como parâmetro.
 */
public class CustomError extends Exception {

  int status;

  public CustomError(String message, int status) {
    super(message);
    this.status = status;
  }

  public int getStatus() {
    return status;
  }
}
