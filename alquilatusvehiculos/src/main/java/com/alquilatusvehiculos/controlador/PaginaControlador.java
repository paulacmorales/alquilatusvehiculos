package com.alquilatusvehiculos.controlador;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaginaControlador {

    @GetMapping("/")
    public String redirigirSegunRol(Authentication auth) {
        if (auth != null && auth.isAuthenticated()) {
            if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                return "redirect:/admin";
            } else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {
                return "redirect:/user";
            }
        }
        return "redirect:/login";
    }
}
