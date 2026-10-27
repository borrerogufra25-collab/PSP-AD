package com.example.biblioteca.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.biblioteca.dto.AutorRequest;
import com.example.biblioteca.dto.AutorResponse;
import com.example.biblioteca.dto.LibroRequest;
import com.example.biblioteca.dto.LibroResponse;
import com.example.biblioteca.exception.ConflictoException;
import com.example.biblioteca.exception.RecursoNoEncontradoException;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Tests de integración de la capa de servicio.
 *
 * <ul>
 *   <li>{@code @SpringBootTest}: arranca el contexto completo de Spring (con H2 incluida),
 *       así que probamos servicios y repositorios reales.</li>
 *   <li>{@code @Transactional} en la clase de test: cada test se ejecuta en una transacción
 *       que se DESHACE al terminar, para que los tests no se afecten entre sí.</li>
 *   <li>{@code @Autowired}: pide a Spring que inyecte los servicios en el test.</li>
 * </ul>
 *
 * <p>Estructura de cada test (patrón AAA): Arrange (preparar), Act (ejecutar), Assert (comprobar).
 */
@SpringBootTest
@Transactional
class LibroServiceTest {

    @Autowired
    private AutorService autorService;

    @Autowired
    private LibroService libroService;

    /** Método auxiliar para crear un autor de prueba. */
    private AutorResponse crearAutor() {
        return autorService.crear(new AutorRequest("Autor de prueba", "Española"));
    }

    private LibroRequest libroRequest(String isbn, Long autorId) {
        return new LibroRequest("Libro de prueba", isbn, 2020, new BigDecimal("12.50"), autorId);
    }

    @Test
    void crearLibro_conDatosValidos_devuelveLibroConIdYAutor() {
        // Arrange
        AutorResponse autor = crearAutor();

        // Act
        LibroResponse libro = libroService.crear(libroRequest("111-1111111111", autor.id()));

        // Assert
        assertThat(libro.id()).isNotNull();
        assertThat(libro.autorNombre()).isEqualTo("Autor de prueba");
    }

    @Test
    void crearLibro_conAutorInexistente_lanzaRecursoNoEncontrado() {
        assertThrows(RecursoNoEncontradoException.class,
                () -> libroService.crear(libroRequest("222-2222222222", 9999L)));
    }

    @Test
    void crearLibro_conIsbnRepetido_lanzaConflicto() {
        AutorResponse autor = crearAutor();
        libroService.crear(libroRequest("333-3333333333", autor.id()));

        assertThrows(ConflictoException.class,
                () -> libroService.crear(libroRequest("333-3333333333", autor.id())));
    }

    @Test
    void eliminarAutor_conLibros_lanzaConflicto() {
        AutorResponse autor = crearAutor();
        libroService.crear(libroRequest("444-4444444444", autor.id()));

        assertThrows(ConflictoException.class, () -> autorService.eliminar(autor.id()));
    }

    @Test
    void listarLibros_conFiltroDeTitulo_devuelveSoloLosQueCoinciden() {
        // Los datos de ejemplo los carga DataLoader al arrancar la aplicación
        var resultado = libroService.listar("quijote");

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).titulo()).containsIgnoringCase("quijote");
    }
}
