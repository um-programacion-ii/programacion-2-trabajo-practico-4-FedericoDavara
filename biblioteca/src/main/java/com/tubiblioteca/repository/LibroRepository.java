package com.tubiblioteca.repository;

import com.tubiblioteca.model.Libro;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Interfaz para manejar las operaciones CRUD de los libros en la biblioteca.
 */
public interface LibroRepository {
    Libro save(Libro libro);
    Optional<Libro> findById(Long id);
    Optional<Libro> findByIsbn(String isbn);
    List<Libro> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);

    // Implementación en memoria para pruebas
    @Repository
    class InMemoryLibroRepository implements LibroRepository {
        private final Map<Long, Libro> libros = new HashMap<>();
        private Long nextId = 1L;

        @Override
        public Libro save(Libro libro) {
            if (libro.getId() == null) {
                libro.setId(nextId++);
            }
            libros.put(libro.getId(), libro);
            return libro;
        }

        @Override
        public Optional<Libro> findById(Long id) {
            return Optional.ofNullable(libros.get(id));
        }

        @Override
        public Optional<Libro> findByIsbn(String isbn) {
            return libros.values().stream()
                    .filter(l -> l.getIsbn().equals(isbn))
                    .findFirst();
        }

        @Override
        public List<Libro> findAll() {
            return new ArrayList<>(libros.values());
        }

        @Override
        public void deleteById(Long id) {
            libros.remove(id);
        }

        @Override
        public boolean existsById(Long id) {
            return libros.containsKey(id);
        }
    }
}

