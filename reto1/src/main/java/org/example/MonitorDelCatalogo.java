package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MonitorDelCatalogo {

    public static void main(String[] args) {

        String[][] contenidos = {
                {"Series", "127.0.0.x"},
                {"Peliculas", "127.0.0.1"},
                {"Documentales", "127.0.0.1"},
                {"Anime", "192.168.999.999"},
                {"Infantil", "127.0.0.1"}
        };
        System.out.println("========================================");
        System.out.println("===UDITFLIX - CATÁLOGO===");
        System.out.println("========================================");
        for (int i = 0; i < contenidos.length; i++) {
            String nombre = contenidos[i][0];
            String direccion = contenidos[i][1];
            System.out.println("[CONTENIDO] " + nombre);

            try {
                ProcessBuilder pb = new ProcessBuilder("ping", "-n", "1", direccion);
                Process proceso = pb.start();
                System.out.println("PID: " + proceso.pid());
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(proceso.getInputStream())
                );
                String linea;
                boolean disponible = false;
                while ((linea = reader.readLine()) != null) {
                    if (linea.contains("TTL")) {
                        disponible = true;
                    }
                }
                proceso.waitFor();
                if (disponible) {
                    System.out.println("ESTADO: ACTIVO");
                } else {
                    System.out.println("ESTADO: CAÍDO");
                }

            } catch (Exception e) {
                System.out.println("ESTADO: CAÍDO");
            }

            System.out.println();
        }
        System.out.println("========================================");
        System.out.println("      COMPROBACIÓN FINALIZADA");
        System.out.println("========================================");
    }
}
