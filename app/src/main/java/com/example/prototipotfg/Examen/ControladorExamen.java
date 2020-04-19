package com.example.prototipotfg.Examen;

import android.util.Pair;

import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Enumerados.NivelExamen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public final class ControladorExamen {

    private static final ControladorExamen INSTANCE = new ControladorExamen();
    private int ejercicioActual;
    private int numEjercicios;
    private NivelExamen nivel;
    private ArrayList<ModoJuego> ejercicios = new ArrayList<> (Arrays.asList(ModoJuego.devuelvePruebasExamen()));
    private boolean[] acertados = new boolean[12];
    private int numAciertos;
    private ArrayList<Integer> repeticiones;


    /*
     * 2 preguntas cada tipo
     * Puntuaciones desbloquear y rango como notas
     * Porcentaje pruebas acertadas
     * Puntos como notas
     * Mezclar las pruebas
     * onResume para lanzar siguiente prueba
     * */

    public static ControladorExamen getInstance() {
        return INSTANCE;
    }

    public void setNivel(int nivel) {
        this.nivel = NivelExamen.getNivelExamen(nivel);
    }

    public void preparaExamen() {
        Collections.shuffle(ejercicios);
        ejercicioActual=0;
        numAciertos=0;
    }

    public Pair<ModoJuego,Integer> devuelveEjercicio() {
        ModoJuego pruebaActual = ejercicios.get(ejercicioActual);
        Integer nivelPrueba = nivel.getNivelModo(pruebaActual);
        return new Pair<ModoJuego,Integer>(pruebaActual,nivelPrueba);
    }

    public void setResultadoEjercicioActual(boolean resultado){
        acertados[ejercicioActual] = resultado;
        ejercicioActual++;
        if (resultado)
            numAciertos++;
    }

    public boolean finalExamen() {
        return numEjercicios == ejercicioActual;
    }
}
