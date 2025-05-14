package com.tubiblioteca.service;

import com.tubiblioteca.model.Usuario;

import java.util.List;

/**
 * Interfaz que define los métodos para manejar la lógica de negocio relacionada con los usuarios.
 */
public interface UsuarioService {
    Usuario buscarPorEmail(String email);
    Usuario buscarPorId(Long id);
    List<Usuario> obtenerTodos();
    Usuario guardar(Usuario usuario);
    void eliminar(Long id);
    Usuario actualizar(Long id, Usuario usuario);
}
