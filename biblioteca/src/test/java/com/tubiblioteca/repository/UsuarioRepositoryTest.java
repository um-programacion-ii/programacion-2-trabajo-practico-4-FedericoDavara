package com.tubiblioteca.repository;

import com.tubiblioteca.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioRepositoryTest {

    private UsuarioRepository repository;

    @BeforeEach
    void setUp() {
        repository = new UsuarioRepository.InMemoryUsuarioRepository();
    }

    @Test
    void guardarYBuscarPorId() {
        Usuario usuario = new Usuario(null, "Juan", "juan@mail.com", true);
        Usuario guardado = repository.save(usuario);

        Optional<Usuario> encontrado = repository.findById(guardado.getId());
        assertTrue(encontrado.isPresent());
        assertEquals("Juan", encontrado.get().getNombre());
    }

    @Test
    void buscarPorEmail() {
        repository.save(new Usuario(null, "Ana", "ana@mail.com", true));
        Optional<Usuario> usuario = repository.findByEmail("ana@mail.com");

        assertTrue(usuario.isPresent());
        assertEquals("ana@mail.com", usuario.get().getEmail());
    }

    @Test
    void eliminarPorId() {
        Usuario usuario = repository.save(new Usuario(null, "Borrar", "borrar@mail.com", true));
        Long id = usuario.getId();
        repository.deleteById(id);

        assertFalse(repository.findById(id).isPresent());
    }

    @Test
    void obtenerTodos() {
        repository.save(new Usuario(null, "Uno", "uno@mail.com", true));
        repository.save(new Usuario(null, "Dos", "dos@mail.com", true));

        List<Usuario> lista = repository.findAll();
        assertEquals(2, lista.size());
    }

    @Test
    void verificarExistenciaPorId() {
        Usuario usuario = repository.save(new Usuario(null, "Existente", "ex@mail.com", true));
        assertTrue(repository.existsById(usuario.getId()));
    }
}
