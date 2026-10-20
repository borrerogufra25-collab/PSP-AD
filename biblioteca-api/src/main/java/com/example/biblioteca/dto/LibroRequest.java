package com.example.biblioteca.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * DTO de ENTRADA para crear o modificar un libro.
 *
 * <p>Fíjate en que NO recibimos un objeto Autor completo, solo su identificador
 * ({@code autorId}). El servicio se encarga de buscar el autor en la base de datos.
 *
 * <p>Diferencia importante entre las anotaciones de "obligatorio":
 * <ul>
 *   <li>{@code @NotNull}: el valor no puede ser null (para números, objetos...).</li>
 *   <li>{@code @NotBlank}: solo para String; no puede ser null, vacío ni solo espacios.</li>
 * </ul>
 */
public record LibroRequest(

        @NotBlank(message = "El título es obligatorio")
        @Size(max = 200, message = "El título no puede superar los 200 caracteres")
        String titulo,

        @NotBlank(message = "El ISBN es obligatorio")
        // Expresión regular: entre 10 y 17 caracteres, solo dígitos y guiones
        @Pattern(regexp = "^[0-9-]{10,17}$",
                 message = "El ISBN debe tener entre 10 y 17 caracteres (solo dígitos y guiones)")
        String isbn,

        @NotNull(message = "El año de publicación es obligatorio")
        @Min(value = 1000, message = "El año debe ser como mínimo 1000")
        @Max(value = 2100, message = "El año debe ser como máximo 2100")
        Integer anioPublicacion,

        @NotNull(message = "El precio es obligatorio")
        @DecimalMin(value = "0.0", message = "El precio no puede ser negativo")
        BigDecimal precio,

        @NotNull(message = "El id del autor es obligatorio")
        Long autorId
) {
}
