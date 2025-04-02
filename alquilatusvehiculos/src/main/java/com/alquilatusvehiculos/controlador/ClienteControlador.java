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

    private static final String CLIENTE_ATTRIBUTE = "cliente";
    private static final String REDIRECT_CLIENTES = "redirect:/clientes";
    private static final String CLIENTE_NO_ENCONTRADO = "Cliente no encontrado";

    // Mostrar todos los clientes (Read)
    @GetMapping
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteRepositorio.findAll());
        return "clientes/lista";
    }

    // Crear un nuevo cliente (Create)
    @GetMapping("/nuevo")
    public String mostrarFormularioCrearCliente(Model model) {
        model.addAttribute(CLIENTE_ATTRIBUTE, new Cliente());
        return "clientes/crear";
    }

    @PostMapping("/nuevo")
    public String crearCliente(@ModelAttribute Cliente cliente) {
        clienteRepositorio.save(cliente);
        return REDIRECT_CLIENTES;
    }

    // Mostrar un cliente (Read)
    @GetMapping("/{id}")
    public String mostrarCliente(@PathVariable("id") Long id, Model model) {
        model.addAttribute(CLIENTE_ATTRIBUTE, clienteRepositorio.findById(id).orElseThrow(() -> new IllegalArgumentException(CLIENTE_NO_ENCONTRADO)));
        return "clientes/perfil";
    }

    // Editar un cliente (Update)
    @GetMapping("/{id}/editar")
    public String mostrarFormularioEditarCliente(@PathVariable("id") Long id, Model model) {
        model.addAttribute(CLIENTE_ATTRIBUTE, clienteRepositorio.findById(id).orElseThrow(() -> new IllegalArgumentException(CLIENTE_NO_ENCONTRADO)));
        return "clientes/editar";
    }

    @PostMapping("/{id}/editar")
    public String editarCliente(@PathVariable("id") Long id, @ModelAttribute Cliente cliente) {
        cliente.setIdCliente(id);
        clienteRepositorio.save(cliente);
        return REDIRECT_CLIENTES;
    }

    // Eliminar un cliente (Delete)
    @GetMapping("/{id}/eliminar")
    public String eliminarCliente(@PathVariable("id") Long id) {
        Cliente cliente = clienteRepositorio.findById(id).orElseThrow(() -> new IllegalArgumentException(CLIENTE_NO_ENCONTRADO));
        clienteRepositorio.delete(cliente);
        return REDIRECT_CLIENTES;
    }
}
