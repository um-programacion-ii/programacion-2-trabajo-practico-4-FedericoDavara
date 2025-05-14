package com.tubiblioteca.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa un libro en la biblioteca.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Libro {
    private Long id;
    private String isbn;
    private String titulo;
    private String autor;
    private EstadoLibro estado;

    public enum EstadoLibro {
        DISPONIBLE,
        PRESTADO,
        EN_REPARACION
    }
}