package com.example.biblioteca.controller;

import com.example.biblioteca.dto.AutorRequest;
import com.example.biblioteca.dto.AutorResponse;
import com.example.biblioteca.dto.LibroResponse;
import com.example.biblioteca.service.AutorService;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST de Autores: recibe las peticiones HTTP, llama al servicio y
 * devuelve la respuesta en JSON. No contiene lógica de negocio.
 *
 * <ul>
 *   <li>{@code @RestController}: es un controlador cuyas respuestas se escriben directamente
 *       como JSON (= @Controller + @ResponseBody).</li>
 *   <li>{@code @RequestMapping("/api/autores")}: prefijo común de todas las URLs de esta clase.</li>
 *   <li>{@code @Tag}, {@code @Operation}, {@code @ApiResponse}: anotaciones de springdoc que
 *       enriquecen la documentación de Swagger UI. No afectan al funcionamiento.</li>
 * </ul>
 *
 * <p>Tabla de endpoints (convención REST: el verbo HTTP indica la acción):
 * <pre>
 *   GET    /api/autores              -> listar todos
 *   GET    /api/autores/{id}         -> obtener uno
 *   GET    /api/autores/{id}/libros  -> libros de un autor
 *   POST   /api/autores              -> crear
 *   PUT    /api/autores/{id}         -> modificar
 *   DELETE /api/autores/{id}         -> eliminar
 * </pre>
 */
@RestController
@RequestMapping("/api/autores")
@RequiredArgsConstructor
@Tag(name = "Autores", description = "Operaciones sobre autores")
public class AutorController {

    private final AutorService autorService;

    @GetMapping
    @Operation(summary = "Listar todos los autores")
    public List<AutorResponse> listar() {
        return autorService.listar();
    }

    /**
     * {@code @PathVariable} toma el valor de {id} de la URL: GET /api/autores/3 -> id = 3.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener un autor por su id")
    @ApiResponse(responseCode = "404", description = "El autor no existe")
    public AutorResponse obtenerPorId(@PathVariable Long id) {
        return autorService.obtenerPorId(id);
    }

    @GetMapping("/{id}/libros")
    @Operation(summary = "Listar los libros de un autor")
    @ApiResponse(responseCode = "404", description = "El autor no existe")
    public List<LibroResponse> listarLibros(@PathVariable Long id) {
        return autorService.listarLibros(id);
    }

    /**
     * {@code @RequestBody}: convierte el JSON del cuerpo de la petición en un AutorRequest.
     * {@code @Valid}: ejecuta las validaciones (@NotBlank, @Size...) del DTO antes de entrar
     * al método. Si fallan, el método NO se ejecuta y se devuelve un 400.
     * {@code @ResponseStatus(CREATED)}: responde 201 en lugar del 200 por defecto.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear un autor")
    @ApiResponse(responseCode = "400", description = "Datos no válidos")
    public AutorResponse crear(@Valid @RequestBody AutorRequest request) {
        return autorService.crear(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modificar un autor")
    @ApiResponse(responseCode = "400", description = "Datos no válidos")
    @ApiResponse(responseCode = "404", description = "El autor no existe")
    public AutorResponse actualizar(@PathVariable Long id, @Valid @RequestBody AutorRequest request) {
        return autorService.actualizar(id, request);
    }

    /** 204 No Content: la operación fue bien y no hay nada que devolver en el cuerpo. */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar un autor")
    @ApiResponse(responseCode = "404", description = "El autor no existe")
    @ApiResponse(responseCode = "409", description = "El autor tiene libros asociados")
    public void eliminar(@PathVariable Long id) {
        autorService.eliminar(id);
    }
}
