# Biblioteca API — Proyecto de ejemplo con Spring Boot

API REST para gestionar **autores** y **libros**. Está pensada para **estudiar**: todo el código
está comentado en español y solo usa conceptos de nivel medio.

## Tecnologías

| Tecnología | Para qué se usa aquí |
|---|---|
| Java 25 | Lenguaje (records, `var`, streams) |
| Spring Boot 4.1.1 | Framework: configura y arranca todo |
| Spring Web MVC | Controladores REST (JSON sobre HTTP) |
| Spring Data JPA (Hibernate) | Acceso a base de datos con objetos |
| H2 | Base de datos en memoria (no hay que instalar nada) |
| Bean Validation | Validar los datos de entrada (`@NotBlank`, `@Min`...) |
| Lombok | Menos código repetitivo (getters, constructores, builder, logs) |
| springdoc-openapi | Documentación automática + Swagger UI |

## Cómo ejecutarlo

Requisitos: **JDK 25** y **Maven 3.9+** (o abrir el proyecto en IntelliJ / Eclipse / VS Code).

```bash
mvn spring-boot:run      # arrancar la aplicación
mvn test                 # ejecutar los tests
```

Una vez arrancada:

| Qué | URL |
|---|---|
| Swagger UI (probar la API desde el navegador) | http://localhost:8080/swagger-ui.html |
| Documentación OpenAPI en JSON | http://localhost:8080/v3/api-docs |
| Consola de H2 (ver las tablas) | http://localhost:8080/h2-console |

Datos de la consola H2: JDBC URL `jdbc:h2:mem:bibliotecadb`, usuario `sa`, contraseña vacía.

> **Si usas IntelliJ/Eclipse**: activa el procesamiento de anotaciones (*annotation processing*)
> y el plugin de Lombok, si tu IDE te lo pide.

## Estructura del proyecto

```
src/main/java/com/example/biblioteca
├── BibliotecaApplication.java     Punto de entrada (main)
├── config/
│   ├── OpenApiConfig.java         Título y descripción de Swagger
│   └── DataLoader.java            Inserta datos de ejemplo al arrancar
├── model/                         ENTIDADES JPA (tablas)
│   ├── Autor.java
│   └── Libro.java                 (muchos libros -> un autor)
├── repository/                    ACCESO A DATOS (interfaces Spring Data)
│   ├── AutorRepository.java
│   └── LibroRepository.java       (con "query methods" derivados del nombre)
├── dto/                           OBJETOS DE ENTRADA/SALIDA de la API (records)
│   ├── AutorRequest / AutorResponse
│   └── LibroRequest / LibroResponse
├── service/                       LÓGICA DE NEGOCIO
│   ├── AutorService.java
│   └── LibroService.java
├── controller/                    ENDPOINTS REST
│   ├── AutorController.java
│   └── LibroController.java
└── exception/                     MANEJO DE ERRORES
    ├── RecursoNoEncontradoException (-> 404)
    ├── ConflictoException           (-> 409)
    ├── ErrorResponse                (formato JSON de los errores)
    └── GlobalExceptionHandler       (@RestControllerAdvice)
```

## Arquitectura en capas y flujo de una petición

```
Cliente --HTTP/JSON--> Controller --> Service --> Repository --> H2
                          |             |            |
                    valida (@Valid)  reglas de    SQL generado
                    y traduce JSON   negocio      por Spring Data
```

Ejemplo: `POST /api/libros`

1. El **controlador** recibe el JSON y lo convierte en un `LibroRequest`.
2. `@Valid` comprueba las validaciones. Si fallan → **400** (lo gestiona `GlobalExceptionHandler`).
3. El **servicio** aplica las reglas: ¿existe el autor? (si no → **404**), ¿ISBN repetido? (si sí → **409**).
4. El **repositorio** guarda la entidad (`INSERT`).
5. El servicio convierte la entidad en `LibroResponse` y el controlador la devuelve con **201 Created**.

## Endpoints

### Autores — `/api/autores`

