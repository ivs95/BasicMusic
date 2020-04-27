package com.example.prototipotfg.Examen;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;

import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Enumerados.NivelExamen;
import com.example.prototipotfg.Examen.Ejercicios.AdivinarAcordeExamen;
import com.example.prototipotfg.Singletons.Controlador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public final class ControladorExamen {

    private static final ControladorExamen INSTANCE = new ControladorExamen();
    private Context contexto;
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
    public void setContext(Context contexto){
        this.contexto=contexto;
    }

    public void preparaExamen() {
        Collections.shuffle(ejercicios);
        indiceActual=0;
        numAciertos=0;
    }

    public void iniciaExamen(){
        preparaExamen();
    }

    public void setResultadoExamen() {
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

    public Intent iniciaPrueba(Context contexto) {
        Controlador.getInstance().setModo_juego(ejercicioActual.first);
        Controlador.getInstance().setNivel(ejercicioActual.second);
        Controlador.getInstance().estableceDificultad();
        Intent i = null;
        switch (ejercicioActual.first){
            case Adivinar_Acordes:
                i = new Intent(contexto, AdivinarAcordeExamen.class);break;
            case Adivinar_Intervalo:
                i = new Intent(contexto, AdivinarAcordeExamen.class);break;
            case Halla_Ritmo:
                i = new Intent(contexto, AdivinarAcordeExamen.class);break;
            case Realiza_Ritmo:
                i = new Intent(contexto, AdivinarAcordeExamen.class);break;
            case Crear_Acordes:
                i = new Intent(contexto, AdivinarAcordeExamen.class);break;
            case Crear_Intervalo:
                i = new Intent(contexto, AdivinarAcordeExamen.class);break;
            case Adivinar_Notas:
                i = new Intent(contexto, AdivinarAcordeExamen.class);break;
        }
        return i;
    }

    public boolean finalExamen() {
        return NUM_EJERCICIOS == indiceActual;
    }

}
