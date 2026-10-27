package com.salesianos.dam.primerjemplo.dto;

import com.salesianos.dam.primerjemplo.model.Category;

public record GetCategoryDetail(
    Long id,
    String name
) {

  public static GetCategoryDetail of(Category c) {
    return new GetCategoryDetail(
        c.getId(),
        c.getName()
    );
  }
}
