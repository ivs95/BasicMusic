package com.example.prototipotfg.Examen;

import android.util.Pair;

import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Enumerados.NivelExamen;
import com.example.prototipotfg.Singletons.Controlador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public final class ControladorExamen {

    private static final ControladorExamen INSTANCE = new ControladorExamen();
    private final int NUM_EJERCICIOS = 14;
    private int indiceActual;
    private Pair<ModoJuego, Integer> ejercicioActual;
    private NivelExamen nivel;
    private ArrayList<ModoJuego> ejercicios = new ArrayList<> (Arrays.asList(ModoJuego.devuelvePruebasExamen()));
    private boolean[] acertados = new boolean[14];
    private int numAciertos;
    private boolean aprobado;


    /*
     * Puntuaciones desbloquear y rango como notas
     * Porcentaje pruebas acertadas
     * Puntos como notas
     * */

    public static ControladorExamen getInstance() {
        return INSTANCE;
    }

    public void setNivel(int nivel) {
        this.nivel = NivelExamen.getNivelExamen(nivel);
    }

    public void preparaExamen() {
        Collections.shuffle(ejercicios);
        indiceActual=0;
        numAciertos=0;
    }

    public void iniciaExamen(){
        preparaExamen();
        while(!finalExamen()) {
            setEjercicio();
            iniciaPrueba();
        }
        setResultadoExamen();
    }

    private void setResultadoExamen() {
        this.aprobado=((double)numAciertos/NUM_EJERCICIOS)>=nivel.getPorcentajeAprobar();
    }

    public void setEjercicio() {
        ejercicioActual = new Pair(ejercicios.get(indiceActual), nivel.getNivelModo(ejercicios.get(indiceActual)));

    }

    public void setResultadoEjercicioActual(boolean resultado){
        acertados[indiceActual] = resultado;
        indiceActual++;
        if (resultado)
            numAciertos++;
    }

    private void iniciaPrueba() {
        Controlador.getInstance().setModo_juego(ejercicioActual.first);
        Controlador.getInstance().setNivel(ejercicioActual.second);
        Controlador.getInstance().estableceDificultad();
        switch (ejercicioActual.first){
            case Adivinar_Acordes:
            case Adivinar_Intervalo:
            case Halla_Ritmo:
            case Realiza_Ritmo:
            case Crear_Acordes:
            case Crear_Intervalo:
            case Adivinar_Notas:
        }
    }

    public boolean finalExamen() {
        return NUM_EJERCICIOS == indiceActual;
    }
}
