package com.example.biblioteca.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Gestor global de excepciones.
 *
 * <p>{@code @RestControllerAdvice} hace que esta clase "vigile" TODOS los controladores.
 * Cuando uno lanza una excepción, Spring busca aquí un método {@code @ExceptionHandler} para ese
 * tipo de excepción y usa su resultado como respuesta HTTP.
 *
 * <p>Ventaja: los controladores y servicios solo lanzan excepciones y no se preocupan
 * de cómo se convierten en respuestas. Todo el tratamiento de errores está en un único sitio.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  /**
   * 404: el recurso solicitado no existe.
   */
  @ExceptionHandler(RecursoNoEncontradoException.class)
  public ResponseEntity<ErrorResponse> manejarNoEncontrado(RecursoNoEncontradoException ex) {
    return construirRespuesta(HttpStatus.NOT_FOUND, ex.getMessage(), Map.of());
  }

  /**
   * 409: conflicto con una regla de negocio (ISBN repetido, autor con libros...).
   */
  @ExceptionHandler(ConflictoException.class)
  public ResponseEntity<ErrorResponse> manejarConflicto(ConflictoException ex) {
    return construirRespuesta(HttpStatus.CONFLICT, ex.getMessage(), Map.of());
  }

  /**
   * 400: fallaron las validaciones (@Valid) del cuerpo de la petición. Recorremos los errores y los
   * devolvemos como un mapa "campo -> mensaje".
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> manejarValidacion(MethodArgumentNotValidException ex) {
    Map<String, String> detalles = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(error ->
        // putIfAbsent: si un campo tiene varios errores, nos quedamos con el primero
        detalles.putIfAbsent(error.getField(), error.getDefaultMessage()));
    return construirRespuesta(HttpStatus.BAD_REQUEST, "Datos de entrada no válidos", detalles);
  }

  /**
   * 400: el JSON enviado está mal formado o un tipo no coincide (ej.: texto donde va un número).
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorResponse> manejarJsonIncorrecto(HttpMessageNotReadableException ex) {
    return construirRespuesta(HttpStatus.BAD_REQUEST,
        "El cuerpo de la petición no es un JSON válido", Map.of());
  }

  /**
   * 500: cualquier otro error inesperado. Se registra en el log y no se filtran detalles internos.
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> manejarErrorGeneral(Exception ex) {
    log.error("Error inesperado", ex);
    return construirRespuesta(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor",
        Map.of());
  }

  /**
   * Método auxiliar para no repetir código al construir la respuesta.
   */
  private ResponseEntity<ErrorResponse> construirRespuesta(
      HttpStatus status, String mensaje, Map<String, String> detalles) {
    ErrorResponse cuerpo = new ErrorResponse(
        LocalDateTime.now(), status.value(), status.getReasonPhrase(), mensaje, detalles);
    return ResponseEntity.status(status).body(cuerpo);
  }
}
