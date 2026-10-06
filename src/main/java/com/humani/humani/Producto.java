package com.humani.humani;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private int cantidad;
    private String categoria;
    private String almacenamiento;
    private double precio;

    public Producto() {
    }

    public Producto(String nombre, int cantidad, String categoria,
                    String almacenamiento, double precio) {

        this.nombre = nombre;
        this.cantidad = cantidad;
        this.categoria = categoria;
        this.almacenamiento = almacenamiento;
        this.precio = precio;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getAlmacenamiento() {
        return almacenamiento;
    }

    public double getPrecio() {
        return precio;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setAlmacenamiento(String almacenamiento) {
        this.almacenamiento = almacenamiento;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void mostrarProducto() {

        System.out.println("------------------------------");
        System.out.println("ID: " + id);
        System.out.println("Nombre: " + nombre);
        System.out.println("Cantidad: " + cantidad);
        System.out.println("Categoría: " + categoria);
        System.out.println("Almacenamiento: " + almacenamiento);
        System.out.println("Precio: $" + precio);
        System.out.println("------------------------------");
    }
}