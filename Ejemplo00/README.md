# UD1 - Desarrollo de una API REST con Spring Boot

Proyecto didáctico basado EXCLUSIVAMENTE en los contenidos prácticos de la presentación UD1.

## Contenidos incluidos

- HTTP: GET, POST, PUT y DELETE.
- Códigos HTTP: 200, 201, 204, 400 y 404.
- JSON.
- `@RestController`.
- `@RequestMapping`.
- `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`.
- `@RequestBody`.
- `@PathVariable`.
- `@RequestParam`.
- `ResponseEntity<?>`.
- `@Service`, `@Repository`, `@Component` y `@Controller` como estereotipos.
- Inyección de dependencias mediante constructor.
- Conversión automática Java <-> JSON mediante el mecanismo de `HttpMessageConverter`.

## Lo que NO se incluye

Para respetar el alcance de la presentación, este proyecto NO introduce:
- JPA/Hibernate.
- Bases de datos.
- DTO.
- Lombok.
- Seguridad/JWT.
- Validación.
- Excepciones personalizadas.
- Swagger/OpenAPI.
- HATEOAS.
- PATCH.
- Microservicios.

## Ejecutar

Desde la carpeta del proyecto:

```bash
mvn spring-boot:run
```

Servidor por defecto:

```text
http://localhost:8080
```

También se puede ejecutar la clase `Ud1RestApplication` desde Eclipse/STS/IntelliJ.

## Ejemplos para probar

### 1. Primer endpoint

GET

```text
http://localhost:8080/hello
```

Respuesta:

```text
Hello World
```

### 2. Obtener un producto

GET

```text
http://localhost:8080/productos/1
```

Respuesta:

```json
{
  "id": 1,
  "nombre": "Teclado"
}
```

### 3. Crear un producto

POST

```text
http://localhost:8080/productos
```

Body JSON:

```json
{
  "id": 3,
  "nombre": "Monitor"
}
```

La aplicación recibe ese JSON mediante `@RequestBody`.

### 4. Obtener todos los productos

GET

```text
http://localhost:8080/productos
```

### 5. Actualizar

PUT

```text
http://localhost:8080/productos/1
```

Body:

```json
{
  "id": 1,
  "nombre": "Teclado mecánico"
}
```

### 6. Borrar

DELETE

```text
http://localhost:8080/productos/1
```

### 7. PathVariable

GET

```text
http://localhost:8080/productos/1
```

El `1` forma parte de la ruta y se recibe con `@PathVariable`.

### 8. RequestParam

GET

```text
http://localhost:8080/productos/buscar?nombre=teclado
```

También puedes probar:

```text
http://localhost:8080/productos/buscar
```

El parámetro es opcional porque se ha configurado `required=false`.

### 9. Varios parámetros de query

GET

```text
http://localhost:8080/productos/filtro?nombre=teclado&orden=desc
```

Los parámetros de la query se reciben con `@RequestParam`.

## Importante

Los datos de los productos se guardan solamente en memoria mientras la aplicación está funcionando. Al reiniciar la aplicación, vuelven a los datos iniciales.
