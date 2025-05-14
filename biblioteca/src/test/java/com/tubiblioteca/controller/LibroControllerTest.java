package com.tubiblioteca.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tubiblioteca.model.Libro;
import com.tubiblioteca.service.LibroService;
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

@WebMvcTest(LibroController.class)
class LibroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibroService libroService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void obtenerTodosLosLibros() throws Exception {
        Libro libro = new Libro(1L, "123456", "Libro 1", "Autor", Libro.EstadoLibro.DISPONIBLE);
        Mockito.when(libroService.obtenerTodos()).thenReturn(Arrays.asList(libro));

        mockMvc.perform(get("/api/libros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Libro 1"));
    }

    @Test
    void obtenerLibroPorId() throws Exception {
        Libro libro = new Libro(1L, "123456", "Libro 1", "Autor", Libro.EstadoLibro.DISPONIBLE);
        Mockito.when(libroService.obtenerPorId(1L)).thenReturn(Optional.of(libro));

        mockMvc.perform(get("/api/libros/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Libro 1"));
    }

    @Test
    void crearLibro() throws Exception {
        Libro libro = new Libro(null, "7891011", "Nuevo Libro", "Autor", Libro.EstadoLibro.DISPONIBLE);
        Libro guardado = new Libro(1L, "7891011", "Nuevo Libro", "Autor", Libro.EstadoLibro.DISPONIBLE);
        Mockito.when(libroService.guardar(Mockito.any())).thenReturn(guardado);

        mockMvc.perform(post("/api/libros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(libro)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void actualizarLibro() throws Exception {
        Libro actualizado = new Libro(1L, "111213", "Libro Actualizado", "Autor", Libro.EstadoLibro.DISPONIBLE);
        Mockito.when(libroService.actualizar(Mockito.eq(1L), Mockito.any())).thenReturn(Optional.of(actualizado));

        mockMvc.perform(put("/api/libros/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Libro Actualizado"));
    }

    @Test
    void eliminarLibro() throws Exception {
        Mockito.when(libroService.eliminar(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/libros/1"))
                .andExpect(status().isNoContent());
    }
}
