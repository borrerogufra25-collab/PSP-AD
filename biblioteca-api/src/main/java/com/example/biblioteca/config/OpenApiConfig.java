package com.example.biblioteca.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de la documentación OpenAPI (Swagger).
 *
 * <p>springdoc ya genera la documentación automáticamente leyendo nuestros controladores.
 * Aquí solo personalizamos la información general que aparece arriba en Swagger UI.
 */
@Configuration
public class OpenApiConfig {

    /**
     * Un método con @Bean le dice a Spring: "crea este objeto y guárdalo en tu contenedor".
     * springdoc buscará un bean de tipo OpenAPI y lo usará como cabecera de la documentación.
     */
    @Bean
    public OpenAPI bibliotecaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Biblioteca")
                        .description("API REST de ejemplo para gestionar autores y libros. "
                                + "Proyecto didáctico con Spring Boot, JPA, H2 y validaciones.")
                        .version("1.0.0"));
    }
}
