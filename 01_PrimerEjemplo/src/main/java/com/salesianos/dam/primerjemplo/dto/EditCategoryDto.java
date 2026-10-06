package com.salesianos.dam.primerjemplo.dto;

import com.salesianos.dam.primerjemplo.model.Category;

public record EditCategoryDto(
    String name
) {
    public Category to() {
        return Category.builder()
                       .name(name)
                       .build();
    }
}
