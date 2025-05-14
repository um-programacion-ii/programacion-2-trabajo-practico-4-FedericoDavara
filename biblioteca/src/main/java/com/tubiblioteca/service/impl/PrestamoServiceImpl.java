package com.tubiblioteca.service.impl;

import com.tubiblioteca.model.Prestamo;
import com.tubiblioteca.repository.PrestamoRepository;
import com.tubiblioteca.service.PrestamoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación de la interfaz PrestamoService que maneja la lógica de negocio relacionada con los préstamos.
 */
@Service
public class PrestamoServiceImpl implements PrestamoService {
    private final PrestamoRepository prestamoRepository;

    public PrestamoServiceImpl(PrestamoRepository prestamoRepository) {
        this.prestamoRepository = prestamoRepository;
    }

    /**
     * Busca un préstamo por su ID.
     *
     * @param id el ID del préstamo a buscar
     * @return el préstamo encontrado
     * @throws RuntimeException si no se encuentra el préstamo
     */
    @Override
    public Prestamo buscarPorId(Long id) {
        return prestamoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prestamo no encontrado con ID: " + id));
    }

    /**
     * Obtiene todos los préstamos.
     *
     * @return una lista de todos los préstamos
     */
    @Override
    public List<Prestamo> obtenerTodos() {
        return prestamoRepository.findAll();
    }

    /**
     * Guarda un nuevo préstamo o actualiza uno existente.
     *
     * @param prestamo el préstamo a guardar
     * @return el préstamo guardado
     */
    @Override
    public Prestamo guardar(Prestamo prestamo) {
        return prestamoRepository.save(prestamo);
    }

    @Override
    public void eliminar(Long id) {
        prestamoRepository.deleteById(id);
    }

    @Override
    public Prestamo actualizar(Long id, Prestamo prestamo) {
        if (!prestamoRepository.existsById(id)) {
            throw new RuntimeException("Prestamo no encontrado con ID: " + id);
        }
        prestamo.setId(id);
        return prestamoRepository.save(prestamo);
    }
}

