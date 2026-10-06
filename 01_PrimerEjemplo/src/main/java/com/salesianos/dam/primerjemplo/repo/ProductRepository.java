package com.salesianos.dam.primerjemplo.repo;

import com.salesianos.dam.primerjemplo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository
    extends JpaRepository<Product, Long> {

}