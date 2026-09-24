package org.example;

import java.io.IOException;

public class LanzadorProcesos {
    public static void main(String[] args){
        System.out.println("Solicitando al SO la creacion de un proceso nativo ...");
         ProcessBuilder pb = new ProcessBuilder("notepad.exe");
         try {
             Process proceso = pb.start();
             System.out.println("proceso en ejecucion. PID"+ proceso.pid());
             int salida = proceso.waitFor();
             System.out.println("El proceso externo finalizo con codigo"+ salida);

         }catch (IOException e){
             System.out.println("error: el sistema operativo no encuentra el programa.");

         }catch (InterruptedException e){
             System.out.println("error: el proceso fue interrumpido bruscamente");

         }



    }
}
