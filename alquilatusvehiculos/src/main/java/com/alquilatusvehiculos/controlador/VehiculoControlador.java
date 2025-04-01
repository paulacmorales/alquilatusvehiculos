package com.alquilatusvehiculos.controlador;

import com.alquilatusvehiculos.modelo.Vehiculo;
import com.alquilatusvehiculos.repositorio.VehiculoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vehiculos")
public class VehiculoControlador {

    @Autowired
    private VehiculoRepositorio vehiculoRepositorio;

    // Listar todos los vehículos (Read)
    @GetMapping
    public String listarVehiculos(Model model) {
        model.addAttribute("vehiculos", vehiculoRepositorio.findAll());
        return "vehiculos/lista";
    }

    // Crear un nuevo vehículo (Create)
    @GetMapping("/nuevo")
    public String mostrarFormularioCrearVehiculo(Model model) {
        model.addAttribute("vehiculo", new Vehiculo());
        return "vehiculos/crear";
    }

    @PostMapping("/nuevo")
    public String crearVehiculo(@ModelAttribute Vehiculo vehiculo) {
        vehiculoRepositorio.save(vehiculo);
        return "redirect:/vehiculos";
    }

    // Mostrar un vehículo (Read)
    @GetMapping("/{id}")
    public String mostrarVehiculo(@PathVariable("id") Long id, Model model) {
        model.addAttribute("vehiculo", vehiculoRepositorio.findById(id).orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado")));
        return "vehiculos/perfil";
    }

    // Editar un vehículo (Update)
    @GetMapping("/{id}/editar")
    public String mostrarFormularioEditarVehiculo(@PathVariable("id") Long id, Model model) {
        model.addAttribute("vehiculo", vehiculoRepositorio.findById(id).orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado")));
        return "vehiculos/editar";
    }

    @PostMapping("/{id}/editar")
    public String editarVehiculo(@PathVariable("id") Long id, @ModelAttribute Vehiculo vehiculo) {
        vehiculo.setId(id);
        vehiculoRepositorio.save(vehiculo);
        return "redirect:/vehiculos";
    }

    // Eliminar un vehículo (Delete)
    @GetMapping("/{id}/eliminar")
    public String eliminarVehiculo(@PathVariable("id") Long id) {
        Vehiculo vehiculo = vehiculoRepositorio.findById(id).orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado"));
        vehiculoRepositorio.delete(vehiculo);
        return "redirect:/vehiculos";
    }
}
