package com.alquilatusvehiculos.controlador;

import com.alquilatusvehiculos.modelo.Alquiler;
import com.alquilatusvehiculos.modelo.Cliente;
import com.alquilatusvehiculos.modelo.Vehiculo;
import com.alquilatusvehiculos.Repositorios.AlquilerRepository;
import com.alquilatusvehiculos.Repositorios.ClienteRepository;
import com.alquilatusvehiculos.Repositorios.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.alquilatusvehiculos.modelo.usuario;

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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            List<Alquiler> alquileres = alquilerRepositorio.findAll();
            model.addAttribute("alquileres", alquileres);
        } else {
            String username = auth.getName();
            Cliente cliente = clienteRepositorio.findByUsername(username);
            List<Alquiler> alquileres = alquilerRepositorio.findByCliente(cliente);
            model.addAttribute("alquileres", alquileres);
        }

        return "alquileres";
    }

    // Formulario para añadir un nuevo alquiler
    @GetMapping("/alquileres/nuevo")
    public String mostrarFormularioNuevoAlquiler(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        List<Cliente> clientes;

        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            clientes = clienteRepositorio.findAll();
        } else {
            Cliente cliente = clienteRepositorio.findByUsername(username);
            clientes = List.of(cliente);
        }

        List<Vehiculo> vehiculos = vehiculoRepositorio.findAll();
        model.addAttribute("clientes", clientes);
        model.addAttribute("vehiculos", vehiculos);
        model.addAttribute("alquiler", new Alquiler());

        return "formulario_alquiler";
    }

    // Crear un nuevo alquiler
    @PostMapping("/alquileres/nuevo")
    public String crearAlquiler(@ModelAttribute Alquiler alquiler) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        if (auth.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            Cliente cliente = clienteRepositorio.findByUsername(username);
            alquiler.setCliente(cliente);
        }

        double precioTotal = calcularPrecioTotal(alquiler.getFechaInicio(), alquiler.getFechaFin(), alquiler.getVehiculo());
        alquiler.setPrecioTotal(precioTotal);

        alquilerRepositorio.save(alquiler);
        return "redirect:/alquileres";
    }

    // Editar un alquiler existente
    @GetMapping("/alquileres/editar/{id}")
    public String mostrarFormularioEditarAlquiler(@PathVariable("id") Long id, Model model) {
        Alquiler alquiler = alquilerRepositorio.findById(id).orElse(null);

        if (alquiler != null) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();

            List<Cliente> clientes;

            if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                clientes = clienteRepositorio.findAll(); // 👑 Admin: todos los clientes
            } else {
                Cliente cliente = clienteRepositorio.findByUsername(username); // 🧍 Usuario: solo su cliente
                clientes = List.of(cliente); // Creamos una lista con un solo cliente
            }

            model.addAttribute("alquiler", alquiler);
            model.addAttribute("clientes", clientes);
            model.addAttribute("vehiculos", vehiculoRepositorio.findAll());

            return "editar_alquiler";
        }

        return "redirect:/alquileres";
    }

    // Actualizar un alquiler existente
    @PostMapping("/alquileres/editar")
    public String actualizarAlquiler(@ModelAttribute Alquiler alquiler) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        if (auth.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            Cliente cliente = clienteRepositorio.findByUsername(username);
            alquiler.setCliente(cliente); // Mantiene al cliente original del usuario logueado
        }

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
