package com.tubiblioteca.TestServicios;

import com.tubiblioteca.model.Libro;
import com.tubiblioteca.repository.LibroRepository;
import com.tubiblioteca.service.impl.LibroServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LibroServiceImplTest {

    @Mock
    private LibroRepository libroRepository;

    @InjectMocks
    private LibroServiceImpl libroService;

    @Test
    void buscarPorIsbn_devuelveLibro() {
        String isbn = "ABC123";
        Libro libro = new Libro(1L, isbn, "Título", "Autor", Libro.EstadoLibro.DISPONIBLE);
        when(libroRepository.findByIsbn(isbn)).thenReturn(Optional.of(libro));

        Libro resultado = libroService.buscarPorIsbn(isbn);

        assertNotNull(resultado);
        assertEquals(isbn, resultado.getIsbn());
        verify(libroRepository).findByIsbn(isbn);
    }

    @Test
    void buscarPorIsbn_noEncontrado_lanzaExcepcion() {
        when(libroRepository.findByIsbn("XXX")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> libroService.buscarPorIsbn("XXX"));
    }

    @Test
    void guardarLibro_devuelveLibroGuardado() {
        Libro libro = new Libro(null, "NEW123", "Nuevo", "Autor", Libro.EstadoLibro.DISPONIBLE) ;
        Libro guardado = new Libro(1L, "NEW123", "Nuevo", "Autor", Libro.EstadoLibro.DISPONIBLE);
        when(libroRepository.save(libro)).thenReturn(guardado);

        Libro resultado = libroService.guardar(libro);

        assertEquals(1L, resultado.getId());
        verify(libroRepository).save(libro);
    }

    @Test
    void obtenerTodos_devuelveLista() {
        when(libroRepository.findAll()).thenReturn(List.of(
                new Libro(1L, "ISBN1", "Título 1", "Autor 1", Libro.EstadoLibro.DISPONIBLE)
        ));

        List<Libro> libros = libroService.obtenerTodos();

        assertEquals(1, libros.size());
        verify(libroRepository).findAll();
    }
}
