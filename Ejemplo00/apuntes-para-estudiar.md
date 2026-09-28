# Apuntes rápidos - REST + Lombok + JPA + H2

## 1. @Entity

```java
@Entity
public class Producto {
}
```

Indica que la clase representa una entidad que JPA puede guardar en una base de datos.

## 2. @Id

```java
@Id
private Long id;
```

Indica la clave primaria.

## 3. @GeneratedValue

```java
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```

Hace que el identificador se genere automáticamente.

## 4. Lombok

```java
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
```

Lombok genera código repetitivo automáticamente.

- `@Getter` -> getters
- `@Setter` -> setters
- `@NoArgsConstructor` -> constructor vacío
- `@AllArgsConstructor` -> constructor con todos los atributos

## 5. JpaRepository

```java
public interface ProductoRepository
        extends JpaRepository<Producto, Long> {
}
```

Proporciona operaciones básicas de persistencia sin tener que escribirlas manualmente.

Algunas operaciones:

```java
findAll()
findById(id)
save(producto)
deleteById(id)
existsById(id)
```

## 6. @RestController

```java
@RestController
@RequestMapping("/productos")
public class ProductoController {
}
```

Crea un controlador REST.

## 7. GET

```java
@GetMapping
public ResponseEntity<List<Producto>> obtenerTodos() {
    return ResponseEntity.ok(productos);
}
```

GET se utiliza para obtener recursos.

## 8. POST + @RequestBody

```java
@PostMapping
public ResponseEntity<Producto> crear(
        @RequestBody Producto producto) {
}
```

`@RequestBody` transforma el JSON recibido en un objeto Java.

## 9. @PathVariable

```java
@GetMapping("/{id}")
public ResponseEntity<Producto> obtenerPorId(
        @PathVariable Long id) {
}
```

Para:

```text
GET /productos/5
```

`id` vale `5`.

## 10. @RequestParam

```java
@GetMapping("/buscar")
public ResponseEntity<List<Producto>> buscar(
        @RequestParam String nombre) {
}
```

Para:

```text
GET /productos/buscar?nombre=teclado
```

`nombre` vale `teclado`.

## 11. ResponseEntity

Permite controlar la respuesta HTTP:

```java
ResponseEntity.ok()
ResponseEntity.status(HttpStatus.CREATED)
ResponseEntity.notFound()
ResponseEntity.noContent()
```

Correspondencia:

- 200 -> OK
- 201 -> Created
- 404 -> Not Found
- 204 -> No Content

## 12. Flujo de una petición

Ejemplo:

```text
POST /productos
        |
        v
ProductoController
        |
        v
ProductoService
        |
        v
ProductoRepository
        |
        v
H2
```

El controlador recibe la petición REST, el servicio organiza la lógica, el repositorio utiliza JPA y JPA trabaja con H2.
