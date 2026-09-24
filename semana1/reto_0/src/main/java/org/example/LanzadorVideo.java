package org.example;

import java.io.IOException;

public class LanzadorVideo {
    public static void main(String[]args){
        ProcessBuilder pb = new ProcessBuilder("cmd", "/c" , "start", "","https://www.youtube.com/watch?v=V9ABpon3liA");
        try {
            Process proceso= pb.start();
            System.out.println("video lanzadao ");

        }catch (IOException e){
            System.out.println("Error");

        }
    }
}
