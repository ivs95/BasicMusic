package com.example.prototipotfg.Examen.Ejercicios;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import com.example.prototipotfg.Enumerados.Instrumentos;
import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Enumerados.Octavas;
import com.example.prototipotfg.Examen.ControladorExamen;
import com.example.prototipotfg.Notas.AdivinarNota;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.Controlador;
import com.example.prototipotfg.Singletons.FactoriaNotas;
import com.example.prototipotfg.Singletons.GestorBBDD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static android.view.View.INVISIBLE;

public class AdivinarNotaExamen extends AdivinarNota {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nivel_seleccionar_adivinar_notas);
        ponerComprobarVisible(INVISIBLE);
        ArrayList<Octavas> octavas = Controlador.getInstance().getOctavas();
        HashMap<String, String> notas = null;
        notas = FactoriaNotas.getInstance().getNumNotasAleatorias(Controlador.getInstance().getNum_opciones(), Instrumentos.Piano, octavas);
        nombres = new ArrayList<>(notas.keySet());
        findViewById(R.id.continuar_an).setEnabled(false);
        findViewById(R.id.continuar_an).setAlpha(.5f);
        FactoriaNotas.getInstance().setReferencia(Octavas.devuelveOctavaPorNumero(Integer.parseInt(nombres.get(0).substring(nombres.get(0).length()-1))));
        FactoriaNotas.getInstance().setReferenciaDo(Octavas.devuelveOctavaPorNumero(Integer.parseInt(nombres.get(0).substring(nombres.get(0).length()-1))));
        adaptaVista(Controlador.getInstance().getDificultad());
        //Obtenemos el linear layout donde colocar los botones
        LinearLayout opciones = findViewById(R.id.opciones);

        //Creamos las propiedades de layout que tendrán los botones.
        //Son LinearLayout.LayoutParams porque los botones van a estar en un LinearLayout.
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT );
        Random rand = new Random();
        int num_respuestas = nombres.size();
        int random1 = rand.nextInt(num_respuestas);
        ArrayList <Integer> aux = new ArrayList<Integer>();
        aux.add(random1);
        for(int i = 0; i< num_respuestas-1; i++) {
            while (aux.contains(random1))
                random1 = rand.nextInt(num_respuestas);

            aux.add(random1);
        }

        //Creamos los botones en bucle
        for (int i=0; i<num_respuestas; i++){
            Button button = new Button(this);
            button.setId(i+1);
            //Asignamos propiedades de layout al boton
            button.setLayoutParams(lp);
            //Asignamos Texto al botón
            button.setText(nombres.get(aux.get(i)));

            //Asignamose el Listener
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    respuesta_seleccionada(v);
                }
            });
            //Añadimos el botón a la botonera
            opciones.addView(button);
            if (button.getText().toString() == nombres.get(0)){
                this.respuestaCorrecta=button;
            }
            botonesNotas.add(button);
        }


    }

    @Override
    public void comprobarResultado(View view) {
            this.comprobada = true;
            if (respuesta != nombres.get(0)) {
                botonSeleccionado.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_red_500)));
                ControladorExamen.getInstance().setResultadoEjercicioActual(false);
            }
            else
                ControladorExamen.getInstance().setResultadoEjercicioActual(true);
            respuestaCorrecta.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
            super.deshabilitaBotones();

        findViewById(R.id.comprobar).setVisibility(View.GONE);
        findViewById(R.id.continuar_an).setEnabled(true);
        findViewById(R.id.continuar_an).setAlpha(1);
        ((Button)findViewById(R.id.continuar_an)).setText("Continuar");
        findViewById(R.id.continuar_an).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
