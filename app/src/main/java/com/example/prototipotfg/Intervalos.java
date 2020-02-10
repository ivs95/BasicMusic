package com.example.prototipotfg;

import java.util.ArrayList;

public enum Intervalos {

    Unisono("Unisono", 0, 0),
    Segunda_menor("Segunda Menor",1, 1),
    Segunda_mayor("Segunda Mayor",2, 2),
    Tercera_menor("Tercera Menor",3, 3),
    Tercera_mayor("Tercera Mayor",4, 4),
    Cuarta_justa("Cuarta Justa",5, 5),
    Cuarta_aumentada("Cuarta Aumentada",6, 6),
    Quinta_justa("Quinta Justa",7, 7),
    Sexta_menor("Sexta Menor",8, 8),
    Sexta_mayor("Sexta Mayor",9, 9),
    Septima_menor("Septima Menor",10, 10),
    Septima_mayor("Septima Mayor",11, 11),
    Octava_justa("Octava Justa",12, 12);

    private String nombre;
    private int diferencia;
    private int numero;

    Intervalos(String nombre, int diferencia, int numero){
        this.nombre=nombre;
        this.diferencia=diferencia;
        this.numero=numero;
    }

    public String getNombre() {
        return nombre;
    }

    public int getDiferencia() {
        return diferencia;
    }

    public int getNumero() {
        return numero;
    }

}
