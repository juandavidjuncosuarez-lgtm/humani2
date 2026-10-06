package com.humani.humani;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class Main {

    public static void main(String[] args) {

        // Iniciar Spring Boot
        ConfigurableApplicationContext contexto =
                SpringApplication.run(HumaniApplication.class, args);

        // Obtener ProductoApp desde Spring
        ProductoApp aplicacion =
                contexto.getBean(ProductoApp.class);

        // Iniciar el menú del sistema
        aplicacion.iniciar();

        // Cerrar Spring cuando termine el programa
        contexto.close();
    }
}