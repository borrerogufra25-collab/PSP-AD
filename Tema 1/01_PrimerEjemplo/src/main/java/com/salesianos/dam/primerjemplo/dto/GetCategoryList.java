package com.salesianos.dam.primerjemplo.dto;

import com.salesianos.dam.primerjemplo.model.Category;

public record GetCategoryList(
    Long id,
    String name
) {

  public static GetCategoryList of(Category c) {
    return new GetCategoryList(c.getId(),
        c.getName()
    );
  }
}
