package com.example.biblioteca.dto;

import com.example.biblioteca.model.Autor;

/**
 * DTO de SALIDA: lo que la API devuelve al cliente sobre un autor (se convierte a JSON).
 */
public record AutorResponse(
    Long id,
    String nombre,
    String nacionalidad
) {

  /**
   * Método estático de conveniencia para convertir una entidad en su DTO de respuesta. Lo usamos
   * así: {@code AutorResponse.desde(autor)}.
   */
  public static AutorResponse desde(Autor autor) {
    return new AutorResponse(
        autor.getId(),
        autor.getNombre(),
        autor.getNacionalidad()
    );
  }
}
