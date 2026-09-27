package com.ejemplo.ud1rest.controller;

import com.ejemplo.ud1rest.model.Producto;
import com.ejemplo.ud1rest.service.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/*
 * CONTROLADOR REST
 *
 * La presentación indica que:
 *
 * - @RestController identifica un controlador REST.
 * - @RequestMapping puede definir un prefijo común.
 * - Cada método puede representar un endpoint.
 *
 * Hemos usado /productos como recurso.
 *
 * Observa que usamos un SUSTANTIVO ("productos") y no:
 *
 *     /obtenerProductos
 *     /crearProducto
 *     /borrarProducto
 *
 * La acción ya la indica el verbo HTTP.
 */
@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;

    /*
     * INYECCIÓN DE DEPENDENCIAS
     *
     * Spring proporciona ProductoService.
     *
     * El controlador no crea la dependencia con:
     *
     *     new ProductoService(...)
     *
     * La recibe desde fuera.
     */
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    /*
     * =========================================================
     * GET - OBTENER TODOS
     * =========================================================
     *
     * GET /productos
     *
     * Devuelve 200 OK con la lista de productos.
     */
    @GetMapping
    public ResponseEntity<List<Producto>> obtenerTodos() {

        List<Producto> productos = productoService.obtenerTodos();

        return ResponseEntity.ok(productos);
    }

    /*
     * =========================================================
     * GET - OBTENER UNO
     * =========================================================
     *
     * GET /productos/1
     *
     * El {id} es una variable de la ruta.
     *
     * @PathVariable sirve para recibir ese valor como argumento
     * del método.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {

        Producto producto = productoService.obtenerPorId(id);

        if (producto == null) {
            // El recurso no existe -> 404 Not Found
            return ResponseEntity.notFound().build();
        }

        // Recurso encontrado -> 200 OK
        return ResponseEntity.ok(producto);
    }

    /*
     * =========================================================
     * POST - CREAR
     * =========================================================
     *
     * POST /productos
     *
     * El cliente manda un JSON en el cuerpo:
     *
     * {
     *     "id": 3,
     *     "nombre": "Monitor"
     * }
     *
     * @RequestBody hace que Spring convierta ese JSON
     * en un objeto Producto.
     */
    @PostMapping
    public ResponseEntity<Producto> crear(@RequestBody Producto producto) {

        Producto productoCreado = productoService.guardar(producto);

        // 201 Created -> recurso creado correctamente
        return ResponseEntity.status(201).body(productoCreado);
    }

    /*
     * =========================================================
     * PUT - ACTUALIZAR
     * =========================================================
     *
     * PUT /productos/1
     *
     * El ID viene en la URL.
     * El producto nuevo viene en el cuerpo como JSON.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(
            @PathVariable Long id,
            @RequestBody Producto producto) {

        Producto productoActualizado =
                productoService.actualizar(id, producto);

        if (productoActualizado == null) {
            // El recurso que queremos modificar no existe.
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(productoActualizado);
    }

    /*
     * =========================================================
     * DELETE - BORRAR
     * =========================================================
     *
     * DELETE /productos/1
     *
     * No necesitamos @RequestBody.
     * El recurso se identifica mediante el {id}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {

        boolean borrado = productoService.borrar(id);

        if (!borrado) {
            return ResponseEntity.notFound().build();
        }

        // 204 No Content -> borrado correctamente
        return ResponseEntity.noContent().build();
    }

    /*
     * =========================================================
     * REQUEST PARAM
     * =========================================================
     *
     * Ejemplo:
     *
     * GET /productos/buscar?nombre=teclado
     *
     * La parte después de ? es la QUERY.
     *
     * @RequestParam recupera el parámetro de la query.
     *
     * required=false -> no es obligatorio.
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> buscar(
            @RequestParam(required = false) String nombre) {

        if (nombre == null) {
            return ResponseEntity.ok(
                    productoService.obtenerTodos()
            );
        }

        List<Producto> resultado = productoService.obtenerTodos()
                .stream()
                .filter(producto ->
                        producto.getNombre()
                                .toLowerCase()
                                .contains(nombre.toLowerCase()))
                .toList();

        return ResponseEntity.ok(resultado);
    }

    /*
     * =========================================================
     * REQUEST PARAM CON VALOR POR DEFECTO
     * =========================================================
     *
     * Ejemplo:
     *
     * GET /productos/orden
     *
     * Si no se manda "orden", usamos "asc".
     *
     * También podríamos mandar:
     *
     * GET /productos/orden?orden=desc
     */
    @GetMapping("/orden")
    public ResponseEntity<List<Producto>> ordenar(
            @RequestParam(defaultValue = "asc") String orden) {

        List<Producto> productos = productoService.obtenerTodos();

        if (orden.equalsIgnoreCase("desc")) {
            productos = productos.reversed();
        }

        return ResponseEntity.ok(productos);
    }

    /*
     * =========================================================
     * TODOS LOS PARAMETROS DE LA QUERY
     * =========================================================
     *
     * Ejemplo:
     *
     * GET /productos/filtro?nombre=teclado&orden=desc
     *
     * Map<String, String> recoge los parámetros:
     *
     * nombre -> teclado
     * orden  -> desc
     *
     * La presentación muestra esta posibilidad mediante Map.
     */
    @GetMapping("/filtro")
    public ResponseEntity<Map<String, String>> filtro(
            @RequestParam Map<String, String> parametros) {

        return ResponseEntity.ok(parametros);
    }
}
