package com.salesianos.dam.primerjemplo.dto;

import com.salesianos.dam.primerjemplo.model.Product;

public record ProductUpdateDto {

    private String name;
    private Double price;

    public Product to() {
        return Product.builder()
            .name(name)
            .price(price)
            .build();
    }
}