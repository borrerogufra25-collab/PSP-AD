package com.salesianos.dam.ejercicioparametros.controller;

import com.salesianos.dam.ejercicioparametros.model.Producto;
import com.salesianos.dam.ejercicioparametros.repository.ProductoRepository;
import com.salesianos.dam.ejercicioparametros.service.ProductoService;
import java.util.Arrays;
import java.util.List;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Log
@RestController
public class ProductoController {

  ProductoRepository productoRepository;
  private final ProductoService productoService;

  public ProductoController(ProductoService productoService) {
    this.productoService = productoService;
  }


  @GetMapping("/api/producto")
  public ResponseEntity<Producto> buscarFiltrado(
      @RequestParam(required = false) String nombre,
      @RequestParam(required = false) MultiValueMap<String, String> rango) {
    return null;
  }

  @GetMapping
  public List<Producto> listar(
      @RequestParam(name = "query", required = false) String q
  ) {
    String[] params = q == null ? new String[]{""} : q.split(",");
    Arrays.asList(params).stream().forEach(log::info);

    return List.of();
  }

}
