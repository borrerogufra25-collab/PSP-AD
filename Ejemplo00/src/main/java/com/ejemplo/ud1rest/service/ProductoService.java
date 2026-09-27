package com.ejemplo.ud1rest.service;

import com.ejemplo.ud1rest.model.Producto;
import com.ejemplo.ud1rest.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * @Service es otro estereotipo mostrado en la presentación.
 *
 * Esta clase contiene la lógica que utiliza el controlador.
 *
 * Observa que NO hacemos:
 *
 *     new ProductoRepository()
 *
 * La dependencia se recibe desde fuera mediante el constructor.
 * Esto es inyección de dependencias.
 */
@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> obtenerTodos() {
        return productoRepository.obtenerTodos();
    }

    public Producto obtenerPorId(Long id) {
        return productoRepository.obtenerPorId(id);
    }

    public Producto guardar(Producto producto) {
        return productoRepository.guardar(producto);
    }

    public Producto actualizar(Long id, Producto producto) {
        return productoRepository.actualizar(id, producto);
    }

    public boolean borrar(Long id) {
        return productoRepository.borrar(id);
    }
}
