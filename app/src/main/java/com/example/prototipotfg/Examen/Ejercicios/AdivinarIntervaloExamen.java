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
        setContentView(R.layout.nivel_adivinar_intervalo);
        ponerComprobarVisible(GONE);

        findViewById(R.id.continuar_ai).setEnabled(false);
        findViewById(R.id.continuar_ai).setAlpha(.5f);

        notasIntervalo = FactoriaNotas.getInstance().getNotasIntervalo(Controlador.getInstance().getOctavas(), Controlador.getInstance().getRango());
        int tono1 = notasIntervalo.get(0).first.getTono();
        int tono2 = notasIntervalo.get(1).first.getTono();
        Intervalos intervalo = getIntervaloConDif((tono2-tono1));
        this.intervalo_correcto = intervalo.getNombre();
        int posicion_intervalo = intervalo.getNumero();
        //inicializacion de botones
        FactoriaNotas.getInstance().setReferencia(notasIntervalo.get(0).second);
        //Obtenemos el linear layout donde colocar los botones
        LinearLayout opciones = (LinearLayout) findViewById(R.id.opciones);

        //Creamos las propiedades de layout que tendrán los botones.
        //Son LinearLayout.LayoutParams porque los botones van a estar en un LinearLayout.
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT );
        lp.setMargins(0,0,0,50);
        Random rand = new Random();
        int num_respuestas = Controlador.getInstance().getNum_opciones();
        int random1 = 0;
        if (Controlador.getInstance().getDificultad().equals(Dificultad.Facil)){
            random1 = rand.nextInt(Controlador.getInstance().getRango()+Controlador.getInstance().getRango()) - Controlador.getInstance().getRango();
        }
        else {
            random1 = rand.nextInt(Controlador.getInstance().getRango()) + 1;
            if (posicion_intervalo < 0) {
                random1 = -random1;
            }
        }
        ArrayList<Integer> aux = new ArrayList<>();
        aux.add(posicion_intervalo);


        for(int i = 0; i < num_respuestas-1; i++) {
            while (aux.contains(random1) || random1 == 0){
                if (Controlador.getInstance().getDificultad().equals(Dificultad.Facil)){
                    random1 = rand.nextInt(Controlador.getInstance().getRango()+Controlador.getInstance().getRango()) - Controlador.getInstance().getRango();
                }
                else {
                    random1 = rand.nextInt(Controlador.getInstance().getRango()) + 1;
                    if (posicion_intervalo < 0) {
                        random1 = -random1;
                    }
                }
            }
            aux.add(random1);
        }

        Collections.shuffle(aux);

        //Creamos los botones en bucle
        for (int i=0; i<num_respuestas; i++){
            Button button = new Button(this);
            button.setId(i+1);
            //Asignamos propiedades de layout al boton
            button.setLayoutParams(lp);
            //Asignamos Texto al botón
            button.setText(Intervalos.getIntervaloPorDiferencia(aux.get(i)).getNombre());

            //Asignamose el Listener
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    respuesta_seleccionada(v);
                }
            });
            //Añadimos el botón a la botonera
            button.setPadding(0,0,0,0);
            if (button.getText().equals(this.intervalo_correcto)){
                respuestaCorrecta = button;
            }
            botonesOpciones.add(button);
            opciones.addView(button);
        }


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
