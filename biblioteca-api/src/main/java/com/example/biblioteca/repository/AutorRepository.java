package com.example.biblioteca.repository;

import com.example.biblioteca.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de Autor.
 *
 * <p>Es una INTERFAZ: no escribimos ninguna implementación. Spring Data JPA genera
 * la clase automáticamente al arrancar.
 *
 * <p>Al extender {@code JpaRepository<Autor, Long>} obtenemos gratis los métodos CRUD:
 * save, findById, findAll, deleteById, existsById, count...
 * <ul>
 *   <li>1er parámetro genérico: la entidad (Autor).</li>
 *   <li>2º parámetro genérico: el tipo de su clave primaria (Long).</li>
 * </ul>
 *
 * <p>(@Repository es opcional en interfaces de Spring Data, pero deja clara la intención.)
 */
@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    // De momento no necesitamos métodos propios.
}
