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

    // Eliminar un alquiler por ID
    @GetMapping("/alquileres/eliminar/{id}")
    public String eliminarAlquiler(@PathVariable("id") Long id) {
        // Verificar si el alquiler existe
        Alquiler alquiler = alquilerRepositorio.findById(id).orElse(null);
        if (alquiler != null) {
            alquilerRepositorio.delete(alquiler);  // Eliminar el alquiler
        }
        return "redirect:/alquileres";  // Redirige a la lista de alquileres después de eliminar
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

    // Formulario para editar un alquiler
    @GetMapping("/alquileres/editar/{id}")
    public String mostrarFormularioEditarAlquiler(@PathVariable("id") Long id, Model model) {
        Alquiler alquiler = alquilerRepositorio.findById(id).orElse(null);
        if (alquiler != null) {
            List<Cliente> clientes = clienteRepositorio.findAll();
            List<Vehiculo> vehiculos = vehiculoRepositorio.findAll();
            model.addAttribute("alquiler", alquiler);
            model.addAttribute("clientes", clientes);
            model.addAttribute("vehiculos", vehiculos);
            return "editar_alquiler";  // Nombre del archivo HTML para el formulario de edición
        }
        return "redirect:/alquileres";  // Si no se encuentra el alquiler, redirige a la lista de alquileres
    }

    // Actualizar un alquiler existente
    @PostMapping("/alquileres/editar/{id}")
    public String actualizarAlquiler(@PathVariable("id") Long id, Alquiler alquiler) {
        // Verificar si el alquiler existe
        Alquiler alquilerExistente = alquilerRepositorio.findById(id).orElse(null);
        if (alquilerExistente != null) {
            // Actualizar los campos del alquiler existente con los nuevos valores
            alquilerExistente.setCliente(alquiler.getCliente());
            alquilerExistente.setVehiculo(alquiler.getVehiculo());
            alquilerExistente.setFechaInicio(alquiler.getFechaInicio());
            alquilerExistente.setFechaFin(alquiler.getFechaFin());

            // Calcular el total del alquiler
            double precioTotal = calcularPrecioTotal(alquiler.getFechaInicio(), alquiler.getFechaFin(), alquiler.getVehiculo());
            alquilerExistente.setPrecioTotal(precioTotal);

            // Guardar los cambios en el alquiler
            alquilerRepositorio.save(alquilerExistente);
        }
        return "redirect:/alquileres";  // Redirige a la lista de alquileres después de guardar
    }

    // Método para calcular el precio total del alquiler con validaciones
    private double calcularPrecioTotal(LocalDate fechaInicio, LocalDate fechaFin, Vehiculo vehiculo) {
        if (fechaInicio == null || fechaFin == null || fechaInicio.isAfter(fechaFin)) {
            throw new IllegalArgumentException("Las fechas son inválidas. La fecha de inicio no puede ser posterior a la fecha de fin.");
        }

        long dias = ChronoUnit.DAYS.between(fechaInicio, fechaFin);
        if (dias <= 0) {
            throw new IllegalArgumentException("El alquiler debe durar al menos un día.");
        }

        return dias * vehiculo.getPrecioPorDia();
    }


}
