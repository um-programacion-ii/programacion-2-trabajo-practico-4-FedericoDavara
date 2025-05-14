package com.tubiblioteca.repository;

import com.tubiblioteca.model.Libro;
import com.tubiblioteca.model.Prestamo;
import com.tubiblioteca.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PrestamoRepositoryTest {

    private PrestamoRepository repository;

    @BeforeEach
    void setUp() {
        repository = new PrestamoRepository.InMemoryPrestamoRepository();
    }

    @Test
    void guardarYBuscarPorId() {
        Libro libro = new Libro(1L, "Libro 1", "Autor 1", "ISBN001", Libro.EstadoLibro.DISPONIBLE);
        Usuario usuario = new Usuario(1L, "Usuario 1", "usuario1@mail.com", true);
        Prestamo prestamo = new Prestamo(null, libro, usuario, LocalDate.now(), LocalDate.now().plusDays(7));

        Prestamo guardado = repository.save(prestamo);
        Optional<Prestamo> encontrado = repository.findById(guardado.getId());

        assertTrue(encontrado.isPresent());
        assertEquals("Libro 1", encontrado.get().getLibro().getTitulo());
        assertEquals("Usuario 1", encontrado.get().getUsuario().getNombre());
    }

    @Test
    void eliminarPorId() {
        Prestamo prestamo = new Prestamo(null, new Libro(2L, "L2", "A2", "ISBN002", Libro.EstadoLibro.DISPONIBLE),
                new Usuario(2L, "U2", "u2@mail.com", true), LocalDate.now(), LocalDate.now().plusDays(5));
        Prestamo guardado = repository.save(prestamo);

        repository.deleteById(guardado.getId());
        assertFalse(repository.findById(guardado.getId()).isPresent());
    }

    @Test
    void obtenerTodos() {
        repository.save(new Prestamo(null,
                new Libro(3L, "L3", "A3", "ISBN003", Libro.EstadoLibro.DISPONIBLE),
                new Usuario(3L, "U3", "u3@mail.com", true),
                LocalDate.now(), LocalDate.now().plusDays(3)));

        repository.save(new Prestamo(null,
                new Libro(4L, "L4", "A4", "ISBN004", Libro.EstadoLibro.DISPONIBLE),
                new Usuario(4L, "U4", "u4@mail.com", true),
                LocalDate.now(), LocalDate.now().plusDays(4)));

        List<Prestamo> prestamos = repository.findAll();
        assertEquals(2, prestamos.size());
    }

    @Test
    void verificarExistenciaPorId() {
        Prestamo prestamo = new Prestamo(null,
                new Libro(5L, "L5", "A5", "ISBN005", Libro.EstadoLibro.DISPONIBLE),
                new Usuario(5L, "U5", "u5@mail.com", true),
                LocalDate.now(), LocalDate.now().plusDays(10));
        Prestamo guardado = repository.save(prestamo);

        assertTrue(repository.existsById(guardado.getId()));
    }
}

