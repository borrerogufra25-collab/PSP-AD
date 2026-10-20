package com.example.biblioteca.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad JPA: representa una fila de la tabla "libros".
 *
 * <p>Relación con Autor: <b>muchos libros pertenecen a un autor</b> (N:1).
 * En la tabla "libros" se crea una columna "autor_id" que es clave foránea hacia "autores".
 *
 * <p>Para simplificar, la relación es <b>unidireccional</b>: Libro conoce a su Autor,
 * pero Autor no tiene una lista de libros. Si necesitamos los libros de un autor,
 * los consultamos con el repositorio ({@code LibroRepository.findByAutorId}).
 */
@Entity
@Table(name = "libros")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String titulo;

    // unique = true -> no puede haber dos libros con el mismo ISBN (restricción UNIQUE en BD)
    @Column(nullable = false, unique = true, length = 20)
    private String isbn;

    // Nombre de columna explícito para usar snake_case en la base de datos
    @Column(name = "anio_publicacion", nullable = false)
    private Integer anioPublicacion;

    // BigDecimal es el tipo correcto para dinero (double/float pierden precisión).
    // precision = 8 dígitos en total, scale = 2 decimales (ej.: 999999.99)
    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal precio;

    /**
     * Relación muchos-a-uno con Autor.
     *
     * <ul>
     *   <li>{@code fetch = LAZY}: el autor NO se carga de la BD hasta que se usa realmente
     *       (getAutor().getNombre()). Es más eficiente que EAGER (carga siempre).</li>
     *   <li>{@code @JoinColumn}: define la columna de clave foránea "autor_id".</li>
     * </ul>
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;
}
