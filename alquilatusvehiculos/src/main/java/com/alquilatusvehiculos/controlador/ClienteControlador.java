package com.alquilatusvehiculos.controlador;

import com.alquilatusvehiculos.modelo.Cliente;
import com.alquilatusvehiculos.repositorio.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ClienteControlador {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    // Mostrar perfil del cliente
    @GetMapping("/cliente/perfil")
    public String mostrarPerfilCliente(Model model) {
        // Suponiendo que tenemos un cliente por ID
        Cliente cliente = clienteRepositorio.findById(1L).orElseThrow();
        model.addAttribute("cliente", cliente);
        return "cliente/perfil";  // Nombre de la vista Thymeleaf
    }

    // Editar perfil del cliente
    @PostMapping("/cliente/editar")
    public String editarPerfilCliente(Cliente cliente) {
        // Guardar cambios del cliente
        clienteRepositorio.save(cliente);
        return "redirect:/cliente/perfil";  // Redirige al perfil del cliente
    }
}
