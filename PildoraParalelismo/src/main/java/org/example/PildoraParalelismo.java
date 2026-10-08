package org.example;

import java.io.IOException;

public class PildoraParalelismo {
    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("🚀 PILDORA TÉCNICA: SECUENCIAL VS PARALELO");
        System.out.println("==========================================\n");


        try {
            System.out.println(" INICIANDO EJECUIÓN SECUENCIAL......");
            long inicioSecuencial = System.currentTimeMillis();

            System.out.println("      -> Lanzando proceso 1 ( y esperando que muera....)");
            Process p1 = new ProcessBuilder("ping", "-n", "2", "127.0.0.1").start();
            p1.waitFor();
            System.out.println("      -> Lanzando proceso 2 ( y esperando que muera....)");
            Process p2 = new ProcessBuilder("ping", "-n", "2", "8.8.8.8").start();
            p2.waitFor();
            long finSecuencial = System.currentTimeMillis();
            System.out.println("⏱️ TIEMPO TOTAL SECUENCIAL :" + (finSecuencial - inicioSecuencial) + "ms\n");
            // El camino paralelo
            System.out.println("=================================");
            System.out.println("INICIANDO EJECUCION PARALELA .....");

            // reseteo
            long inicioParalelo = System.currentTimeMillis();

            // paso A: Apretamos todos los gatillos
            System.out.println("         -> Lanzando proceso 3(¡no esperamos!");
            Process p3 = new ProcessBuilder("ping", "-n", "2", "127.0.0.1").start();
            System.out.println("          ->lanzando proceso 4 (¡no esperamos!");
            Process p4 = new ProcessBuilder("ping", "-n", "2", "127.0.0.1").start();


            System.out.println("         -> Bloqueando java para recoger resultados");
            p3.waitFor();
            p4.waitFor();

            long finParalelo = System.currentTimeMillis();
            System.out.println("⏱️ TIEMPO TOTAL PARALELO : " + (finParalelo - inicioParalelo) + "ms\n");



        } catch (IOException e){
            System.out.println("Error no se puede lanzar el proceso");
        } catch (InterruptedException e){
            System.out.println("Error: la espera fue interrrumpida de forma inespereda");
        }

    }
}