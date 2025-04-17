package com.alquilatusvehiculos.controlador;

import com.alquilatusvehiculos.modelo.usuario;
import com.alquilatusvehiculos.servicios.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor

public class UsuarioControlador {

    private final UsuarioService usuarioService;

    @GetMapping("/register")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new usuario());
        return "registro";
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }
    @GetMapping("/admin")
    public String vistaAdmin() {
        return "admin_dashboard"; // plantilla para admin
    }

    @GetMapping("/user")
    public String vistaUser() {
        return "user_dashboard"; // plantilla para user
    }



    @PostMapping("/register")
    public String procesarRegistro(@ModelAttribute("usuario") usuario usuario,
                                   @RequestParam(name = "codigoRol", required = false) String codigoRol,
                                   Model model) {
        if (usuarioService.emailExiste(usuario.getEmail())) {
            model.addAttribute("error", "El email ya está en uso");
            return "registro";
        }

        // Lógica para asignar el rol
        if ("admin123".equalsIgnoreCase(codigoRol != null ? codigoRol.trim() : "")) {
            usuario.setRol("ROLE_ADMIN");
        } else {
            usuario.setRol("ROLE_USER");
        }

        usuario.setEstado(true);
        usuarioService.registrar(usuario);
        model.addAttribute("registroOk", true);
        return "redirect:/login";
    }
}