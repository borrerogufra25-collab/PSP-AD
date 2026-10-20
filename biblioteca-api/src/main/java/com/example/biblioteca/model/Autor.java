package com.example.biblioteca.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidad JPA: representa una fila de la tabla "autores".
 *
 * <p>Una entidad es una clase Java que se "mapea" a una tabla. Cada objeto Autor
 * equivale a una fila; cada atributo, a una columna.
 *
 * <h3>Anotaciones de Lombok usadas</h3>
 * <ul>
 *   <li>{@code @Getter / @Setter}: generan los getters y setters de todos los atributos.</li>
 *   <li>{@code @NoArgsConstructor}: constructor vacío. JPA lo EXIGE para poder crear objetos.</li>
 *   <li>{@code @AllArgsConstructor}: constructor con todos los atributos (lo necesita @Builder).</li>
 *   <li>{@code @Builder}: permite crear objetos de forma legible:
 *       {@code Autor.builder().nombre("X").nacionalidad("Y").build()}.</li>
 * </ul>
 *
 * <p>Nota: en entidades JPA NO se recomienda usar {@code @Data} de Lombok, porque genera
 * equals/hashCode/toString con todos los campos, lo que puede dar problemas con las relaciones.
 */
@Entity                      // Indica que esta clase es una entidad JPA
@Table(name = "autores")     // Nombre de la tabla en la base de datos
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Autor {

  /**
   * Clave primaria. IDENTITY = la base de datos genera el valor automáticamente (autoincremental:
   * 1, 2, 3...).
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // nullable = false -> la columna es NOT NULL; length = tamaño máximo (VARCHAR(100))
  @Column(nullable = false, length = 100)
  private String nombre;

  @Column(length = 50)
  private String nacionalidad;
}
