package com.alquilatusvehiculos.controlador;

import com.alquilatusvehiculos.modelo.Cliente;
import com.alquilatusvehiculos.Repositorios.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/clientes")
public class ClienteControlador {

    @Autowired
    private ClienteRepository clienteRepository;

    // Mostrar todos los clientes
    @GetMapping
    public String getAllClientes(Model model) {
        List<Cliente> clientes = clienteRepository.findAll();
        model.addAttribute("clientes", clientes);
        model.addAttribute("cliente", new Cliente()); // Para el formulario de creación
        return "clientes";  // Nombre de la vista Thymeleaf
    }

    // Crear un nuevo cliente
    @PostMapping
    public String createCliente(@ModelAttribute Cliente cliente) {
        clienteRepository.save(cliente);
        return "redirect:/clientes";  // Redirige a la lista de clientes después de crear uno
    }

    // Editar un cliente
    @GetMapping("/editar/{id}")
    public String editCliente(@PathVariable("id") Long id, Model model) {
        Cliente cliente = clienteRepository.findById(id).orElse(null);
        if (cliente != null) {
            model.addAttribute("cliente", cliente);
        }
        return "editar_cliente"; // Vista para editar cliente
    }

    // Actualizar un cliente
    @PostMapping("/editar")
    public String updateCliente(@ModelAttribute Cliente cliente) {
        clienteRepository.save(cliente);
        return "redirect:/clientes";  // Redirige a la lista de clientes después de actualizar
    }

    // Eliminar un cliente
    @GetMapping("/eliminar/{id}")
    public String deleteCliente(@PathVariable("id") Long id) {
        clienteRepository.deleteById(id);
        return "redirect:/clientes";  // Redirige a la lista de clientes después de eliminar
    }
}
