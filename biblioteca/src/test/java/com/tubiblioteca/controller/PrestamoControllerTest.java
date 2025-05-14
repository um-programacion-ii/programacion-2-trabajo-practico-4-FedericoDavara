package com.tubiblioteca.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tubiblioteca.model.Libro;
import com.tubiblioteca.model.Prestamo;
import com.tubiblioteca.model.Usuario;
import com.tubiblioteca.service.PrestamoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PrestamoController.class)
class PrestamoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PrestamoService prestamoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void obtenerTodosLosPrestamos() throws Exception {
        Prestamo prestamo = crearPrestamo(1L);
        Mockito.when(prestamoService.obtenerTodos()).thenReturn(Arrays.asList(prestamo));

        mockMvc.perform(get("/api/prestamos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].libro.titulo").value("Libro de Prueba"));
    }

    @Test
    void obtenerPrestamoPorId() throws Exception {
        Prestamo prestamo = crearPrestamo(1L);
        Mockito.when(prestamoService.obtenerPorId(1L)).thenReturn(Optional.of(prestamo));

        mockMvc.perform(get("/api/prestamos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.usuario.nombre").value("Juan"));
    }

    @Test
    void crearPrestamo() throws Exception {
        Prestamo prestamo = crearPrestamo(null);
        Prestamo guardado = crearPrestamo(1L);

        Mockito.when(prestamoService.guardar(Mockito.any())).thenReturn(guardado);

        mockMvc.perform(post("/api/prestamos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(prestamo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void actualizarPrestamo() throws Exception {
        Prestamo actualizado = crearPrestamo(1L);
        actualizado.setFechaDevolucion(LocalDate.now().plusDays(10));
        Mockito.when(prestamoService.actualizar(Mockito.eq(1L), Mockito.any())).thenReturn(Optional.of(actualizado));

        mockMvc.perform(put("/api/prestamos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fechaDevolucion").value(actualizado.getFechaDevolucion().toString()));
    }

    @Test
    void eliminarPrestamo() throws Exception {
        Mockito.when(prestamoService.eliminar(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/prestamos/1"))
                .andExpect(status().isNoContent());
    }

    private Prestamo crearPrestamo(Long id) {
        Libro libro = new Libro(1L, "1234", "Libro de Prueba", "Autor X", Libro.EstadoLibro.DISPONIBLE);
        Usuario usuario = new Usuario(1L, "Juan", "juan@mail.com", true);
        return new Prestamo(id, libro, usuario, LocalDate.now(), LocalDate.now().plusDays(7));
    }
}
