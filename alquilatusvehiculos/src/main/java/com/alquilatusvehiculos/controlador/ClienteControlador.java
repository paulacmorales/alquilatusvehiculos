package com.alquilatusvehiculos.controlador;

import com.alquilatusvehiculos.modelo.Cliente;
import com.alquilatusvehiculos.repositorio.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clientes")
public class ClienteControlador {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    // Mostrar todos los clientes (Read)
    @GetMapping
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteRepositorio.findAll());
        return "clientes/lista";
    }

    // Crear un nuevo cliente (Create)
    @GetMapping("/nuevo")
    public String mostrarFormularioCrearCliente(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/crear";
    }

    @PostMapping("/nuevo")
    public String crearCliente(@ModelAttribute Cliente cliente) {
        clienteRepositorio.save(cliente);
        return "redirect:/clientes";
    }

    // Mostrar un cliente (Read)
    @GetMapping("/{id}")
    public String mostrarCliente(@PathVariable("id") Long id, Model model) {
        model.addAttribute("cliente", clienteRepositorio.findById(id).orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado")));
        return "clientes/perfil";
    }

    // Editar un cliente (Update)
    @GetMapping("/{id}/editar")
    public String mostrarFormularioEditarCliente(@PathVariable("id") Long id, Model model) {
        model.addAttribute("cliente", clienteRepositorio.findById(id).orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado")));
        return "clientes/editar";
    }

    @PostMapping("/{id}/editar")
    public String editarCliente(@PathVariable("id") Long id, @ModelAttribute Cliente cliente) {
        cliente.setId(id);
        clienteRepositorio.save(cliente);
        return "redirect:/clientes";
    }

    // Eliminar un cliente (Delete)
    @GetMapping("/{id}/eliminar")
    public String eliminarCliente(@PathVariable("id") Long id) {
        Cliente cliente = clienteRepositorio.findById(id).orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        clienteRepositorio.delete(cliente);
        return "redirect:/clientes";
    }
}
