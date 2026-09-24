package org.example;

import java.util.ArrayList;
public class Main {
   public static void main(String[] args) {

       System.out.println("--- INICIANDO UDITVERSUM---");
       // 1 MALA PRACTICA: La logica de negocio esta tirada al Main.
       // los diamantes <> obligan a que la liga SOLO acepte episodios
       ArrayList<Episodio> catalogo = new ArrayList<>();

       catalogo.add(new Episodio("Diseño 3D - Intro", 45));
       catalogo.add(new Episodio("animacion", 60));
       catalogo.add(new Episodio("texturas", 50));

       long inicio = System.currentTimeMillis();

       for(Episodio ep : catalogo){
       ep.procesar();

        }
       long fin = System.currentTimeMillis();
        long totalSegundos= (fin - inicio) / 1000;
        System.out.println("tiempo total :"+ totalSegundos + "segundos de bloqueo.");
    }
}

