package com.example.prototipotfg.Mix.Ejercicios;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.prototipotfg.Acordes.Adivinar.AdivinarAcorde;
import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Singletons.ControladorMix;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.Controlador;
import com.example.prototipotfg.Singletons.GestorBBDD;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class AdivinarAcordeMix extends AdivinarAcorde {
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
    public void comprobarAcordes(View view) {
        this.comprobada = true;
        if (this.botonSeleccionado != this.respuestaCorrecta) {
            botonSeleccionado.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_red_500)));
            resultado = false;
        } else
            resultado = true;

        Controlador.getInstance().setMixIniciado(true);
        respuestaCorrecta.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
        ponerComprobarVisible(GONE);
        findViewById(R.id.btnAcordeReferencia).setEnabled(false);
        findViewById(R.id.btnAcordeReferencia).setAlpha(.5f);
        findViewById(R.id.button_info_adivinarAcorde).setEnabled(false);
        findViewById(R.id.button_info_adivinarAcorde).setAlpha(.5f);
        findViewById(R.id.botonReproduceAdivinarAcorde).setEnabled(false);
        findViewById(R.id.botonReproduceAdivinarAcorde).setAlpha(.5f);
        for (Button b : botonesOpciones)
            b.setEnabled(false);


        findViewById(R.id.continuar_ac).setVisibility(VISIBLE);
        ((Button) findViewById(R.id.continuar_ac)).setText("Continuar");
        findViewById(R.id.continuar_ac).setOnClickListener(new View.OnClickListener() {

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
