# Guía de estudio de la UD1

## 1. Endpoint

Un endpoint combina principalmente:

- una URL/recurso
- un verbo HTTP
- los parámetros que recibe

Ejemplo:

```text
GET /productos/1
```

`productos` identifica el recurso y `1` identifica un producto concreto.

## 2. Verbos HTTP de la presentación

| Acción | HTTP |
|---|---|
| Obtener | GET |
| Crear | POST |
| Actualizar | PUT |
| Borrar | DELETE |

## 3. Anotaciones principales

### @RestController

Indica que una clase es un controlador REST.

### @RequestMapping

Permite definir una ruta común para el controlador.

### @GetMapping

Asocia un método con una petición GET.

### @PostMapping

Asocia un método con una petición POST.

### @PutMapping

Asocia un método con una petición PUT.

### @DeleteMapping

Asocia un método con una petición DELETE.

### @RequestBody

Recoge el cuerpo de una petición y permite que Spring convierta el JSON recibido en un objeto Java.

### @PathVariable

Recoge una variable que forma parte de la URL.

```text
/productos/{id}
```

### @RequestParam

Recoge parámetros de la query.

```text
/productos/buscar?nombre=teclado
```

Puede ser opcional:

```java
@RequestParam(required = false)
```

O tener un valor por defecto:

```java
@RequestParam(defaultValue = "asc")
```

## 4. ResponseEntity

`ResponseEntity<?>` permite construir una respuesta indicando:

- código HTTP
- cuerpo
- encabezados

Ejemplos:

```java
return ResponseEntity.ok(producto);
```

Resultado: `200 OK`.

```java
return ResponseEntity.status(201).body(producto);
```

Resultado: `201 Created`.

```java
return ResponseEntity.notFound().build();
```

Resultado: `404 Not Found`.

```java
return ResponseEntity.noContent().build();
```

Resultado: `204 No Content`.

## 5. JSON

Ejemplo:

```json
{
  "id": 1,
  "nombre": "Teclado"
}
```

Spring puede convertir automáticamente entre el objeto Java y JSON mediante los `HttpMessageConverter`.

## 6. Inyección de dependencias

Sin DI:

```java
ProductoRepository repo = new ProductoRepository();
```

Con DI:

```java
public ProductoService(ProductoRepository productoRepository) {
    this.productoRepository = productoRepository;
}
```

Spring proporciona la dependencia.

## 7. Estereotipos

Los vistos en la presentación:

```text
@Component
@Service
@Repository
@Controller
@RestController
```

En este proyecto se utilizan especialmente:

```text
@RestController
@Service
@Repository
```
