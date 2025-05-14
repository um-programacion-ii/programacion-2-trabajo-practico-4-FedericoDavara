package com.tubiblioteca.repository;

import com.tubiblioteca.model.Prestamo;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Interfaz para manejar las operaciones CRUD de los préstamos en la biblioteca.
 */
public interface PrestamoRepository {
    Prestamo save(Prestamo prestamo);
    Optional<Prestamo> findById(Long id);
    List<Prestamo> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);

    // Implementación en memoria para pruebas
    @Repository
    class InMemoryPrestamoRepository implements PrestamoRepository {
        private final Map<Long, Prestamo> prestamos = new HashMap<>();
        private Long nextId = 1L;

        @Override
        public Prestamo save(Prestamo prestamo) {
            if (prestamo.getId() == null) {
                prestamo.setId(nextId++);
            }
            prestamos.put(prestamo.getId(), prestamo);
            return prestamo;
        }

        @Override
        public Optional<Prestamo> findById(Long id) {
            return Optional.ofNullable(prestamos.get(id));
        }

        @Override
        public List<Prestamo> findAll() {
            return new ArrayList<>(prestamos.values());
        }

        @Override
        public void deleteById(Long id) {
            prestamos.remove(id);
        }

        @Override
        public boolean existsById(Long id) {
            return prestamos.containsKey(id);
        }
    }
}
