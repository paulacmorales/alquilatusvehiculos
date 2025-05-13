package com.alquilatusvehiculos.servicios;

import com.alquilatusvehiculos.repositorio.ClienteRepository;
import com.alquilatusvehiculos.modelo.Cliente;
import com.alquilatusvehiculos.modelo.Usuario;
import com.alquilatusvehiculos.repositorio.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean emailExiste(String email) {
        return usuarioRepository.findByEmail(email).isPresent();
    }

    public Usuario registrar(Usuario nuevoUsuario) {
        nuevoUsuario.setPassword(passwordEncoder.encode(nuevoUsuario.getPassword()));
        Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);

        // Usamos el email como username en Cliente
        if (clienteRepository.findByUsername(usuarioGuardado.getEmail()) == null) {
            Cliente cliente = new Cliente();
            cliente.setUsername(usuarioGuardado.getEmail());
            cliente.setNombre(usuarioGuardado.getNombre());
            cliente.setEmail(usuarioGuardado.getEmail());
            clienteRepository.save(cliente);
        }

        return usuarioGuardado;
    }
}
