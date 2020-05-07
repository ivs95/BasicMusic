package com.example.prototipotfg.Examen.Ejercicios;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.prototipotfg.Acordes.Crear.CrearAcorde;
import com.example.prototipotfg.BBDD.NivelAdivinar;
import com.example.prototipotfg.Enumerados.Acordes;
import com.example.prototipotfg.Enumerados.Instrumentos;
import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Enumerados.Notas;
import com.example.prototipotfg.Enumerados.Octavas;
import com.example.prototipotfg.Enumerados.RangosPuntuaciones;
import com.example.prototipotfg.Examen.ControladorExamen;
import com.example.prototipotfg.Examen.SeleccionNivelExamen;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.Controlador;
import com.example.prototipotfg.Singletons.FactoriaNotas;
import com.example.prototipotfg.Singletons.GestorBBDD;
import com.example.prototipotfg.Singletons.Reproductor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static android.view.View.GONE;

public class CrearAcordeExamen extends CrearAcorde {

    private boolean resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (GestorBBDD.getInstance().esPrimerNivelAdivinar(ModoJuego.Modo_Mix, ControladorExamen.getInstance().getNivel().getNivel()) && !Controlador.getInstance().getMixIniciado() && ControladorExamen.getInstance().getNivel().getNivel() != 1) {

            LayoutInflater inflater = (LayoutInflater)
                    getSystemService(LAYOUT_INFLATER_SERVICE);

            ModoJuego.mostrarPopUpNuevoNivel(inflater, ModoJuego.Modo_Mix, findViewById(android.R.id.content).getRootView());
        }
        ((TextView)findViewById(R.id.lblIndice)).setText(ControladorExamen.getInstance().getIndiceActual()+1 + "/" + ControladorExamen.getInstance().getNumEjercicios());
        findViewById(R.id.lblIndice).setVisibility(View.VISIBLE);

    }

    @Override
    public void comprobarCrearAcordes(View view) {


        for(int i = 0; i<num_notas; i++){
            if(respuestaCorrecta[i] != null)
                respuestaCorrecta[i].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));

            if(botonesSeleccionados[i]!=null && respuestaCorrecta[i] == null){
                botonesSeleccionados[i].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_red_500)));
            }
        }

        boolean correcta = true;
        for(int i = 0; i < respuestas.size();i++){
            String text = respuestas.get(i);
            Pair<Notas, Octavas> par = new Pair<>(Notas.devuelveNotaPorNombre(text.substring(0, text.length()-1)), Octavas.devuelveOctavaPorNumero(Integer.parseInt(text.substring(text.length()-1))));
            if (!acordeCorrectoReproducir.contains(par)) {
                resultado = false;
            }
        }
        if(respuestas.size() != acordeCorrectoReproducir.size()-1)
            resultado = false;

        Controlador.getInstance().setMixIniciado(true);
        ArrayList<AssetFileDescriptor> afd = preparaAssets(acordeCorrectoReproducir);
        Reproductor.getInstance().reproducirAcorde(afd);
        cierraAssets(afd);
        findViewById(R.id.btnCrearAcordeReferencia).setEnabled(false);
        findViewById(R.id.btnCrearAcordeReferencia).setAlpha(.5f);
        findViewById(R.id.botonReproduceCrearAcorde).setEnabled(false);
        findViewById(R.id.botonReproduceCrearAcorde).setAlpha(.5f);
        findViewById(R.id.infoCrearAcordes).setEnabled(false);
        findViewById(R.id.infoCrearAcordes).setAlpha(.5f);
        for (Button b : botonesOpciones)
            b.setEnabled(false);
        ponerComprobarVisible(GONE);
        findViewById(R.id.continuar_ca).setVisibility(View.VISIBLE);
        ((Button)findViewById(R.id.continuar_ca)).setText("Continuar");
        findViewById(R.id.continuar_ca).setOnClickListener(new View.OnClickListener() {
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
