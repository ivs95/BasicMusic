package com.example.prototipotfg.Singletons;

import com.example.prototipotfg.Enumerados.Dificultad;
import com.example.prototipotfg.Enumerados.Intervalos;
import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Enumerados.Octavas;

import java.util.ArrayList;
import java.util.Arrays;

public class Controlador {

    private static final Controlador ourInstance = new Controlador();
    private ModoJuego modo_juego;
    private int nivel;
    private Dificultad dificultad;
    private ArrayList<Octavas> octavas = new ArrayList<>();
    private ArrayList<Intervalos> intervalos = new ArrayList<>();
    private int num_opciones;

    public static Controlador getInstance() {
        return ourInstance;
    }

    private Controlador() {
    }

    public ModoJuego getModo_juego() {
        return modo_juego;
    }

    public void setModo_juego(ModoJuego modo_juego) {
        this.modo_juego = modo_juego;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
    public int getNum_opciones(){
        return this.num_opciones;
    }
    public int getNivel(){
            return this.nivel;
    }

    public Dificultad getDificultad() {
        return dificultad;
    }

    public void setDificultad(Dificultad dificultad) {
        this.dificultad = dificultad;
    }

    public void estableceDificultad() {

        /*
         * Hacer un switch del tipo modo y modo juego como corresponda
         * Segun el nivel pues seguir tablita del word para setear numOpciones, Octavas y dificultad (configuracion en la tablita)
         * */
        switch (this.modo_juego) {
            case Adivinar_Intervalo:
                estableceDificultadAdivinarIntervalo();
                break;
            default:
                break;
        }

    }

    private void estableceDificultadAdivinarIntervalo() {
        switch (this.nivel) {
            case 1:
                this.num_opciones = 2;
                this.octavas = new ArrayList<Octavas>(Arrays.asList(Octavas.Cuarta, Octavas.Quinta));
                this.dificultad = Dificultad.Facil;
                this.intervalos = Intervalos.getIntervalosDeRango(3);
                break;
            case 2:
                this.num_opciones = 3;
                this.octavas = new ArrayList<Octavas>(Arrays.asList(Octavas.Tercera, Octavas.Cuarta, Octavas.Quinta, Octavas.Sexta));
                this.dificultad = Dificultad.Facil;
                this.intervalos = Intervalos.getIntervalosDeRango(5);
                break;
            case 3:
                this.num_opciones = 4;
                this.octavas = new ArrayList<Octavas>(Arrays.asList(Octavas.Tercera, Octavas.Cuarta, Octavas.Quinta, Octavas.Sexta));
                this.dificultad = Dificultad.Facil;
                this.intervalos = Intervalos.getIntervalosDeRango(7);
                break;
            case 4:
                this.num_opciones = 4;
                this.octavas = new ArrayList<Octavas>(Arrays.asList(Octavas.Segunda, Octavas.Tercera, Octavas.Cuarta, Octavas.Quinta, Octavas.Sexta));
                this.dificultad = Dificultad.Medio;
                this.intervalos = Intervalos.getIntervalosDeRango(7);
                break;
            case 5:
                this.num_opciones = 4;
                this.octavas = new ArrayList<Octavas>(Arrays.asList(Octavas.Segunda, Octavas.Tercera, Octavas.Cuarta, Octavas.Quinta, Octavas.Sexta));
                this.dificultad = Dificultad.Medio;
                this.intervalos = Intervalos.getIntervalosDeRango(9);
                break;
            case 6:
                this.num_opciones = 5;
                this.octavas = new ArrayList<Octavas>(Arrays.asList(Octavas.Segunda, Octavas.Tercera, Octavas.Cuarta, Octavas.Quinta, Octavas.Sexta, Octavas.Septima));
                this.dificultad = Dificultad.Medio;
                this.intervalos = Intervalos.getIntervalosDeRango(11);
                break;
            case 7:
                this.num_opciones = 5;
                this.octavas = new ArrayList<Octavas>(Arrays.asList(Octavas.Primera, Octavas.Segunda, Octavas.Tercera, Octavas.Cuarta, Octavas.Quinta, Octavas.Sexta, Octavas.Septima));
                this.dificultad = Dificultad.Medio;
                this.intervalos = Intervalos.getIntervalosDeRango(13);
                break;
            default:
                break;
        }
    }

    public ArrayList<Octavas> getOctavas() {
        return this.octavas;
    }

}
