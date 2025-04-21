package com.alquilatusvehiculos.Repositorios;



import com.alquilatusvehiculos.modelo.Alquiler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alquilatusvehiculos.modelo.Cliente;


import java.util.List;

@Repository
public interface AlquilerRepository extends JpaRepository<Alquiler, Long> {
    List<Alquiler> findByCliente(Cliente cliente);
}
