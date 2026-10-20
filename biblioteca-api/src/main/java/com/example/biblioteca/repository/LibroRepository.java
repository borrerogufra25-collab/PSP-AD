package com.example.biblioteca.repository;

import com.example.biblioteca.model.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de Libro.
 *
 * <p>Aquí añadimos <b>métodos derivados del nombre</b> ("query methods"): Spring Data
 * lee el nombre del método y construye la consulta SQL por nosotros. No hay que escribir SQL ni
 * JPQL.
 *
 * <p>Regla general: {@code findBy} / {@code existsBy} + nombre del atributo + condición.
 */
@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

  /**
   * Busca libros cuyo título CONTENGA el texto, sin distinguir mayúsculas/minúsculas. Equivale a:
   * SELECT * FROM libros WHERE LOWER(titulo) LIKE LOWER('%texto%')
   */
  List<Libro> findByTituloContainingIgnoreCase(String titulo);

  /**
   * Libros de un autor. "AutorId" significa: atributo "autor" (de Libro) y, dentro de él, "id".
   * Equivale a: SELECT * FROM libros WHERE autor_id = ?
   */
  List<Libro> findByAutorId(Long autorId);

  /**
   * ¿Existe algún libro con este ISBN? (para evitar duplicados al crear)
   */
  boolean existsByIsbn(String isbn);

  /**
   * ¿Existe OTRO libro (con id distinto) con este ISBN? (para evitar duplicados al actualizar)
   */
  boolean existsByIsbnAndIdNot(String isbn, Long id);

  /**
   * ¿Tiene este autor algún libro? (para impedir borrar autores con libros)
   */
  boolean existsByAutorId(Long autorId);
}
