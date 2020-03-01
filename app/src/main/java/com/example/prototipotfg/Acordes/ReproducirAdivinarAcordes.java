package com.example.prototipotfg.Acordes;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.prototipotfg.Enumerados.Acordes;
import com.example.prototipotfg.Enumerados.Instrumentos;
import com.example.prototipotfg.Enumerados.Notas;
import com.example.prototipotfg.Enumerados.Octavas;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.Controlador;
import com.example.prototipotfg.Singletons.FactoriaNotas;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class ReproducirAdivinarAcordes extends Activity {

    private Acordes acordeCorrecto;
    private ArrayList<Acordes> acordesPosibles;
    private Notas notaInicio;
    private View botonSeleccionado;
    private View respuestaCorrecta;
    private int numOpciones;
    private boolean comprobada = false;
    private Octavas octavaInicio;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nivel_adivinar_acorde);
        Random random = new Random();
        this.numOpciones=Controlador.getInstance().getNum_opciones();
        this.octavaInicio = Controlador.getInstance().getOctavas().get((new Random()).nextInt(Controlador.getInstance().getOctavas().size()));
        this.acordesPosibles = seleccionaAcordesAleatorios(numOpciones, Controlador.getInstance().getAcordes());
        this.notaInicio = FactoriaNotas.getInstance().getNotaInicioIntervalo(Instrumentos.Piano, octavaInicio);
        this.acordeCorrecto = acordesPosibles.get(0);
        LinearLayout opciones = (LinearLayout) findViewById(R.id.opcionesAcordes);



    }

    private ArrayList<Acordes> seleccionaAcordesAleatorios(int numOpciones, ArrayList<Acordes> acordes) {
        ArrayList<Acordes> retorno = new ArrayList<>();
        Random random = new Random();
        Acordes acorde;
        for (int i = 0; i < numOpciones; i++){
            acorde = acordes.get(random.nextInt(acordes.size()));
            while (retorno.contains(acorde)){
                acorde = acordes.get(random.nextInt(acordes.size()));
            }
            retorno.add(acorde);
        }
        return retorno;
    }

    public void reproducirAcorde(View view){
        Acordes.reproducirAcorde(this.acordeCorrecto, this.notaInicio);
    }

    public void comprobarAcordes(View view){

    }

    public void volverAtrasAcordes(View view){

    }
}
