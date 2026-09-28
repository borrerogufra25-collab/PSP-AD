package com.ejemplo.ud1rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ejemplo.ud1rest.model.Producto;
import com.ejemplo.ud1rest.service.ProductoService;

/*
 * @RestController indica que esta clase es un controlador REST.
 *
 * Las respuestas de los métodos se convierten automáticamente
 * a JSON cuando devolvemos objetos Java.
 */
@RestController

/*
 * Todas las rutas de este controlador empiezan por /productos.
 */
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;

    /*
     * Inyección de dependencias.
     */
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    /*
     * GET /productos
     *
     * Devuelve todos los productos.
     */
    @GetMapping
    public ResponseEntity<List<Producto>> obtenerTodos() {

        List<Producto> productos = productoService.obtenerTodos();

        return ResponseEntity.ok(productos);
    }

    /*
     * GET /productos/{id}
     *
     * @PathVariable obtiene el valor que aparece en la URL.
     *
     * Ejemplo:
     * /productos/1
     * id = 1
     */
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {

        Optional<Producto> producto = productoService.obtenerPorId(id);

        if (producto.isPresent()) {
            return ResponseEntity.ok(producto.get());
        }

        return ResponseEntity.notFound().build();
    }

    /*
     * POST /productos
     *
     * @RequestBody convierte el JSON recibido en un objeto Producto.
     */
    @PostMapping
    public ResponseEntity<Producto> crear(@RequestBody Producto producto) {

        Producto nuevoProducto = productoService.guardar(producto);

        /*
         * 201 CREATED indica que se ha creado el recurso.
         */
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(nuevoProducto);
    }

    /*
     * PUT /productos/{id}
     *
     * Modifica un producto existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(
            @PathVariable Long id,
            @RequestBody Producto datos) {

        Optional<Producto> producto =
                productoService.actualizar(id, datos);

        if (producto.isPresent()) {
            return ResponseEntity.ok(producto.get());
        }

        return ResponseEntity.notFound().build();
    }

    /*
     * DELETE /productos/{id}
     *
     * Si existe, elimina el producto y devuelve 204 NO CONTENT.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        boolean eliminado = productoService.eliminar(id);

        if (eliminado) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    /*
     * GET /productos/buscar?nombre=teclado
     *
     * @RequestParam obtiene un parámetro de la query.
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> buscar(
            @RequestParam String nombre) {

        return ResponseEntity.ok(
                productoService.buscarPorNombre(nombre)
        );
    }
}
