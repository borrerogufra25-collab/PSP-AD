package com.example.biblioteca.dto;

import com.example.biblioteca.model.Libro;
import java.math.BigDecimal;

/**
 * DTO de SALIDA para un libro. Incluye el id y el nombre del autor para que el cliente no tenga que
 * hacer otra petición solo para saber quién lo escribió.
 */
public record LibroResponse(
    Long id,
    String titulo,
    String isbn,
    Integer anioPublicacion,
    BigDecimal precio,
    Long autorId,
    String autorNombre
) {

  /**
   * Convierte la entidad en DTO. OJO: {@code libro.getAutor()} es LAZY, así que este método debe
   * llamarse dentro de un método @Transactional (los de LibroService lo son); si no, daría
   * LazyInitializationException.
   */
  public static LibroResponse desde(Libro libro) {
    return new LibroResponse(
        libro.getId(),
        libro.getTitulo(),
        libro.getIsbn(),
        libro.getAnioPublicacion(),
        libro.getPrecio(),
        libro.getAutor().getId(),
        libro.getAutor().getNombre());
  }
}
