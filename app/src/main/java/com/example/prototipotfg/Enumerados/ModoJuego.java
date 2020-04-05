package com.example.prototipotfg.Enumerados;

import androidx.annotation.NonNull;

public enum ModoJuego {
    Adivinar_Intervalo("Adivinar_Intervalo"),
    Adivinar_Notas("Adivinar_Notas"),
    Adivinar_Acordes("Adivinar_Acordes"),
    Crear_Intervalo("Crear_Intervalo"),
    Halla_Ritmo("Halla_Ritmo"),
    Realiza_Ritmo("Realiza_Ritmo"),
    Crear_Acordes("Crear_Acordes"),
    Imitar_Audio("Imitar_Audio");

    private String nombre;

    ModoJuego(String nombre){
        this.nombre=nombre;
    }

    public String getNombre(){
        return this.nombre;
    }
}
