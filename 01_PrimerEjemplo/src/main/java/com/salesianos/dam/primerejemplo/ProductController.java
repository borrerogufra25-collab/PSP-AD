package com.salesianos.dam.primerejemplo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

        @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return ResponseEntity.status(201).body(productRepository)
    }

    record Product(String name, String price) {
    }

    @GetMapping
    pub
}
