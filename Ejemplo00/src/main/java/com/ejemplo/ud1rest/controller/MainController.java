package com.ejemplo.ud1rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * @RestController indica que esta clase es un controlador REST.
 *
 * La presentación explica que un controlador REST es una clase POJO
 * que contiene un conjunto de endpoints.
 */
@RestController
public class MainController {

    /*
     * @GetMapping asocia este método con:
     *
     *     GET /hello
     *
     * La ruta completa será:
     *
     *     http://localhost:8080/hello
     */
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello World";
    }
}
