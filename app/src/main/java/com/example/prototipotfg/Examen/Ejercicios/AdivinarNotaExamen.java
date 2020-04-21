package com.example.prototipotfg.Examen.Ejercicios;

import android.content.res.ColorStateList;
import android.view.View;
import android.widget.Button;

import androidx.core.content.ContextCompat;

import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Enumerados.RangosPuntuaciones;
import com.example.prototipotfg.Examen.ControladorExamen;
import com.example.prototipotfg.Notas.AdivinarNota;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.GestorBBDD;

public class AdivinarNotaExamen extends AdivinarNota {

    @Override
    public void comprobarResultado(View view) {
        int rangoActual = RangosPuntuaciones.getRangoPorNombre(GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Adivinar_Notas.toString()).getRango()).ordinal();
        if (!comprobada) {
            this.comprobada = true;
            if (respuesta != nombres.get(0)) {
                botonSeleccionado.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_red_500)));
                ControladorExamen.getInstance().setResultadoEjercicioActual(false);
            }
            else
                ControladorExamen.getInstance().setResultadoEjercicioActual(true);
            respuestaCorrecta.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
            super.deshabilitaBotones();
        }
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
