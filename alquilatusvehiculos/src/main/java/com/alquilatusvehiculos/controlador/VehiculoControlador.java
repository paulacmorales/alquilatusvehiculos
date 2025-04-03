package com.alquilatusvehiculos.controlador;

import com.alquilatusvehiculos.modelo.Vehiculo;
import com.alquilatusvehiculos.Repositorios.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/vehiculos")
public class VehiculoControlador {
    @Autowired
    private VehiculoRepository vehiculoRepository;

    @GetMapping
    public String getAllVehiculos(Model model) {
        List<Vehiculo> vehiculos = vehiculoRepository.findAll();
        model.addAttribute("vehiculos", vehiculos);
        return "vehiculos";
    }

    @PostMapping
    public String createVehiculo(@ModelAttribute Vehiculo vehiculo) {
        vehiculoRepository.save(vehiculo);
        return "redirect:/vehiculos";
    }

    @GetMapping("/editar/{id}")
    public String editarVehiculo(@PathVariable Long id, Model model) {
        Vehiculo vehiculo = vehiculoRepository.findById(id).orElse(null);
        model.addAttribute("vehiculo", vehiculo);
        return "editarVehiculo";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoVehiculo(Model model) {
        model.addAttribute("vehiculo", new Vehiculo());  // Esto crea un objeto Vehiculo vacío
        return "formulario_vehiculo";  // Nombre del archivo HTML del formulario
    }


    @PostMapping("/actualizar/{id}")
    public String actualizarVehiculo(@PathVariable Long id, @ModelAttribute Vehiculo vehiculo) {
        vehiculo.setId(id);
        vehiculoRepository.save(vehiculo);
        return "redirect:/vehiculos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarVehiculo(@PathVariable Long id) {
        vehiculoRepository.deleteById(id);
        return "redirect:/vehiculos";
    }
}
