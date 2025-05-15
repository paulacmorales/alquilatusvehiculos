package com.alquilatusvehiculos.controlador.api;

import com.alquilatusvehiculos.modelo.Alquiler;
import com.alquilatusvehiculos.repositorio.AlquilerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alquileres")
public class AlquilerRestController {

    @Autowired
    private AlquilerRepository alquilerRepository;

    @GetMapping
    public List<Alquiler> getAllAlquileres() {
        return alquilerRepository.findAll();
    }


}
