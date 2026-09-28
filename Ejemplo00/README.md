# UD1 REST - Spring Boot + Lombok + JPA + H2

Proyecto de ejemplo para practicar los conceptos de REST de la UD1:

- GET, POST, PUT y DELETE
- `@RestController`
- `@RequestMapping`
- `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`
- `@PathVariable`
- `@RequestParam`
- `@RequestBody`
- `ResponseEntity`
- JSON
- Inyección de dependencias
- Lombok
- JPA
- H2

## Tecnologías

- Java 17
- Spring Boot 3.5.6
- Spring Web
- Spring Data JPA
- Lombok
- H2

## Estructura

```text
src/main/java/com/ejemplo/ud1rest/
├── Ud1RestApplication.java
├── controller/
│   └── ProductoController.java
├── model/
│   └── Producto.java
├── repository/
│   └── ProductoRepository.java
└── service/
    └── ProductoService.java
```

## Qué hace cada capa

### Controller
Recibe las peticiones HTTP y devuelve las respuestas REST.

### Service
Contiene la lógica que queremos separar del controlador.

### Repository
Se encarga de comunicarse con la base de datos mediante JPA.

### Entity
`Producto` representa una tabla de la base de datos.

## H2

La aplicación utiliza H2 como base de datos en memoria.

Configuración en:

`src/main/resources/application.properties`

La consola H2 queda disponible en:

`http://localhost:8080/h2-console`

Datos de conexión:

- JDBC URL: `jdbc:h2:mem:productosdb`
- User Name: `sa`
- Password: vacío

## Ejemplos para Postman

### Obtener todos

GET `http://localhost:8080/productos`

### Obtener uno

GET `http://localhost:8080/productos/1`

### Buscar por nombre

GET `http://localhost:8080/productos/buscar?nombre=teclado`

### Crear

POST `http://localhost:8080/productos`

Body -> raw -> JSON:

```json
{
  "nombre": "Teclado",
  "precio": 35.99
}
```

### Modificar

PUT `http://localhost:8080/productos/1`

Body:

```json
{
  "nombre": "Teclado mecánico",
  "precio": 49.99
}
```

### Eliminar

DELETE `http://localhost:8080/productos/1`

## Importante

H2 es una base de datos en memoria. Al detener la aplicación, los datos se pierden.

Este proyecto añade Lombok, JPA y H2 porque se han solicitado expresamente. No añade seguridad, JWT, DTO, Swagger/OpenAPI, HATEOAS ni PATCH.
