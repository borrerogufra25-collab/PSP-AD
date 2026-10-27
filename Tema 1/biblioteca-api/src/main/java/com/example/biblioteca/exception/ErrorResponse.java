package com.example.biblioteca.exception;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Formato único para TODOS los errores de la API. Así el cliente siempre recibe la misma estructura
 * JSON, por ejemplo:
 *
 * <pre>
 * {
 *   "timestamp": "2026-10-20T10:15:30",
 *   "status": 400,
 *   "error": "Bad Request",
 *   "mensaje": "Datos de entrada no válidos",
 *   "detalles": { "titulo": "El título es obligatorio" }
 * }
 * </pre>
 *
 * @param timestamp momento en que se produjo el error
 * @param status    código HTTP numérico (400, 404, 409, 500...)
 * @param error     descripción estándar del código HTTP
 * @param mensaje   explicación legible del problema
 * @param detalles  errores por campo (solo en validaciones; si no hay, será un mapa vacío)
 */
public record ErrorResponse(
    LocalDateTime timestamp,
    int status,
    String error,
    String mensaje,
    Map<String, String> detalles
) {

}
