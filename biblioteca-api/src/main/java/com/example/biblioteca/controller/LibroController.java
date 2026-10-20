package com.example.biblioteca.controller;

import com.example.biblioteca.dto.LibroRequest;
import com.example.biblioteca.dto.LibroResponse;
import com.example.biblioteca.service.LibroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST de Libros.
 *
 * <pre>
 *   GET    /api/libros              -> listar todos (o filtrar con ?titulo=texto)
 *   GET    /api/libros/{id}         -> obtener uno
 *   POST   /api/libros              -> crear
 *   PUT    /api/libros/{id}         -> modificar
 *   DELETE /api/libros/{id}         -> eliminar
 * </pre>
 */
@RestController
@RequestMapping("/api/libros")
@RequiredArgsConstructor
@Tag(name = "Libros", description = "Operaciones sobre libros")
public class LibroController {

    private final LibroService libroService;

    /**
     * {@code @RequestParam}: lee un parámetro de la query string: GET /api/libros?titulo=quijote
     * Con required = false, si no se envía el parámetro llega null y se listan todos.
     */
    @GetMapping
    @Operation(summary = "Listar libros", description = "Si se indica 'titulo', filtra los libros cuyo título lo contenga")
    public List<LibroResponse> listar(@RequestParam(required = false) String titulo) {
        return libroService.listar(titulo);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un libro por su id")
    @ApiResponse(responseCode = "404", description = "El libro no existe")
    public LibroResponse obtenerPorId(@PathVariable Long id) {
        return libroService.obtenerPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear un libro")
    @ApiResponse(responseCode = "400", description = "Datos no válidos")
    @ApiResponse(responseCode = "404", description = "El autor indicado no existe")
    @ApiResponse(responseCode = "409", description = "Ya existe un libro con ese ISBN")
    public LibroResponse crear(@Valid @RequestBody LibroRequest request) {
        return libroService.crear(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modificar un libro")
    @ApiResponse(responseCode = "400", description = "Datos no válidos")
    @ApiResponse(responseCode = "404", description = "El libro o el autor no existen")
    @ApiResponse(responseCode = "409", description = "Ya existe otro libro con ese ISBN")
    public LibroResponse actualizar(@PathVariable Long id, @Valid @RequestBody LibroRequest request) {
        return libroService.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar un libro")
    @ApiResponse(responseCode = "404", description = "El libro no existe")
    public void eliminar(@PathVariable Long id) {
        libroService.eliminar(id);
    }
}
