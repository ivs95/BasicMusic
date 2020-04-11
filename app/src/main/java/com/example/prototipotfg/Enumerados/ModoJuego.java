package com.example.prototipotfg.Enumerados;

import android.app.Service;
import android.content.Context;

import androidx.annotation.NonNull;

import com.example.prototipotfg.R;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public enum ModoJuego {
    Adivinar_Intervalo("Adivinar_Intervalo", 6, new HashMap<Integer, String>()),
    Adivinar_Notas("Adivinar_Notas", 10, new HashMap<Integer, String>()),
    Adivinar_Acordes("Adivinar_Acordes", 6, new HashMap<Integer, String>()),
    Crear_Intervalo("Crear_Intervalo", 8, new HashMap<Integer, String>()),
    Halla_Ritmo("Halla_Ritmo", 8, new HashMap<Integer, String>()),
    Realiza_Ritmo("Realiza_Ritmo", 8, new HashMap<Integer, String>()),
    Crear_Acordes("Crear_Acordes", 6, new HashMap<Integer, String>()),
    Imitar_Audio("Imitar_Audio", 3, new HashMap<Integer, String>());

    private String nombre;
    private int max_level;
    private HashMap<Integer, String> texto;


    ModoJuego(String nombre, int max_level, HashMap<Integer, String> texto){
        this.nombre=nombre;
        this.max_level = max_level;
        this.texto = texto;
    }

    public String getNombre(){
        return this.nombre;
    }

    public String getTextDadoNivel(int nivel){
        return texto.get(nivel);
    }

    public int getMax_level(){ return this.max_level;}

    public void rellena_cambios(String modoJuego, Context context){
        switch(modoJuego){
            case "Adivinar_Notas": {
                this.texto.put(2, context.getString(R.string.cambiosNv2AdNotas));
                this.texto.put(3, context.getString(R.string.cambiosNv3AdNotas));
                this.texto.put(4, context.getString(R.string.cambiosNv4AdNotas));
                this.texto.put(5, context.getString(R.string.cambiosNv5AdNotas));
                this.texto.put(6, context.getString(R.string.cambiosNv6AdNotas));
                this.texto.put(7, context.getString(R.string.cambiosNv7AdNotas));
                this.texto.put(8, context.getString(R.string.cambiosNv8AdNotas));
                this.texto.put(9, context.getString(R.string.cambiosNv9AdNotas));
                this.texto.put(10, context.getString(R.string.cambiosNv10AdNotas));
                break;
            }
            case "Adivinar_Intervalos":{
                this.texto.put(2, context.getString(R.string.cambiosNv2AdIntervalos));
                this.texto.put(3, context.getString(R.string.cambiosNv3AdIntervalos));
                this.texto.put(4, context.getString(R.string.cambiosNv4AdIntervalos));
                this.texto.put(5, context.getString(R.string.cambiosNv5AdIntervalos));
                this.texto.put(6, context.getString(R.string.cambiosNv6AdIntervalos)); break;
            }

            case "Crear_Intervalos":{
                this.texto.put(2, context.getString(R.string.cambiosNv2CrearIntervalos));
                this.texto.put(3, context.getString(R.string.cambiosNv3CrearIntervalos));
                this.texto.put(4, context.getString(R.string.cambiosNv4CrearIntervalos));
                this.texto.put(5, context.getString(R.string.cambiosNv5CrearIntervalos));
                this.texto.put(6, context.getString(R.string.cambiosNv6CrearIntervalos));
                this.texto.put(7, context.getString(R.string.cambiosNv7CrearIntervalos));
                this.texto.put(8, context.getString(R.string.cambiosNv8CrearIntervalos));break;

            }
            case "Adivinar_Acordes":{
                this.texto.put(2, context.getString(R.string.cambiosNv2AdAcordes));
                this.texto.put(3, context.getString(R.string.cambiosNv3AdAcordes));
                this.texto.put(4, context.getString(R.string.cambiosNv4AdAcordes));
                this.texto.put(5, context.getString(R.string.cambiosNv5AdAcordes));
                this.texto.put(6, context.getString(R.string.cambiosNv6AdAcordes)); break;

            }
            case "Crear_Acordes":{
                this.texto.put(2, context.getString(R.string.cambiosNv2CrearAcordes));
                this.texto.put(3, context.getString(R.string.cambiosNv3CrearAcordes));
                this.texto.put(4, context.getString(R.string.cambiosNv4CrearAcordes));
                this.texto.put(5, context.getString(R.string.cambiosNv5CrearAcordes));
                this.texto.put(6, context.getString(R.string.cambiosNv6CrearAcordes)); break;

            }
            case "Halla_Ritmos":{
                this.texto.put(2, context.getString(R.string.cambiosNv2HallaRitmos));
                this.texto.put(3, context.getString(R.string.cambiosNv3HallaRitmos));
                this.texto.put(4, context.getString(R.string.cambiosNv4HallaRitmos));
                this.texto.put(5, context.getString(R.string.cambiosNv5HallaRitmos));
                this.texto.put(6, context.getString(R.string.cambiosNv6HallaRitmos));
                this.texto.put(7, context.getString(R.string.cambiosNv7HallaRitmos));
                this.texto.put(8, context.getString(R.string.cambiosNv8HallaRitmos));break;

            }
            case "Realiza_Ritmos":{
                this.texto.put(2, context.getString(R.string.cambiosNv2RealizaRitmos));
                this.texto.put(3, context.getString(R.string.cambiosNv3RealizaRitmos));
                this.texto.put(4, context.getString(R.string.cambiosNv4RealizaRitmos));
                this.texto.put(5, context.getString(R.string.cambiosNv5RealizaRitmos));
                this.texto.put(6, context.getString(R.string.cambiosNv6RealizaRitmos));
                this.texto.put(7, context.getString(R.string.cambiosNv7RealizaRitmos));
                this.texto.put(8, context.getString(R.string.cambiosNv8RealizaRitmos));break;

            }

            }

    }
}
