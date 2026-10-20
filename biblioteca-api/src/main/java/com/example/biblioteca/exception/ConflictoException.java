package com.example.biblioteca.exception;

/**
 * Excepción propia: se lanza cuando la operación choca con una regla de negocio (por ejemplo, ISBN
 * duplicado o borrar un autor que aún tiene libros). GlobalExceptionHandler la convierte en una
 * respuesta HTTP 409 (Conflict).
 */
public class ConflictoException extends RuntimeException {

  public ConflictoException(String mensaje) {
    super(mensaje);
  }
}
