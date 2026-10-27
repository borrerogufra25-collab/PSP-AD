package com.example.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO (Data Transfer Object) de ENTRADA: los datos que el cliente envía para crear o modificar un
 * autor.
 *
 * <p>¿Por qué un DTO y no usar directamente la entidad Autor?
 * <ul>
 *   <li>El cliente no debería poder enviar el "id" ni campos internos.</li>
 *   <li>Separamos lo que expone la API de cómo se guarda en la base de datos.</li>
 *   <li>Podemos poner aquí las validaciones sin "ensuciar" la entidad.</li>
 * </ul>
 *
 * <p>Es un <b>record</b> de Java: una clase inmutable que genera automáticamente
 * constructor, getters (nombre(), nacionalidad()), equals, hashCode y toString.
 *
 * <p>Las anotaciones de validación solo se comprueban si en el controlador el parámetro
 * lleva {@code @Valid}. Si algún dato es incorrecto, Spring lanza
 * MethodArgumentNotValidException (que capturamos en GlobalExceptionHandler).
 */
public record AutorRequest(

    @NotBlank(message = "El nombre es obligatorio")   // no null, no vacío, no solo espacios
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    String nombre,

    @Size(max = 50, message = "La nacionalidad no puede superar los 50 caracteres")
    String nacionalidad   // opcional: sin @NotBlank puede venir null
) {

}
