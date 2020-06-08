package com.example.prototipotfg.Singletons;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;

import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Enumerados.NivelMix;
import com.example.prototipotfg.Mix.Ejercicios.AdivinarAcordeMix;
import com.example.prototipotfg.Mix.Ejercicios.AdivinarIntervaloMix;
import com.example.prototipotfg.Mix.Ejercicios.AdivinarNotaMix;
import com.example.prototipotfg.Mix.Ejercicios.CrearAcordeMix;
import com.example.prototipotfg.Mix.Ejercicios.CrearIntervaloMix;
import com.example.prototipotfg.Mix.Ejercicios.ImitarRitmoMix;
import com.example.prototipotfg.Mix.Ejercicios.DibujarRitmoMix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public final class ControladorMix {

    private static final ControladorMix INSTANCE = new ControladorMix();
    private final int NUM_EJERCICIOS = 14;
    private int indiceActual;
    private Pair<ModoJuego, Integer> ejercicioActual;
    private NivelMix nivel;
    private ArrayList<ModoJuego> ejercicios = new ArrayList<> (Arrays.asList(NivelMix.Uno.getModos()));
    private boolean[] acertados = new boolean[14];
    private int numAciertos;
    private boolean aprobado;
    private HashMap<ModoJuego, Integer> resultadoEjercicios = new HashMap<>();


    /*
     * Puntuaciones desbloquear y rango como notas
     * Porcentaje pruebas acertadas
     * Puntos como notas
     * */

    public static ControladorMix getInstance() {
        return INSTANCE;
    }

    public void setNivel(int nivel) {
        this.nivel = NivelMix.getNivelMix(nivel);
    }

    public NivelMix getNivel(){return this.nivel;}
    public void preparaModo() {
        ejercicios.addAll(ejercicios);
        Collections.shuffle(ejercicios);
        indiceActual=0;
        numAciertos=0;
        inicializaResultados();
    }

    public int getIndiceActual(){
        return this.indiceActual;
    }

    private void inicializaResultados() {
        resultadoEjercicios.put(ModoJuego.Adivinar_Acordes,0);
        resultadoEjercicios.put(ModoJuego.Adivinar_Intervalo,0);
        resultadoEjercicios.put(ModoJuego.Adivinar_Notas,0);
        resultadoEjercicios.put(ModoJuego.Crear_Acordes,0);
        resultadoEjercicios.put(ModoJuego.Crear_Intervalo,0);
        resultadoEjercicios.put(ModoJuego.Halla_Ritmo,0);
        resultadoEjercicios.put(ModoJuego.Realiza_Ritmo,0);
    }

    public int getNumAciertos(){
        return this.numAciertos;
    }

    public void iniciaModo(){
        preparaModo();
    }

    public void setResultadoModo() {
        this.aprobado=this.numAciertos>=nivel.getAciertosAprobar();
    }

    public boolean isAprobado(){
        return aprobado;
    }

    public void setEjercicio() {
        ejercicioActual = new Pair(ejercicios.get(indiceActual), nivel.getNivelModo(ejercicios.get(indiceActual)));

    }

    public void setResultadoEjercicioActual(boolean resultado){
        acertados[indiceActual] = resultado;
        indiceActual++;
        if (resultado) {
            resultadoEjercicios.put(ejercicios.get(indiceActual-1), resultadoEjercicios.get(ejercicios.get(indiceActual-1)) + 1);
            numAciertos++;
        }
    }

    public Intent iniciaPrueba(Context contexto) {
        Controlador.getInstance().setModo_juego(ejercicioActual.first);
        Controlador.getInstance().setNivel(ejercicioActual.second);
        Controlador.getInstance().estableceDificultad();
        Intent i = null;
        switch (ejercicioActual.first){
            case Adivinar_Acordes:
                i = new Intent(contexto, AdivinarAcordeMix.class);break;
            case Adivinar_Intervalo:
                i = new Intent(contexto, AdivinarIntervaloMix.class);break;
            case Halla_Ritmo:
                i = new Intent(contexto, DibujarRitmoMix.class);break;
            case Realiza_Ritmo:
                i = new Intent(contexto, ImitarRitmoMix.class);break;
            case Crear_Acordes:
                i = new Intent(contexto, CrearAcordeMix.class);break;
            case Crear_Intervalo:
                i = new Intent(contexto, CrearIntervaloMix.class);break;
            case Adivinar_Notas:
                i = new Intent(contexto, AdivinarNotaMix.class);break;
        }
        return i;
    }

    public boolean finalMix() {
        return NUM_EJERCICIOS == indiceActual;
    }


    public HashMap<ModoJuego, Integer> getResultadoEjercicios() {
        return resultadoEjercicios;
    }

    public int getNumEjercicios() {
        return this.NUM_EJERCICIOS;
    }

}
