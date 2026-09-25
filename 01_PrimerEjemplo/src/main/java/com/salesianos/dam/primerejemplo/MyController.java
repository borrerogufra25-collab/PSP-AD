package com.salesianos.dam.primerejemplo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @GetMapping
    public Greeting hello() {
        return new Greeting("Hola mundo", "World");
    }

    record Greeting(String greeting, String name) {
    }
}
