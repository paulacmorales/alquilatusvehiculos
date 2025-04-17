package com.alquilatusvehiculos.Repositorios;

import com.alquilatusvehiculos.modelo.usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<usuario, Long> {
    Optional<usuario> findByEmail(String email);
}