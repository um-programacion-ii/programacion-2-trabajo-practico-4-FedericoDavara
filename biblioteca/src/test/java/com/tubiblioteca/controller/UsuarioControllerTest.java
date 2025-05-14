package com.tubiblioteca.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tubiblioteca.model.Usuario;
import com.tubiblioteca.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void obtenerTodosLosUsuarios() throws Exception {
        Usuario usuario = new Usuario(1L, "Juan", "juan@mail.com", true);
        Mockito.when(usuarioService.obtenerTodos()).thenReturn(Arrays.asList(usuario));

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Juan"));
    }

    @Test
    void obtenerUsuarioPorId() throws Exception {
        Usuario usuario = new Usuario(1L, "Juan", "juan@mail.com", true);
        Mockito.when(usuarioService.obtenerPorId(1L)).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/api/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("juan@mail.com"));
    }

    @Test
    void crearUsuario() throws Exception {
        Usuario usuario = new Usuario(null, "Ana", "ana@mail.com", true);
        Usuario guardado = new Usuario(1L, "Ana", "ana@mail.com", true);
        Mockito.when(usuarioService.guardar(Mockito.any())).thenReturn(guardado);

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuario)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void actualizarUsuario() throws Exception {
        Usuario actualizado = new Usuario(1L, "Ana", "ana@mail.com", false);
        Mockito.when(usuarioService.actualizar(Mockito.eq(1L), Mockito.any())).thenReturn(Optional.of(actualizado));

        mockMvc.perform(put("/api/usuarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value(false));
    }

    @Test
    void eliminarUsuario() throws Exception {
        Mockito.when(usuarioService.eliminar(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/usuarios/1"))
                .andExpect(status().isNoContent());
    }
}
