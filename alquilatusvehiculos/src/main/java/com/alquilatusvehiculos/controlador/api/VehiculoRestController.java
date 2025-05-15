package com.alquilatusvehiculos.controlador.api;

import com.alquilatusvehiculos.modelo.Vehiculo;
import com.alquilatusvehiculos.repositorio.VehiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
@RequiredArgsConstructor
public class VehiculoRestController {

    private final VehiculoRepository vehiculoRepository;

    @GetMapping
    public List<Vehiculo> listarVehiculos() {
        return vehiculoRepository.findAll();
    }
}
