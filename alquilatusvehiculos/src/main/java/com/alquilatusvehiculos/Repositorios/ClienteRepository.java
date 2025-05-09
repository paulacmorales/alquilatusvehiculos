package com.alquilatusvehiculos.Repositorios;


import com.alquilatusvehiculos.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Cliente findByUsername(String username);



}
