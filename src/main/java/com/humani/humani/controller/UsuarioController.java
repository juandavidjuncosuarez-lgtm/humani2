package com.humani.humani.controller;

import com.humani.humani.Usuario;
import com.humani.humani.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Mostrar todos los usuarios
    @GetMapping
    public String listarUsuarios(Model model) {

        model.addAttribute(
                "usuarios",
                usuarioRepository.findAll()
        );

        return "usuarios";
    }

    // Mostrar formulario para registrar usuario
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {

        model.addAttribute(
                "usuario",
                new Usuario()
        );

        return "usuario-form";
    }

    // Guardar usuario
    @PostMapping("/guardar")
    public String guardarUsuario(
            @ModelAttribute Usuario usuario) {

        usuarioRepository.save(usuario);

        return "redirect:/usuarios";
    }

    // Eliminar usuario
    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(
            @PathVariable Long id) {

        usuarioRepository.deleteById(id);

        return "redirect:/usuarios";
    }
}