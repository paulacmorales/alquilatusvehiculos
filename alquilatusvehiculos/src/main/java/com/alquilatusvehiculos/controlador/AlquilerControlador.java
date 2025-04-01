package com.alquilatusvehiculos.controlador;

import com.alquilatusvehiculos.modelo.Alquiler;
import com.alquilatusvehiculos.repositorio.AlquilerRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/alquileres")
public class AlquilerControlador {

    @Autowired
    private AlquilerRepositorio alquilerRepositorio;

    private static final String ALQUILER_ATTRIBUTE = "alquiler";
    private static final String REDIRECT_ALQUILERES = "redirect:/alquileres";
    private static final String ALQUILER_NO_ENCONTRADO = "Alquiler no encontrado";

    // Listar todos los alquileres (Read)
    @GetMapping
    public String listarAlquileres(Model model) {
        model.addAttribute("alquileres", alquilerRepositorio.findAll());
        return "alquiler/lista";
    }

    // Crear un nuevo alquiler (Create)
    @GetMapping("/nuevo")
    public String mostrarFormularioCrearAlquiler(Model model) {
        model.addAttribute(ALQUILER_ATTRIBUTE, new Alquiler());
        return "alquiler/crear";
    }

    @PostMapping("/nuevo")
    public String crearAlquiler(@ModelAttribute Alquiler alquiler) {
        alquilerRepositorio.save(alquiler);
        return REDIRECT_ALQUILERES;
    }

    // Mostrar detalles de un alquiler (Read)
    @GetMapping("/{id}")
    public String mostrarAlquiler(@PathVariable("id") Long id, Model model) {
        model.addAttribute(ALQUILER_ATTRIBUTE, alquilerRepositorio.findById(id)
            .orElseThrow(() -> new IllegalArgumentException(ALQUILER_NO_ENCONTRADO)));
        return "alquiler/detalles";
    }

    // Editar alquiler (Update)
    @GetMapping("/{id}/editar")
    public String mostrarFormularioEditarAlquiler(@PathVariable("id") Long id, Model model) {
        model.addAttribute(ALQUILER_ATTRIBUTE, alquilerRepositorio.findById(id)
            .orElseThrow(() -> new IllegalArgumentException(ALQUILER_NO_ENCONTRADO)));
        return "alquiler/editar";
    }

    @PostMapping("/{id}/editar")
    public String editarAlquiler(@PathVariable("id") Long id, @ModelAttribute Alquiler alquiler) {
        alquiler.setId(id);
        alquilerRepositorio.save(alquiler);
        return REDIRECT_ALQUILERES;
    }

    // Eliminar alquiler (Delete)
    @GetMapping("/{id}/eliminar")
    public String eliminarAlquiler(@PathVariable("id") Long id) {
        Alquiler alquiler = alquilerRepositorio.findById(id)
            .orElseThrow(() -> new IllegalArgumentException(ALQUILER_NO_ENCONTRADO));
        alquilerRepositorio.delete(alquiler);
        return REDIRECT_ALQUILERES;
    }
}
