package com.example.prototipotfg.Mix.Ejercicios;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Singletons.ControladorMix;
import com.example.prototipotfg.Intervalos.Crear.CrearIntervalo;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.Controlador;
import com.example.prototipotfg.Singletons.GestorBBDD;

public class CrearIntervaloMix extends CrearIntervalo {
    private boolean resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (GestorBBDD.getInstance().esPrimerNivelAdivinar(ModoJuego.Modo_Mix, ControladorMix.getInstance().getNivel().getNivel()) && !Controlador.getInstance().getMixIniciado() && ControladorMix.getInstance().getNivel().getNivel() != 1) {

            LayoutInflater inflater = (LayoutInflater)
                    getSystemService(LAYOUT_INFLATER_SERVICE);

            ModoJuego.mostrarPopUpNuevoNivel(inflater, ModoJuego.Modo_Mix, findViewById(android.R.id.content).getRootView(), false, 0, 0);
        }
        ((TextView) findViewById(R.id.lblIndice)).setText(ControladorMix.getInstance().getIndiceActual() + 1 + "/" + ControladorMix.getInstance().getNumEjercicios());
        findViewById(R.id.lblIndice).setVisibility(View.VISIBLE);
    }

    @Override
    public void comprobarResultado(View view) {
        if (respuesta != respuesta_correcta) {
            resultado = false;
            botonSeleccionado.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_red_500)));
        } else
            resultado = true;

        respuestaCorrecta.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
        findViewById(R.id.comprobar_crear_intervalo).setVisibility(View.GONE);
        findViewById(R.id.Id_boton_reproduce_nota_intervalo).setEnabled(false);
        findViewById(R.id.Id_boton_reproduce_nota_intervalo).setAlpha(.5f);
        findViewById(R.id.botonReferencia).setEnabled(false);
        findViewById(R.id.botonReferencia).setAlpha(.5f);

        for (Button b : botonesOpciones) {
            b.setEnabled(false);
        }

        Controlador.getInstance().setMixIniciado(true);
        findViewById(R.id.continuar_ci).setVisibility(View.VISIBLE);
        ((Button) findViewById(R.id.continuar_ci)).setText("Continuar");
        findViewById(R.id.continuar_ci).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("resultado", resultado);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

}
