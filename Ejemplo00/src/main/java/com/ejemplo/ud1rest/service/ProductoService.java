package com.ejemplo.ud1rest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ejemplo.ud1rest.model.Producto;
import com.ejemplo.ud1rest.repository.ProductoRepository;

/*
 * @Service indica que esta clase contiene lógica de la aplicación.
 */
@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    /*
     * Inyección de dependencias mediante constructor.
     *
     * Spring crea ProductoRepository y se lo proporciona
     * automáticamente al crear ProductoService.
     */
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> obtenerPorId(Long id) {
        return productoRepository.findById(id);
    }

    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    public Optional<Producto> actualizar(Long id, Producto datos) {

        Optional<Producto> resultado = productoRepository.findById(id);

        if (resultado.isPresent()) {
            Producto producto = resultado.get();

            producto.setNombre(datos.getNombre());
            producto.setPrecio(datos.getPrecio());

            return Optional.of(productoRepository.save(producto));
        }

        return Optional.empty();
    }

    public boolean eliminar(Long id) {

        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return true;
        }

        return false;
    }

    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }
}
