package com.tubiblioteca.repository;

import com.tubiblioteca.model.Usuario;
import org.springframework.stereotype.Repository;

import java.util.*;

public interface UsuarioRepository {
    Usuario save(Usuario usuario);
    Optional<Usuario> findById(Long id);
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);

    @Repository
    class InMemoryUsuarioRepository implements UsuarioRepository {
        private final Map<Long, Usuario> usuarios = new HashMap<>();
        private Long nextId = 1L;

        @Override
        public Usuario save(Usuario usuario) {
            if (usuario.getId() == null) {
                usuario.setId(nextId++);
            }
            usuarios.put(usuario.getId(), usuario);
            return usuario;
        }

        @Override
        public Optional<Usuario> findById(Long id) {
            return Optional.ofNullable(usuarios.get(id));
        }

        @Override
        public Optional<Usuario> findByEmail(String email) {
            return usuarios.values().stream()
                    .filter(u -> u.getEmail().equals(email))
                    .findFirst();
        }

        @Override
        public List<Usuario> findAll() {
            return new ArrayList<>(usuarios.values());
        }

        @Override
        public void deleteById(Long id) {
            usuarios.remove(id);
        }

        @Override
        public boolean existsById(Long id) {
            return usuarios.containsKey(id);
        }
    }
}
