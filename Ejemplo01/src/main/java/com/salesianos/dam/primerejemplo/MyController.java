package com.salesianos.dam.primerejemplo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MyController {

    @GetMapping("/hello")
    public Greeting hello(
            @RequestParam(defaultValue = "World") String name) {
        return new Greeting("Hola", name);
    }

    record Greeting(String greeting, String name) {
    }


}
