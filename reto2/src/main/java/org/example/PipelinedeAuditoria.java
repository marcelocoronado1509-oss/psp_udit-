package org.example;
import java.io.IOException;
public class PipelinedeAuditoria {

    public static void main(String[] args) {

        try {


            Process p1 = new ProcessBuilder("ping","-n", "1" , "127.0.0.1").start();
            Process p2 = new ProcessBuilder("ping", "-n", "2", "8.8.8.8" ).start();
            int codigo1 = p1.waitFor();
            int codigo2 = p2.waitFor();
            System.out.println("Código de salida del ping 1: " + codigo1);
            System.out.println("Código de salida del ping 2: " + codigo2);
            if (codigo1 == 0 && codigo2 == 0) {
                System.out.println("Los dos procesos han terminado correctamente.");
                System.out.println("Abriendo el Bloc de Notas...");
                new ProcessBuilder("notepad.exe").start();
            } else {
                System.out.println("Alguno de los procesos ha terminado con error.");
                System.out.println("Abriendo la Calculadora...");
                new ProcessBuilder("calc.exe").start();
            }

        } catch (IOException e) {

            System.out.println("Error al ejecutar un proceso: " + e.getMessage());

        } catch (InterruptedException e) {

            System.out.println("El proceso fue interrumpido.");
            Thread.currentThread().interrupt();
        }
    }
}

