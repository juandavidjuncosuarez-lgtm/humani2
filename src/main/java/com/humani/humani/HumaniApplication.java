package com.humani.humani;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class HumaniApplication {

    public static void main(String[] args) {
        SpringApplication.run(HumaniApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void abrirNavegador() {

        String url = "http://localhost:8080/";

        try {

            new ProcessBuilder(
                    "cmd",
                    "/c",
                    "start",
                    "",
                    url
            ).start();

            System.out.println();
            System.out.println("==========================================");
            System.out.println("       HUMANI INICIADO CORRECTAMENTE");
            System.out.println("==========================================");
            System.out.println("Servidor: http://localhost:8080/");
            System.out.println("Abriendo navegador predeterminado...");
            System.out.println("==========================================");
            System.out.println();

        } catch (Exception e) {

            System.out.println();
            System.out.println("NO SE PUDO ABRIR EL NAVEGADOR AUTOMATICAMENTE.");
            System.out.println("Puedes entrar manualmente a:");
            System.out.println(url);
            System.out.println();

            e.printStackTrace();
        }
    }
}