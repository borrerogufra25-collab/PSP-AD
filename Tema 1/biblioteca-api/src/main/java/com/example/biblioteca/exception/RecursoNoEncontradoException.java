package com.example.biblioteca.exception;

/**
 * Excepción propia: se lanza cuando se pide un recurso (autor, libro...) que no existe.
 * GlobalExceptionHandler la convierte en una respuesta HTTP 404 (Not Found).
 *
 * <p>Extiende RuntimeException (excepción "no comprobada"): no obliga a declarar
 * "throws" en cada método ni a rodearla con try/catch.
 */
public class RecursoNoEncontradoException extends RuntimeException {

  public RecursoNoEncontradoException(String mensaje) {
    super(mensaje);
  }
}
