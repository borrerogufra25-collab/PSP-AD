package com.ejemplo.ud1rest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * @Entity indica que esta clase es una entidad JPA.
 * JPA utilizará esta clase para representar una tabla de la base de datos.
 */
@Entity

/*
 * @Table permite indicar el nombre de la tabla.
 */
@Table(name = "productos")

/*
 * Lombok genera automáticamente los getters.
 */
@Getter

/*
 * Lombok genera automáticamente los setters.
 */
@Setter

/*
 * Genera un constructor vacío.
 * JPA necesita un constructor sin argumentos.
 */
@NoArgsConstructor

/*
 * Genera un constructor con todos los atributos.
 */
@AllArgsConstructor

public class Producto {

    /*
     * @Id indica que este atributo es la clave primaria.
     */
    @Id

    /*
     * H2/JPA generará automáticamente el valor del ID.
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private Double precio;
}
