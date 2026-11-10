package com.ejemplo.cartelera.repositorio;

import com.ejemplo.cartelera.modelo.Pelicula;
import com.fasterxml.jackson.annotation.OptBoolean;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio de partida: ¡todavía no contiene métodos de consultas derivadas!
 * <p>
 * En la actividad guiada se añadirán los tres primeros métodos de búsqueda. Después, cada pareja
 * implementará otros cuatro sin escribir SQL ni @Query.
 */
public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {

  List<Pelicula> findByDuracionMinutos(int duracionMinutos);

  List<Pelicula> findByDuracionMinutosGreaterThan(int duracionMinutos);

  List<Pelicula> findByDuracionMinutosGreaterThanAndGenero(int duracionMinutos, String genero);

  List<Pelicula> findByDuracionMinutosGreaterThanOrGenero(int duracionMinutos, String genero);

  List<Pelicula> findByGeneroAndDisponibleGreaterThan(String genero,
      boolean disponibleIsGreaterThan, int duracionMinutos);

  List<Pelicula> findByTituloContainingIgnoreCaseOrderByDuracionMinutosAsc(String titulo);

  List<Pelicula> findByTituloAndDuracionMinutos(String titulo, int duracionMinutos);

  //Este al menos te devuelve una sóla película
  Optional<Pelicula> findFirstByTituloOrderByTitulo(String titulo);

  // ACTIVIDAD 3 (guiada): añade aquí los métodos del tutorial.

  // ACTIVIDAD 4 (parejas): añade aquí los métodos de los cuatro retos.
}
