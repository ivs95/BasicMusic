package com.example.prototipotfg;

import java.util.ArrayList;

public enum Intervalos {

    Unisono("Unisono", 0.0, 1),
    Segunda_menor("Segunda Menor",0.5, 2),
    Segunda_mayor("Segunda Mayor",1, 3),
    Tercera_menor("Tercera Menor",1.5, 4),
    Tercera_mayor("Tercera Mayor",2, 5),
    Cuarta_justa("Cuarta Justa",2.5, 6),
    Cuarta_aumentada("Cuarta Aumentada",3, 7),
    Quinta_justa("Quinta Justa",3.5, 8),
    Sexta_menor("Sexta Menor",4, 9),
    Sexta_mayor("Sexta Mayor",4.5, 10),
    Septima_menor("Septima Menor",5, 11),
    Septima_mayor("Septima Mayor",5.5, 12),
    Octava_justa("Octava Justa",6, 13);

    private String nombre;
    private double diferencia;
    private int numero;

    Intervalos(String nombre, double diferencia, int numero){
        this.nombre=nombre;
        this.diferencia=diferencia;
        this.numero=numero;
    }

    public String getNombre() {
        return nombre;
    }

    public double getDiferencia() {
        return diferencia;
    }

    public int getNumero() {
        return numero;
    }



}
