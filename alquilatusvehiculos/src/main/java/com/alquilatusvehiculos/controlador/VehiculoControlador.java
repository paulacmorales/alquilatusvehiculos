package com.alquilatusvehiculos.controlador;

import com.alquilatusvehiculos.modelo.Alquiler;
import com.alquilatusvehiculos.modelo.Vehiculo;
import com.alquilatusvehiculos.repositorio.AlquilerRepositorio;  // Importar repositorio de alquiler
import com.alquilatusvehiculos.repositorio.VehiculoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class VehiculoControlador {

    @Autowired
    private VehiculoRepositorio vehiculoRepositorio;

    @Autowired
    private AlquilerRepositorio alquilerRepositorio;  // Inyectar repositorio de alquiler

    // Mostrar la lista de vehículos
    @GetMapping("/vehiculos")
    public String listarVehiculos(Model model) {
        model.addAttribute("vehiculos", vehiculoRepositorio.findAll());
        return "vehiculo/lista";  // Nombre de la vista Thymeleaf
    }

    // Mostrar el formulario de alquiler para un vehículo
    @GetMapping("/vehiculos/{id}/alquilar")
    public String mostrarFormularioAlquiler(@PathVariable("id") Long id, Model model) {
        Vehiculo vehiculo = vehiculoRepositorio.findById(id).orElseThrow();
        model.addAttribute("vehiculo", vehiculo);
        return "vehiculo/alquiler";  // Nombre de la vista Thymeleaf
    }

    // Procesar el alquiler de un vehículo
    @PostMapping("/vehiculos/{id}/alquilar")
    public String procesarAlquiler(@PathVariable("id") Long id, Alquiler alquiler) {
        Vehiculo vehiculo = vehiculoRepositorio.findById(id).orElseThrow();
        alquiler.setVehiculo(vehiculo);
        alquilerRepositorio.save(alquiler);  // Guardar el alquiler en el repositorio
        return "redirect:/vehiculos";  // Redirige de vuelta a la lista de vehículos
    }
}
