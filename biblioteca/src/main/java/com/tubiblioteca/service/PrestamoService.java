package com.tubiblioteca.service;

import com.tubiblioteca.model.Prestamo;

import java.util.List;


/**
 * Interfaz que define los métodos para manejar la lógica de negocio relacionada con los préstamos.
 */
public interface PrestamoService {
    Prestamo buscarPorId(Long id);
    List<Prestamo> obtenerTodos();
    Prestamo guardar(Prestamo prestamo);
    void eliminar(Long id);
    Prestamo actualizar(Long id, Prestamo prestamo);
}
