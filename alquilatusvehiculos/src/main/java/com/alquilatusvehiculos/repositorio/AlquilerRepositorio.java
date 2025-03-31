package com.alquilatusvehiculos.repositorio;

import com.alquilatusvehiculos.modelo.Alquiler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlquilerRepositorio extends JpaRepository<Alquiler, Long> {
}
