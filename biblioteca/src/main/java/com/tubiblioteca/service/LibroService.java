package com.tubiblioteca.service;

import com.tubiblioteca.model.Libro;

import java.util.List;

/**
 * Interfaz que define los métodos para manejar la lógica de negocio relacionada con los libros.
 */
public interface LibroService {
    Libro buscarPorIsbn(String isbn);
    Libro buscarPorId(Long id);
    List<Libro> obtenerTodos();
    Libro guardar(Libro libro);
    void eliminar(Long id);
    Libro actualizar(Long id, Libro libro);
}