| Método | URL | Descripción | Respuestas |
|---|---|---|---|
| GET | `/api/autores` | Listar todos | 200 |
| GET | `/api/autores/{id}` | Obtener uno | 200, 404 |
| GET | `/api/autores/{id}/libros` | Libros del autor | 200, 404 |
| POST | `/api/autores` | Crear | 201, 400 |
| PUT | `/api/autores/{id}` | Modificar | 200, 400, 404 |
| DELETE | `/api/autores/{id}` | Eliminar | 204, 404, 409 (tiene libros) |

### Libros — `/api/libros`

| Método | URL | Descripción | Respuestas |
|---|---|---|---|
| GET | `/api/libros` | Listar todos (o `?titulo=texto`) | 200 |
| GET | `/api/libros/{id}` | Obtener uno | 200, 404 |
| POST | `/api/libros` | Crear | 201, 400, 404 (autor), 409 (ISBN) |
| PUT | `/api/libros/{id}` | Modificar | 200, 400, 404, 409 |
| DELETE | `/api/libros/{id}` | Eliminar | 204, 404 |

El archivo `requests.http` contiene una petición de ejemplo para cada caso (incluidos los errores).

### Ejemplo con curl

```bash
# Crear un autor
curl -X POST http://localhost:8080/api/autores \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Isabel Allende","nacionalidad":"Chilena"}'

# Datos no válidos -> 400 con detalle por campo
curl -X POST http://localhost:8080/api/libros \
  -H "Content-Type: application/json" \
  -d '{"titulo":"","isbn":"abc","anioPublicacion":5,"precio":-3}'
```

Respuesta de error de ejemplo:

```json
{
  "timestamp": "2026-10-20T10:15:30",
  "status": 400,
  "error": "Bad Request",
  "mensaje": "Datos de entrada no válidos",
  "detalles": {
    "titulo": "El título es obligatorio",
    "isbn": "El ISBN debe tener entre 10 y 17 caracteres (solo dígitos y guiones)",
    "anioPublicacion": "El año debe ser como mínimo 1000",
    "precio": "El precio no puede ser negativo",
    "autorId": "El id del autor es obligatorio"
  }
}
```

## Conceptos clave (glosario de estudio)

- **Inyección de dependencias**: no hacemos `new AutorService()`; Spring crea los objetos y los
  pasa por el constructor. Con Lombok, `@RequiredArgsConstructor` genera ese constructor.
- **Entidad vs DTO**: la entidad representa la tabla; el DTO representa lo que viaja por la API.
  Separarlos evita exponer datos internos y permite validar la entrada sin tocar la entidad.
- **Record**: clase inmutable compacta de Java (ideal para DTOs).
- **Spring Data JPA**: solo definimos interfaces; los métodos como `findByAutorId` se convierten
  en consultas SQL leyendo su nombre.
- **`@Transactional`**: agrupa operaciones en una transacción (todo o nada). Con *dirty checking*,
  los cambios hechos a una entidad gestionada se guardan solos al terminar.
- **LAZY**: la relación `Libro → Autor` se carga solo cuando se usa. Por eso convertimos a DTO
  dentro de los métodos del servicio (que son transaccionales).
- **Bean Validation**: las anotaciones del DTO + `@Valid` en el controlador validan automáticamente.
- **`@RestControllerAdvice`**: un único sitio donde convertir excepciones en respuestas HTTP.
- **Códigos HTTP usados**: 200 OK, 201 Created, 204 No Content, 400 Bad Request, 404 Not Found,
  409 Conflict, 500 Internal Server Error.

## Notas técnicas

- `ddl-auto=create-drop`: Hibernate crea las tablas al arrancar y las borra al parar.
- Con **Java 23+** hay que declarar Lombok como *annotation processor* en el `pom.xml`
  (ya está hecho y comentado).
- En **Spring Boot 4** el starter web se llama `spring-boot-starter-webmvc` y la consola de H2
  está en el módulo `spring-boot-h2console`.
- springdoc-openapi **3.x** es la serie compatible con Spring Boot 4.

## Ideas para ampliar (ejercicios)

1. Añadir el campo `editorial` a `Libro` y un filtro `GET /api/libros?editorial=...`.
2. Crear una entidad `Categoria` y relacionarla con `Libro`.
3. Añadir paginación con `Pageable` en `GET /api/libros`.
4. Escribir tests del controlador con `MockMvc`.
5. Cambiar H2 por PostgreSQL o MySQL mediante `application.properties`.
