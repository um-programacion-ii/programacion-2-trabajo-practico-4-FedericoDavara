package com.tubiblioteca.TestServicios;

import com.tubiblioteca.model.Usuario;
import com.tubiblioteca.repository.UsuarioRepository;
import com.tubiblioteca.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Test
    void buscarPorId_devuelveUsuario() {
        Usuario usuario = new Usuario(1L, "Juan Pérez", "juan@email.com",true);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario resultado = usuarioService.buscarPorId(1L);

        assertEquals("Juan Pérez", resultado.getNombre());
        verify(usuarioRepository).findById(1L);
    }

    @Test
    void buscarPorId_noEncontrado_lanzaExcepcion() {
        when(usuarioRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> usuarioService.buscarPorId(2L));
    }

    @Test
    void guardarUsuario_devuelveUsuarioGuardado() {
        Usuario usuario = new Usuario(null, "Nuevo", "nuevo@mail.com",true);
        Usuario guardado = new Usuario(1L, "Nuevo", "nuevo@mail.com",true);
        when(usuarioRepository.save(usuario)).thenReturn(guardado);

        Usuario resultado = usuarioService.guardar(usuario);

        assertEquals(1L, resultado.getId());
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void obtenerTodos_devuelveLista() {
        when(usuarioRepository.findAll()).thenReturn(List.of(
                new Usuario(1L, "Usuario 1", "u1@mail.com",true)
        ));

        List<Usuario> usuarios = usuarioService.obtenerTodos();

        assertEquals(1, usuarios.size());
        verify(usuarioRepository).findAll();
    }
}
