package com.example.prototipotfg.Enumerados;

import androidx.annotation.NonNull;

public enum ModoJuego {
    Adivinar_Intervalo("Adivinar_Intervalo", 6),
    Adivinar_Notas("Adivinar_Notas", 10),
    Adivinar_Acordes("Adivinar_Acordes", 6),
    Crear_Intervalo("Crear_Intervalo", 8),
    Halla_Ritmo("Halla_Ritmo", 8),
    Realiza_Ritmo("Realiza_Ritmo", 8),
    Crear_Acordes("Crear_Acordes", 6),
    Imitar_Audio("Imitar_Audio", 3);

    private String nombre;
    private int max_level;

    ModoJuego(String nombre, int max_level){
        this.nombre=nombre;
        this.max_level = max_level;
    }

    public String getNombre(){
        return this.nombre;
    }
    public int getMax_level(){ return this.max_level;}
}
