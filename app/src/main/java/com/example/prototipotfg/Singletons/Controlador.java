package com.example.prototipotfg.Singletons;

import com.example.prototipotfg.Enumerados.Dificultad;
import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Enumerados.Octavas;
import com.example.prototipotfg.Enumerados.TipoModo;

import java.util.ArrayList;

public class Controlador {

    private static final Controlador ourInstance = new Controlador();
    private ModoJuego modo_juego;
    private TipoModo tipo_modo;
    private int nivel;
    private Dificultad dificultad;
    private ArrayList<Octavas> octavas = new ArrayList<>();
    private int num_opciones;

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

    public void estableceDificultad(ModoJuego modo_juego, TipoModo tipo_modo, int nivel){

        /*
        * Hacer un switch del tipo modo y modo juego como corresponda
        * Segun el nivel pues seguir tablita del word para setear numOpciones, Octavas y dificultad (configuracion en la tablita)
        * */

    }
}
