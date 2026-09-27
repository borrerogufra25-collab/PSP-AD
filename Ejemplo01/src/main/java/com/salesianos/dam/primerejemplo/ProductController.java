package com.salesianos.dam.primerejemplo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private final ProductoRepository productoRepository;

    @PostMapping
    public Product addProduct
}
