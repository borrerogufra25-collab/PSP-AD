package com.ejemplo.ud1rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ejemplo.ud1rest.model.Producto;

/*
 * @Repository identifica esta interfaz como componente de acceso a datos.
 *
 * JpaRepository ya proporciona operaciones como:
 * - findAll()
 * - findById()
 * - save()
 * - deleteById()
 * - existsById()
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    /*
     * Spring Data JPA crea la consulta automáticamente a partir
     * del nombre del método.
     */
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
}
