package com.example.biblioteca.service;

import com.example.biblioteca.dto.AutorRequest;
import com.example.biblioteca.dto.AutorResponse;
import com.example.biblioteca.dto.LibroResponse;
import com.example.biblioteca.exception.ConflictoException;
import com.example.biblioteca.exception.RecursoNoEncontradoException;
import com.example.biblioteca.model.Autor;
import com.example.biblioteca.repository.AutorRepository;
import com.example.biblioteca.repository.LibroRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Capa de SERVICIO para Autor: aquí vive la lógica de negocio.
 *
 * <p>Arquitectura en capas de este proyecto:
 * <pre>
 *   Controller  ->  Service  ->  Repository  ->  Base de datos
 *   (HTTP/JSON)     (reglas)     (acceso a datos)
 * </pre>
 *
 * <p>{@code @Service}: Spring crea UNA instancia de esta clase y la guarda en su contenedor.
 * <p>{@code @RequiredArgsConstructor} (Lombok): genera un constructor con los atributos final;
 * Spring lo usa para inyectar los repositorios (inyección de dependencias por constructor).
 *
 * <p>{@code @Transactional(readOnly = true)} a nivel de clase: todos los métodos se ejecutan
 * dentro de una transacción de solo lectura (más eficiente). Los métodos que modifican datos la
 * sobrescriben con {@code @Transactional} normal. Si un método lanza una excepción no comprobada,
 * la transacción se deshace (rollback) y no se guarda nada a medias.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AutorService {

  private final AutorRepository autorRepository;
  private final LibroRepository libroRepository;

  /**
   * Devuelve todos los autores.
   */
  public List<AutorResponse> listar() {
    return autorRepository.findAll().stream()
        .map(AutorResponse::desde)   // convertimos cada entidad en DTO
        .toList();
  }

  /**
   * Devuelve un autor por id o lanza 404 si no existe.
   */
  public AutorResponse obtenerPorId(Long id) {
    return AutorResponse.desde(buscarAutor(id));
  }

  /**
   * Devuelve los libros de un autor (comprobando antes que el autor existe).
   */
  public List<LibroResponse> listarLibros(Long autorId) {
    buscarAutor(autorId);   // si no existe, lanza 404
    return libroRepository.findByAutorId(autorId).stream()
        .map(LibroResponse::desde)
        .toList();
  }

  /**
   * Crea un autor nuevo.
   */
  @Transactional
  public AutorResponse crear(AutorRequest request) {
    Autor autor = Autor.builder()
        .nombre(request.nombre())
        .nacionalidad(request.nacionalidad())
        .build();
    // save() hace un INSERT y devuelve el objeto con el id ya generado
    Autor guardado = autorRepository.save(autor);
    log.info("Autor creado con id {}", guardado.getId());
    return AutorResponse.desde(guardado);
  }

  /**
   * Modifica un autor existente.
   */
  @Transactional
  public AutorResponse actualizar(Long id, AutorRequest request) {
    Autor autor = buscarAutor(id);
    autor.setNombre(request.nombre());
    autor.setNacionalidad(request.nacionalidad());
    // No hace falta llamar a save(): la entidad está "gestionada" por JPA dentro de la
    // transacción y, al terminar el método, Hibernate detecta los cambios y hace el UPDATE
    // (esto se llama "dirty checking").
    return AutorResponse.desde(autor);
  }

  /**
   * Elimina un autor. Regla de negocio: no se puede borrar si tiene libros (si no, la base de datos
   * rechazaría el borrado por la clave foránea).
   */
  @Transactional
  public void eliminar(Long id) {
    Autor autor = buscarAutor(id);
    if (libroRepository.existsByAutorId(id)) {
      throw new ConflictoException(
          "No se puede eliminar el autor " + id + " porque todavía tiene libros asociados");
    }
    autorRepository.delete(autor);
    log.info("Autor {} eliminado", id);
  }

  /**
   * Método auxiliar privado: busca el autor o lanza la excepción 404.
   */
  private Autor buscarAutor(Long id) {
    // findById devuelve un Optional<Autor>: puede contener el autor o estar vacío.
    return autorRepository.findById(id)
        .orElseThrow(() -> new RecursoNoEncontradoException("No existe el autor con id " + id));
  }
}
