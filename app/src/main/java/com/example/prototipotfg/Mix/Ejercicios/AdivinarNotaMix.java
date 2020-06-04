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
import com.example.prototipotfg.Notas.AdivinarNota;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.Controlador;
import com.example.prototipotfg.Singletons.GestorBBDD;

public class AdivinarNotaMix extends AdivinarNota {

    private boolean resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        if (GestorBBDD.getInstance().esPrimerNivelAdivinar(ModoJuego.Modo_Mix, ControladorMix.getInstance().getNivel().getNivel()) && !Controlador.getInstance().getMixIniciado() && ControladorMix.getInstance().getNivel().getNivel() != 1) {

            LayoutInflater inflater = (LayoutInflater)
                    getSystemService(LAYOUT_INFLATER_SERVICE);

            ModoJuego.mostrarPopUpNuevoNivel(inflater, ModoJuego.Modo_Mix, findViewById(android.R.id.content).getRootView(), false, 0, 0);
        }
        ((TextView)findViewById(R.id.lblIndice)).setText(ControladorMix.getInstance().getIndiceActual()+1 + "/" + ControladorMix.getInstance().getNumEjercicios());
        findViewById(R.id.lblIndice).setVisibility(View.VISIBLE);

    }

    @Override
    public void comprobarResultado(View view) {
            this.comprobada = true;
            if (respuesta != nombres.get(0)) {
                botonSeleccionado.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_red_500)));
                resultado= false;
            }
            else
                resultado=true;
            respuestaCorrecta.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
            super.deshabilitaBotones();


        Controlador.getInstance().setMixIniciado(true);
        findViewById(R.id.comprobar).setVisibility(View.GONE);
        findViewById(R.id.continuar_an).setVisibility(View.VISIBLE);
        ((Button)findViewById(R.id.continuar_an)).setText("Continuar");
        findViewById(R.id.continuar_an).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("resultado",resultado);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }


}
