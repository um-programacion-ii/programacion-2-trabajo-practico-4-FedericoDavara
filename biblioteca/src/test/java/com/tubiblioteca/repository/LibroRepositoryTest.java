package com.tubiblioteca.repository;

import com.tubiblioteca.model.Libro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class LibroRepositoryTest {

    private LibroRepository repository;

    @BeforeEach
    void setUp() {
        repository = new LibroRepository.InMemoryLibroRepository();
    }

    @Test
    void guardarYBuscarPorId() {
        Libro libro = new Libro(null, "111", "Clean Code", "Robert C. Martin", Libro.EstadoLibro.DISPONIBLE);
        Libro guardado = repository.save(libro);

        Optional<Libro> encontrado = repository.findById(guardado.getId());
        assertTrue(encontrado.isPresent());
        assertEquals("Clean Code", encontrado.get().getTitulo());
    }

    @Test
    void buscarPorIsbn() {
        repository.save(new Libro(null, "222", "Libro 1", "Autor", Libro.EstadoLibro.DISPONIBLE));
        Optional<Libro> libro = repository.findByIsbn("222");

        assertTrue(libro.isPresent());
        assertEquals("222", libro.get().getIsbn());
    }

    @Test
    void eliminarPorId() {
        Libro libro = repository.save(new Libro(null, "333", "Eliminar", "Autor", Libro.EstadoLibro.DISPONIBLE));
        Long id = libro.getId();
        repository.deleteById(id);

        assertFalse(repository.findById(id).isPresent());
    }

    @Test
    void obtenerTodos() {
        repository.save(new Libro(null, "444", "Uno", "Autor", Libro.EstadoLibro.DISPONIBLE));
        repository.save(new Libro(null, "555", "Dos", "Autor", Libro.EstadoLibro.DISPONIBLE));

        List<Libro> lista = repository.findAll();
        assertEquals(2, lista.size());
    }

    @Test
    void verificarExistenciaPorId() {
        Libro libro = repository.save(new Libro(null, "666", "Existente", "Autor", Libro.EstadoLibro.DISPONIBLE));
        assertTrue(repository.existsById(libro.getId()));
    }
}
