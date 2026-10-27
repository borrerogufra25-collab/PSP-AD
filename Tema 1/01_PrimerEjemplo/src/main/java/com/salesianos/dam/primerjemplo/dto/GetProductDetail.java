package com.salesianos.dam.primerjemplo.dto;

import com.salesianos.dam.primerjemplo.model.Product;

public record GetProductDetail(
    Long id,
    String name,
    Double price,
    String details,
    String categoryName
) {

  public static GetProductDetail of(Product p) {
    String categoryName = String.valueOf(p.getCategory());
    return new GetProductDetail(
        p.getId(),
        p.getName(),
        p.getPrice(),
        p.getDetails(),
        categoryName
    );
  }

}
