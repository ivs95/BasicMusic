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

import com.example.prototipotfg.BBDD.NivelAdivinar;
import com.example.prototipotfg.Enumerados.Dificultad;
import com.example.prototipotfg.Enumerados.Intervalos;
import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Enumerados.RangosPuntuaciones;
import com.example.prototipotfg.Examen.ControladorExamen;
import com.example.prototipotfg.Intervalos.Crear.CrearIntervalo;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.Controlador;
import com.example.prototipotfg.Singletons.FactoriaNotas;
import com.example.prototipotfg.Singletons.GestorBBDD;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static android.view.View.INVISIBLE;

public class CrearIntervaloExamen extends CrearIntervalo {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public void comprobarResultado(View view) {
        if (respuesta != respuesta_correcta) {
            ControladorExamen.getInstance().setResultadoEjercicioActual(false);
            botonSeleccionado.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_red_500)));
        }
        else
            ControladorExamen.getInstance().setResultadoEjercicioActual(true);

        respuestaCorrecta.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
        findViewById(R.id.comprobar_crear_intervalo).setVisibility(View.GONE);
        findViewById(R.id.Id_boton_reproduce_nota_intervalo).setEnabled(false);
        findViewById(R.id.Id_boton_reproduce_nota_intervalo).setAlpha(.5f);
        findViewById(R.id.botonReferencia).setEnabled(false);
        findViewById(R.id.botonReferencia).setAlpha(.5f);
        for (Button b : botonesOpciones){
            b.setEnabled(false);
        }
        findViewById(R.id.continuar_ci).setEnabled(true);
        findViewById(R.id.continuar_ci).setAlpha(1);
        ((Button)findViewById(R.id.continuar_ci)).setText("Continuar");
        findViewById(R.id.continuar_ci).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
