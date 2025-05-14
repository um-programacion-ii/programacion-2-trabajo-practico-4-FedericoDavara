package com.tubiblioteca.service.impl;

import com.tubiblioteca.model.Usuario;
import com.tubiblioteca.repository.UsuarioRepository;
import com.tubiblioteca.service.UsuarioService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación de la interfaz UsuarioService que maneja la lógica de negocio relacionada con los usuarios.
 */
@Service
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;


    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Busca un usuario por su email.
     *
     * @param email el email del usuario a buscar
     * @return el usuario encontrado
     * @throws RuntimeException si no se encuentra el usuario
     */
    @Override
    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email));
    }

    /**
     * Busca un usuario por su ID.
     *
     * @param id el ID del usuario a buscar
     * @return el usuario encontrado
     * @throws RuntimeException si no se encuentra el usuario
     */
    @Override
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }

    /**
     * Obtiene todos los usuarios.
     *
     * @return una lista de todos los usuarios
     */
    @Override
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    /**
     * Guarda un nuevo usuario o actualiza uno existente.
     *
     * @param usuario el usuario a guardar
     * @return el usuario guardado
     */
    @Override
    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    /**
     * Elimina un usuario por su ID.
     *
     * @param id el ID del usuario a eliminar
     */
    @Override
    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }

    /**
     * Actualiza un usuario existente.
     *
     * @param id      el ID del usuario a actualizar
     * @param usuario el nuevo usuario con los datos actualizados
     * @return el usuario actualizado
     * @throws RuntimeException si no se encuentra el usuario
     */
    @Override
    public Usuario actualizar(Long id, Usuario usuario) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con ID: " + id);
        }
        usuario.setId(id);
        return usuarioRepository.save(usuario);
    }
}
