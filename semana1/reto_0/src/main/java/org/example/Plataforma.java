package org.example;

import java.util.ArrayList;

public class Plataforma {
    private ArrayList<Episodio> catalogo = new ArrayList<>();

    public Plataforma(String titulo, int duracion){
    this.catalogo = new ArrayList<>();
}
public void agregarepisodio(Episodio e){
        catalogo.add(e);

}
public void procesarCatalogo(){
        for (Episodio e: catalogo){
            e.procesar();
        }
}
}

