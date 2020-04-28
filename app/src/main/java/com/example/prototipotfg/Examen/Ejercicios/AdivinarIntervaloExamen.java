package com.example.prototipotfg.Examen.Ejercicios;

import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import com.example.prototipotfg.BBDD.NivelAdivinar;
import com.example.prototipotfg.Enumerados.Dificultad;
import com.example.prototipotfg.Enumerados.Intervalos;
import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Enumerados.RangosPuntuaciones;
import com.example.prototipotfg.Examen.ControladorExamen;
import com.example.prototipotfg.Intervalos.Adivinar.AdivinarIntervalo;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.Controlador;
import com.example.prototipotfg.Singletons.FactoriaNotas;
import com.example.prototipotfg.Singletons.GestorBBDD;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static android.view.View.GONE;

public class AdivinarIntervaloExamen extends AdivinarIntervalo {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public void comprobarResultado(View view){
        this.comprobada = true;
        if (respuesta != this.intervalo_correcto) {
            botonSeleccionado.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_red_500)));
            ControladorExamen.getInstance().setResultadoEjercicioActual(false);
        }
        else
            ControladorExamen.getInstance().setResultadoEjercicioActual(true);
        respuestaCorrecta.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));

        findViewById(R.id.comprobar).setVisibility(View.GONE);
        for (Button b : botonesOpciones){
            b.setEnabled(false);
        }
        findViewById(R.id.botonIntervalo).setEnabled(false);
        findViewById(R.id.botonIntervalo).setAlpha(.5f);
        findViewById(R.id.botonReferencia).setEnabled(false);
        findViewById(R.id.botonReferencia).setAlpha(.5f);
        findViewById(R.id.continuar_ai).setEnabled(true);
        findViewById(R.id.continuar_ai).setAlpha(1);
        ((Button)findViewById(R.id.continuar_ai)).setText("Continuar");
        findViewById(R.id.continuar_ai).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
