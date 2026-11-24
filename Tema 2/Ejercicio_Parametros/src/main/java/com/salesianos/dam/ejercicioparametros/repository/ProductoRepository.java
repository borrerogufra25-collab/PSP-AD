package com.salesianos.dam.ejercicioparametros.repository;

import com.salesianos.dam.ejercicioparametros.model.Producto;
import com.salesianos.dam.ejercicioparametros.service.ProductoService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>,
    JpaSpecificationExecutor<Producto> {


}