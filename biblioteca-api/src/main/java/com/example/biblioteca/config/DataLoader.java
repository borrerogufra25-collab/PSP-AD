package com.example.biblioteca.config;

import com.example.biblioteca.model.Autor;
import com.example.biblioteca.model.Libro;
import com.example.biblioteca.repository.AutorRepository;
import com.example.biblioteca.repository.LibroRepository;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Carga datos de ejemplo al arrancar la aplicación.
 *
 * <p>Como la base de datos H2 está en memoria, siempre arranca vacía. Así, cada vez
 * que iniciamos la aplicación tenemos algunos datos con los que probar la API.
 *
 * <p>Un {@link CommandLineRunner} es un componente cuyo método {@code run} Spring
 * ejecuta una sola vez, justo cuando la aplicación ha terminado de arrancar.
 *
 * <p>{@code @Slf4j} (Lombok) crea automáticamente un atributo {@code log} para escribir
 * mensajes en la consola. {@code @RequiredArgsConstructor} (Lombok) crea un constructor con
 * los atributos {@code final}: así Spring nos "inyecta" los repositorios.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final AutorRepository autorRepository;
    private final LibroRepository libroRepository;

    @Override
    public void run(String... args) {
        // Por si acaso: si ya hay datos, no volvemos a insertar
        if (autorRepository.count() > 0) {
            return;
        }

        Autor cervantes = autorRepository.save(
                Autor.builder().nombre("Miguel de Cervantes").nacionalidad("Española").build());
        Autor garciaMarquez = autorRepository.save(
                Autor.builder().nombre("Gabriel García Márquez").nacionalidad("Colombiana").build());

        libroRepository.save(Libro.builder()
                .titulo("Don Quijote de la Mancha")
                .isbn("978-84-376-0494-7")
                .anioPublicacion(1605)
                .precio(new BigDecimal("19.95"))
                .autor(cervantes)
                .build());

        libroRepository.save(Libro.builder()
                .titulo("Cien años de soledad")
                .isbn("978-84-397-2077-7")
                .anioPublicacion(1967)
                .precio(new BigDecimal("16.50"))
                .autor(garciaMarquez)
                .build());

        libroRepository.save(Libro.builder()
                .titulo("El amor en los tiempos del cólera")
                .isbn("978-84-397-0262-9")
                .anioPublicacion(1985)
                .precio(new BigDecimal("14.90"))
                .autor(garciaMarquez)
                .build());

        log.info("Datos de ejemplo cargados: {} autores y {} libros",
                autorRepository.count(), libroRepository.count());
    }
}
