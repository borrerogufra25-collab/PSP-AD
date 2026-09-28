package com.salesianos.dam.primerejemplo;

import ch.qos.logback.core.util.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductoRepository productoRepository;

    @PostMapping
    //public Product addProduct(@RequestBody Product product){
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        if (StringUtil.hasText(product.name()) && StringUtil.hasText(product.price())) {
            return ResponseEntity.status(201)
                    .body(productoRepository.addPrduct(product));
        }
        //  return ResponseEntity.notFound().build();
        return ResponseEntity.badRequest().build();
    }


    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> result = productoRepository.getProducts();
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{name}")
    public ResponseEntity<Product> updateProduct(@PathVariable String name, @RequestBody Product product) {

    }
}
