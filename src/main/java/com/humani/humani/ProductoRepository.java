package com.humani.humani;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    Producto findByNombreIgnoreCase(String nombre);

    boolean existsByNombreIgnoreCase(String nombre);
}