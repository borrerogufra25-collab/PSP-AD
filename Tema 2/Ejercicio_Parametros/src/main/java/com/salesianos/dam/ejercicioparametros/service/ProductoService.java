package com.salesianos.dam.ejercicioparametros.service;

import com.salesianos.dam.ejercicioparametros.repository.ProductoRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductoService {

  private final ProductoRepository productoRepository;

  public ProductoService(ProductoRepository productoRepository) {
    this.productoRepository = productoRepository;
  }
}
