package com.humani.humani.controller;

import com.humani.humani.Producto;
import com.humani.humani.ProductoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoRepository productoRepository;

    public ProductoController(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // Mostrar todos los productos
    @GetMapping
    public String listarProductos(Model model) {

        model.addAttribute(
                "productos",
                productoRepository.findAll()
        );

        return "productos";
    }

    // Mostrar formulario para registrar producto
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {

        model.addAttribute(
                "producto",
                new Producto()
        );

        return "producto-form";
    }

    // Guardar producto
    @PostMapping("/guardar")
    public String guardarProducto(
            @ModelAttribute Producto producto) {

        productoRepository.save(producto);

        return "redirect:/productos";
    }

    // Mostrar formulario de modificación
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(
            @PathVariable Long id,
            Model model) {

        Producto producto =
                productoRepository.findById(id)
                        .orElse(null);

        if (producto == null) {
            return "redirect:/productos";
        }

        model.addAttribute(
                "producto",
                producto
        );

        return "producto-form";
    }

    // Eliminar producto
    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(
            @PathVariable Long id) {

        productoRepository.deleteById(id);

        return "redirect:/productos";
    }
}