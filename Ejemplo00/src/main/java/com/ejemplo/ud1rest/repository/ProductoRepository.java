package com.ejemplo.ud1rest.repository;

import com.ejemplo.ud1rest.model.Producto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/*
 * @Repository es uno de los estereotipos que aparecen en la presentación.
 *
 * Aquí simulamos el acceso a datos con una lista en memoria.
 *
 * NO usamos una base de datos porque la presentación no entra todavía
 * en JPA ni en bases de datos.
 */
@Repository
public class ProductoRepository {

    private final List<Producto> productos = new ArrayList<>();

    public ProductoRepository() {
        productos.add(new Producto(1L, "Teclado"));
        productos.add(new Producto(2L, "Ratón"));
    }

    public List<Producto> obtenerTodos() {
        return productos;
    }

    public Producto obtenerPorId(Long id) {
        for (Producto producto : productos) {
            if (producto.getId().equals(id)) {
                return producto;
            }
        }

        return null;
    }

    public Producto guardar(Producto producto) {
        productos.add(producto);
        return producto;
    }

    public Producto actualizar(Long id, Producto productoNuevo) {
        Producto producto = obtenerPorId(id);

        if (producto == null) {
            return null;
        }

        producto.setNombre(productoNuevo.getNombre());

        return producto;
    }

    public boolean borrar(Long id) {
        Producto producto = obtenerPorId(id);

        if (producto == null) {
            return false;
        }

        productos.remove(producto);
        return true;
    }
}
