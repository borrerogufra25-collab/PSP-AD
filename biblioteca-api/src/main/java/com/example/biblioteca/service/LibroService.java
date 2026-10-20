package com.example.biblioteca.service;

import com.example.biblioteca.dto.LibroRequest;
import com.example.biblioteca.dto.LibroResponse;
import com.example.biblioteca.exception.ConflictoException;
import com.example.biblioteca.exception.RecursoNoEncontradoException;
import com.example.biblioteca.model.Autor;
import com.example.biblioteca.model.Libro;
import com.example.biblioteca.repository.AutorRepository;
import com.example.biblioteca.repository.LibroRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Capa de SERVICIO para Libro (ver los comentarios de AutorService, funciona igual).
 *
 * <p>Reglas de negocio de esta clase:
 * <ul>
 *   <li>Un libro siempre debe pertenecer a un autor que exista (si no, 404).</li>
 *   <li>El ISBN no se puede repetir (si no, 409).</li>
 * </ul>
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class LibroService {

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    /**
     * Lista los libros. Si se recibe un título, filtra por él; si no, devuelve todos.
     *
     * @param titulo texto a buscar dentro del título (puede ser null)
     */
    public List<LibroResponse> listar(String titulo) {
        List<Libro> libros = (titulo == null || titulo.isBlank())
                ? libroRepository.findAll()
                : libroRepository.findByTituloContainingIgnoreCase(titulo);

        return libros.stream().map(LibroResponse::desde).toList();
    }

    public LibroResponse obtenerPorId(Long id) {
        return LibroResponse.desde(buscarLibro(id));
    }

    @Transactional
    public LibroResponse crear(LibroRequest request) {
        // Regla 1: ISBN único
        if (libroRepository.existsByIsbn(request.isbn())) {
            throw new ConflictoException("Ya existe un libro con el ISBN " + request.isbn());
        }
        // Regla 2: el autor debe existir
        Autor autor = buscarAutor(request.autorId());

        Libro libro = Libro.builder()
                .titulo(request.titulo())
                .isbn(request.isbn())
                .anioPublicacion(request.anioPublicacion())
                .precio(request.precio())
                .autor(autor)
                .build();

        Libro guardado = libroRepository.save(libro);
        log.info("Libro creado con id {}", guardado.getId());
        return LibroResponse.desde(guardado);
    }

    @Transactional
    public LibroResponse actualizar(Long id, LibroRequest request) {
        Libro libro = buscarLibro(id);

        // El ISBN puede ser el mismo que ya tenía este libro, pero no el de OTRO libro
        if (libroRepository.existsByIsbnAndIdNot(request.isbn(), id)) {
            throw new ConflictoException("Ya existe otro libro con el ISBN " + request.isbn());
        }
        Autor autor = buscarAutor(request.autorId());

        libro.setTitulo(request.titulo());
        libro.setIsbn(request.isbn());
        libro.setAnioPublicacion(request.anioPublicacion());
        libro.setPrecio(request.precio());
        libro.setAutor(autor);
        // Igual que en AutorService: JPA hace el UPDATE solo al terminar la transacción.
        return LibroResponse.desde(libro);
    }

    @Transactional
    public void eliminar(Long id) {
        // Comprobamos que existe para devolver 404 si no (deleteById no avisaría)
        libroRepository.delete(buscarLibro(id));
        log.info("Libro {} eliminado", id);
    }

    private Libro buscarLibro(Long id) {
        return libroRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el libro con id " + id));
    }

    private Autor buscarAutor(Long id) {
        return autorRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el autor con id " + id));
    }
}
