package com.empresa.proyeco.empresa.Controller;

import com.empresa.proyeco.empresa.model.*;
import com.empresa.proyeco.empresa.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public Usuario getUsuario(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el usuario con id: " + id));
    }

    @PostMapping
    public Usuario createUsuario(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario);
    }


    @PutMapping("/{id}")
    public Usuario updateUsuario(@PathVariable Long id, @RequestBody Usuario detallesUsuario) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el usuario con id: " + id));

        usuario.setNombre(detallesUsuario.getNombre());
        usuario.setEmail(detallesUsuario.getEmail());
        usuario.setPassword(detallesUsuario.getPassword());
        usuario.setContacto(detallesUsuario.getContacto());
        usuario.setTipoUsuario(detallesUsuario.getTipoUsuario());

        return usuarioRepository.save(usuario);
    }

    @DeleteMapping("/{id}")
    public String deleteUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el usuario con id: " + id));

        usuarioRepository.delete(usuario);
        return "Usuario eliminado con éxito";
    }


}
