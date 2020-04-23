package com.example.prototipotfg.Examen.Ejercicios;

import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.prototipotfg.Acordes.Adivinar.AdivinarAcorde;
import com.example.prototipotfg.BBDD.NivelAdivinar;
import com.example.prototipotfg.Enumerados.Acordes;
import com.example.prototipotfg.Enumerados.Dificultad;
import com.example.prototipotfg.Enumerados.Instrumentos;
import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Enumerados.RangosPuntuaciones;
import com.example.prototipotfg.Examen.ControladorExamen;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.Controlador;
import com.example.prototipotfg.Singletons.FactoriaNotas;
import com.example.prototipotfg.Singletons.GestorBBDD;

import java.util.Collections;
import java.util.Random;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class AdivinarAcordeExamen extends AdivinarAcorde {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nivel_adivinar_acorde);
        ponerComprobarVisible(View.GONE);
        findViewById(R.id.continuar_ac).setEnabled(false);
        findViewById(R.id.continuar_ac).setAlpha(.5f);
        findViewById(R.id.button_info_adivinarAcorde).setVisibility(GONE);
        this.numOpciones = Controlador.getInstance().getNum_opciones();
        this.octavaInicio = Controlador.getInstance().getOctavas().get((new Random()).nextInt(Controlador.getInstance().getOctavas().size()-1));
        this.acordesPosibles = seleccionaAcordesAleatorios(numOpciones, Controlador.getInstance().getAcordes());
        this.notaInicio = FactoriaNotas.getInstance().getNotaInicioIntervalo(Instrumentos.Piano, octavaInicio);
        this.acordeCorrecto = acordesPosibles.get(0);
        this.acordeCorrectoReproducir = Acordes.devuelveNotasAcorde(acordeCorrecto,octavaInicio,notaInicio);
        TextView lblNotaInicio = findViewById(R.id.lblNotaInicioAcorde);
        TextView lblOctavaInicio = findViewById(R.id.lblOctavaInicioAcorde);
        if (Controlador.getInstance().getDificultad().equals(Dificultad.Dificil)){
            lblNotaInicio.setVisibility(GONE);
            lblOctavaInicio.setVisibility(GONE);
            Button botonTutorial = findViewById(R.id.button_info_adivinarAcorde);
            botonTutorial.setVisibility(GONE);
            Button botonReferencia = findViewById(R.id.btnAcordeReferencia);
            botonReferencia.setVisibility(VISIBLE);
        }
        else {
            lblNotaInicio.setText(lblNotaInicio.getText() + notaInicio.getNombre());
            lblOctavaInicio.setText(lblOctavaInicio.getText() + octavaInicio.getNombre());
        }
        Collections.shuffle(acordesPosibles);
        for (Acordes a : acordesPosibles)
            acordesReproducir.add(Acordes.devuelveNotasAcorde(a, this.octavaInicio, this.notaInicio));

        LinearLayout opciones = findViewById(R.id.opcionesAcordes);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < numOpciones; i++) {
            Button button = new Button(this);
            button.setId(i + 1);
            //Asignamos propiedades de layout al boton
            button.setLayoutParams(lp);
            //Asignamos Texto al botón
            button.setText(acordesPosibles.get(i).getNombre());

            //Asignamose el Listener
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    respuesta_seleccionada(v);
                }
            });
            //Añadimos el botón a la botonera
            button.setPadding(0, 0, 0, 0);
            if (button.getText().equals(this.acordeCorrecto.getNombre()))
                respuestaCorrecta = button;
            botonesOpciones.add(button);
            opciones.addView(button);
        }
    }

    @Override
    public void comprobarAcordes(View view) {
        this.comprobada = true;
        if (this.botonSeleccionado != this.respuestaCorrecta) {
            botonSeleccionado.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_red_500)));
            ControladorExamen.getInstance().setResultadoEjercicioActual(false);
        }
        else
            ControladorExamen.getInstance().setResultadoEjercicioActual(true);
            respuestaCorrecta.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
        ponerComprobarVisible(GONE);
        findViewById(R.id.btnAcordeReferencia).setEnabled(false);             findViewById(R.id.btnAcordeReferencia).setAlpha(.5f);
        findViewById(R.id.button_info_adivinarAcorde).setEnabled(false);      findViewById(R.id.button_info_adivinarAcorde).setAlpha(.5f);
        findViewById(R.id.botonReproduceAdivinarAcorde).setEnabled(false);    findViewById(R.id.botonReproduceAdivinarAcorde).setAlpha(.5f);
        for (Button b : botonesOpciones)
            b.setEnabled(false);
        findViewById(R.id.continuar_ac).setEnabled(true);          findViewById(R.id.continuar_ac).setAlpha(1);
        ((Button)findViewById(R.id.continuar_an)).setText("Continuar");
        findViewById(R.id.continuar_an).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
