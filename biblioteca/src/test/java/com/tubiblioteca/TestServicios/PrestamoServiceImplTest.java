package com.tubiblioteca.TestServicios;

import com.tubiblioteca.model.Libro;
import com.tubiblioteca.model.Usuario;
import com.tubiblioteca.model.Prestamo;
import com.tubiblioteca.repository.PrestamoRepository;
import com.tubiblioteca.service.impl.PrestamoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrestamoServiceImplTest {

    @Mock
    private PrestamoRepository prestamoRepository;

    @InjectMocks
    private PrestamoServiceImpl prestamoService;

    @Test
    void buscarPorId_devuelvePrestamo() {
        Prestamo prestamo = new Prestamo(1L, new Libro(), new Usuario(), LocalDate.now(), LocalDate.now().plusDays(7));
        when(prestamoRepository.findById(1L)).thenReturn(Optional.of(prestamo));

        Prestamo resultado = prestamoService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(prestamoRepository).findById(1L);
    }

    @Test
    void buscarPorId_noEncontrado_lanzaExcepcion() {
        when(prestamoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> prestamoService.buscarPorId(99L));
    }

    @Test
    void guardarPrestamo_devuelveGuardado() {
        Prestamo prestamo = new Prestamo(null, new Libro(), new Usuario(), LocalDate.now(), LocalDate.now().plusDays(7));
        Prestamo guardado = new Prestamo(1L, prestamo.getLibro(), prestamo.getUsuario(), prestamo.getFechaPrestamo(), prestamo.getFechaDevolucion());

        when(prestamoRepository.save(prestamo)).thenReturn(guardado);

        Prestamo resultado = prestamoService.guardar(prestamo);

        assertEquals(1L, resultado.getId());
        verify(prestamoRepository).save(prestamo);
    }

    @Test
    void obtenerTodos_devuelveLista() {
        when(prestamoRepository.findAll()).thenReturn(List.of(
                new Prestamo(1L, new Libro(), new Usuario(), LocalDate.now(), LocalDate.now().plusDays(7))
        ));

        List<Prestamo> prestamos = prestamoService.obtenerTodos();

        assertEquals(1, prestamos.size());
        verify(prestamoRepository).findAll();
    }
}
