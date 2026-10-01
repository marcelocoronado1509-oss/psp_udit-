package org.example;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PildoraMonitor {
    public static void main(String[] args){
        System.out.println("===MONITOR UDITIFLIX===");
        System.out.println("comprobando servicio");
        try {
            ProcessBuilder pb = new ProcessBuilder("ping", "-n","1","127.0.0.1");
            pb.redirectErrorStream(true);
            Process proceso = pb.start();
            System.out.println("PID" + proceso.pid());
            BufferedReader lector = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
            String linea;
            while ((linea= lector.readLine()) !=null){
                System.out.println(linea);}
            int codigo = proceso.waitFor();
            if (codigo==0){
                System.out.println("ESTADO : SERVICIO ACTIVO");
            }else {
                System.out.println("ESTADO : SERVICIO CON ERROR");
            }
        }catch (IOException e){
            System.out.println("no se pudo lanzar el proceso");
        }catch (InterruptedException e){
            System.out.println("la ejecucion fue interrumpida");
        }
    }
}
