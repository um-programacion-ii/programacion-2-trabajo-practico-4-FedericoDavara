package com.tubiblioteca.service.impl;

import com.tubiblioteca.model.Libro;
import com.tubiblioteca.repository.LibroRepository;
import com.tubiblioteca.service.LibroService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación de la interfaz LibroService que maneja la lógica de negocio relacionada con los libros.
 */
@Service
public class LibroServiceImpl implements LibroService {
    private final LibroRepository libroRepository;

    public LibroServiceImpl(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    /**
     * Busca un libro por su ISBN.
     *
     * @param isbn el ISBN del libro a buscar
     * @return el libro encontrado
     * @throws RuntimeException si no se encuentra el libro
     */
    @Override
    public Libro buscarPorIsbn(String isbn) {
        return libroRepository.findByIsbn(isbn)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con ISBN: " + isbn));
    }

    /**
     * Busca un libro por su ID.
     *
     * @param id el ID del libro a buscar
     * @return el libro encontrado
     * @throws RuntimeException si no se encuentra el libro
     */
    @Override
    public Libro buscarPorId(Long id) {
        return libroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado con ID: " + id));
    }

    /**
     * Obtiene todos los libros.
     *
     * @return una lista de todos los libros
     */
    @Override
    public List<Libro> obtenerTodos() {
        return libroRepository.findAll();
    }

    /**
     * Guarda un nuevo libro o actualiza uno existente.
     *
     * @param libro el libro a guardar
     * @return el libro guardado
     */
    @Override
    public Libro guardar(Libro libro) {
        return libroRepository.save(libro);
    }

    /**
     * Elimina un libro por su ID.
     *
     * @param id el ID del libro a eliminar
     */
    @Override
    public void eliminar(Long id) {
        libroRepository.deleteById(id);
    }

    /**
     * Actualiza un libro existente.
     *
     * @param id    el ID del libro a actualizar
     * @param libro el libro con los nuevos datos
     * @return el libro actualizado
     * @throws RuntimeException si no se encuentra el libro
     */
    @Override
    public Libro actualizar(Long id, Libro libro) {
        if (!libroRepository.existsById(id)) {
            throw new RuntimeException("No se puede actualizar. Libro no encontrado con ID: " + id);
        }
        libro.setId(id);
        return libroRepository.save(libro);
    }
}
