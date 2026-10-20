package com.example.biblioteca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal: punto de entrada de la aplicación.
 *
 * <p>{@code @SpringBootApplication} activa tres cosas a la vez:
 * <ul>
 *   <li><b>@Configuration</b>: esta clase puede definir beans.</li>
 *   <li><b>@EnableAutoConfiguration</b>: Spring Boot configura automáticamente lo que
 *       detecta en el classpath (servidor web, JPA, H2, etc.).</li>
 *   <li><b>@ComponentScan</b>: busca clases con @Component, @Service, @RestController...
 *       en este paquete y en todos sus subpaquetes (por eso esta clase está en el
 *       paquete raíz: com.example.biblioteca).</li>
 * </ul>
 */
@SpringBootApplication
public class BibliotecaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BibliotecaApplication.class, args);
    }
}
