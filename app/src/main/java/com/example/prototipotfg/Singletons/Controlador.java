package com.example.prototipotfg.Singletons;

import com.example.prototipotfg.Enumerados.Dificultad;
import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Enumerados.TipoModo;

public class Controlador {

    private static final Controlador ourInstance = new Controlador();
    private ModoJuego modo_juego;
    private TipoModo tipo_modo;
    private Dificultad dificultad;

    public static Controlador getInstance() {
        return ourInstance;
    }

    private Controlador() {}

    public ModoJuego getModo_juego() {
        return modo_juego;
    }

    public void setModo_juego(ModoJuego modo_juego) {
        this.modo_juego = modo_juego;
    }

    public TipoModo getTipo_modo() {
        return tipo_modo;
    }

    public void setTipo_modo(TipoModo tipo_modo) {
        this.tipo_modo = tipo_modo;
    }

    public Dificultad getDificultad(){
        return dificultad;
    }

    public void setDificultad(Dificultad dificultad){
        this.dificultad=dificultad;
    }
}
