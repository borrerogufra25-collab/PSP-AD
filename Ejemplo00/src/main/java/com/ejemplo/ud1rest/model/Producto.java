package com.ejemplo.ud1rest.model;

/*
 * Esta clase representa el recurso Producto.
 *
 * La presentación muestra que un recurso REST puede tener
 * una representación en JSON.
 *
 * Ejemplo de JSON:
 *
 * {
 *   "id": 1,
 *   "nombre": "Teclado"
 * }
 */
public class Producto {

    private Long id;
    private String nombre;

    // Constructor vacío.
    // Spring/Jackson puede necesitarlo para convertir JSON a Java.
    public Producto() {
    }

    public Producto(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
