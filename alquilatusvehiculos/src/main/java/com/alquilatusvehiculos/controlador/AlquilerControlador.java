package com.alquilatusvehiculos.controlador;

import com.alquilatusvehiculos.modelo.Alquiler;
import com.alquilatusvehiculos.modelo.Cliente;
import com.alquilatusvehiculos.modelo.Vehiculo;
import com.alquilatusvehiculos.Repositorios.AlquilerRepository;
import com.alquilatusvehiculos.Repositorios.ClienteRepository;
import com.alquilatusvehiculos.Repositorios.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
public class AlquilerControlador {

    @Autowired
    private AlquilerRepository alquilerRepositorio;

    @Autowired
    private ClienteRepository clienteRepositorio;

    @Autowired
    private VehiculoRepository vehiculoRepositorio;

    // Mostrar la lista de alquileres
    @GetMapping("/alquileres")
    public String listarAlquileres(Model model) {
        List<Alquiler> alquileres = alquilerRepositorio.findAll();
        model.addAttribute("alquileres", alquileres);
        return "alquileres";  // Nombre del archivo HTML
    }

    // Formulario para añadir un nuevo alquiler
    @GetMapping("/alquileres/nuevo")
    public String mostrarFormularioNuevoAlquiler(Model model) {
        List<Cliente> clientes = clienteRepositorio.findAll();
        List<Vehiculo> vehiculos = vehiculoRepositorio.findAll();
        model.addAttribute("clientes", clientes);
        model.addAttribute("vehiculos", vehiculos);
        model.addAttribute("alquiler", new Alquiler());
        return "formulario_alquiler";  // Nombre del archivo HTML para el formulario
    }

    // Crear un nuevo alquiler
    @PostMapping("/alquileres/nuevo")
    public String crearAlquiler(Alquiler alquiler) {
        // Calcular el total del alquiler
        double precioTotal = calcularPrecioTotal(alquiler.getFechaInicio(), alquiler.getFechaFin(), alquiler.getVehiculo());
        alquiler.setPrecioTotal(precioTotal);

        // Guardar el alquiler con el total calculado
        alquilerRepositorio.save(alquiler);
        return "redirect:/alquileres";  // Redirige a la lista de alquileres
    }

    // Editar un alquiler existente
    @GetMapping("/alquileres/editar/{id}")
    public String mostrarFormularioEditarAlquiler(@PathVariable("id") Long id, Model model) {
        Alquiler alquiler = alquilerRepositorio.findById(id).orElse(null);
        if (alquiler != null) {
            model.addAttribute("alquiler", alquiler);
            model.addAttribute("clientes", clienteRepositorio.findAll());
            model.addAttribute("vehiculos", vehiculoRepositorio.findAll());
            return "editar_alquiler";
        }
        return "redirect:/alquileres";
    }

    // Actualizar un alquiler existente
    @PostMapping("/alquileres/editar")
    public String actualizarAlquiler(@ModelAttribute Alquiler alquiler) {
        double precioTotal = calcularPrecioTotal(alquiler.getFechaInicio(), alquiler.getFechaFin(), alquiler.getVehiculo());
        alquiler.setPrecioTotal(precioTotal);
        alquilerRepositorio.save(alquiler);
        return "redirect:/alquileres";
    }

    // Método para calcular el precio total del alquiler con validaciones
    private double calcularPrecioTotal(LocalDate fechaInicio, LocalDate fechaFin, Vehiculo vehiculo) {
        if (fechaInicio == null || fechaFin == null || fechaInicio.isAfter(fechaFin)) {
            throw new IllegalArgumentException("Fechas inválidas");
        }
        long dias = ChronoUnit.DAYS.between(fechaInicio, fechaFin);
        if (dias <= 0) {
            throw new IllegalArgumentException("Duración mínima: un día");
        }
        return dias * vehiculo.getPrecioPorDia();
    }

    @GetMapping("/alquileres/eliminar/{id}")
    public String eliminarAlquiler(@PathVariable("id") Long id) {
        alquilerRepositorio.deleteById(id);
        return "redirect:/alquileres";
    }


}
