package com.alquilatusvehiculos.servicios;

import com.alquilatusvehiculos.modelo.usuario;
import com.alquilatusvehiculos.Repositorios.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UsuarioDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Spring Security necesita usar roles con el prefijo "ROLE_"
        return new User(
                usuario.getEmail(),
                usuario.getPassword(),
                usuario.getEstado(), // activado
                //Estos true hacen referencia al estado de la cuenta del usuario
                true, // cuenta no expirada
                true, // credenciales activas
                true, // usuario no bloqueado
                Collections.singleton(new SimpleGrantedAuthority(usuario.getRol()))
        );
    }
}
