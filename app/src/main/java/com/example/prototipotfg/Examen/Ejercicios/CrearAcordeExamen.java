package com.example.prototipotfg.Examen.Ejercicios;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nivel_crear_acorde);
        ponerComprobarVisible(View.GONE);
        findViewById(R.id.continuar_ca).setEnabled(false);
        findViewById(R.id.continuar_ca).setAlpha(.5f);
        this.numOpciones = Controlador.getInstance().getNum_opciones();
        this.num_notas = numOpciones + 3;
        this.octavaInicio = Controlador.getInstance().getOctavas().get((new Random()).nextInt(Controlador.getInstance().getOctavas().size()-1));
        this.acordesPosibles = seleccionaAcordesAleatorios(Controlador.getInstance().getAcordes());
        this.notaInicio = FactoriaNotas.getInstance().getNotaInicioIntervalo(Instrumentos.Piano, octavaInicio);
        this.acordeCorrecto = acordesPosibles.get(0);
        this.acordeCorrectoReproducir = Acordes.devuelveNotasAcorde(acordeCorrecto,octavaInicio,notaInicio);
        this.notasPosibles = seleccionaNotasAleatorios(acordeCorrectoReproducir);
        this.botonesSeleccionados = new View[num_notas];
        this.respuestaCorrecta = new View[num_notas];
        int nivel = Controlador.getInstance().getNivel();

        TextView lblNotaInicio = findViewById(R.id.notaInicioCrearAcorde);
        TextView peticionAcorde = findViewById(R.id.lblPeticionCrearAcorde);

        //Button botonReferencia = findViewById(R.id.btnAcordeReferencia);
        //botonReferencia.setVisibility(VISIBLE);
        lblNotaInicio.setText(lblNotaInicio.getText() + acordeCorrectoReproducir.get(0).first.getNombre() + acordeCorrectoReproducir.get(0).second.getOctava());
        peticionAcorde.setText(peticionAcorde.getText() + acordeCorrecto.getNombre());

        Collections.shuffle(notasPosibles);

        ArrayList<Integer> aux = new ArrayList<Integer>();
        for(int i = 0; i < num_notas; i++) {
            aux.add(i);
        }

        if(nivel > 5){
            Button info = findViewById(R.id.infoCrearAcordes);
            info.setVisibility(GONE);
        }
        LinearLayout opciones = findViewById(R.id.opcionesCrearAcordes);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < num_notas; i++) {
            Button button = new Button(this);
            button.setId(i + 1);
            //Asignamos propiedades de layout al boton
            button.setLayoutParams(lp);
            //Asignamos Texto al botón
            button.setText(notasPosibles.get(aux.get(i)));

            //Asignamose el Listener
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    respuesta_seleccionada(v);
                }
            });
            //Añadimos el botón a la botonera
            button.setPadding(0, 0, 0, 0);
            String text = button.getText().toString();
            Pair<Notas, Octavas> par = new Pair<>(Notas.devuelveNotaPorNombre(text.substring(0, text.length()-1)), Octavas.devuelveOctavaPorNumero(Integer.parseInt(text.substring(text.length()-1))));
            if (acordeCorrectoReproducir.contains(par)) {
                respuestaCorrecta[(int) button.getId() - 1] = button;
            }
            else respuestaCorrecta[(int) button.getId() - 1] = null;
            botonesOpciones.add(button);
            opciones.addView(button);
        }


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
                correcta = false;
            }
        }
        if(respuestas.size() != acordeCorrectoReproducir.size()-1)
            correcta = false;

        if(correcta)
            ControladorExamen.getInstance().setResultadoEjercicioActual(true);
        else
            ControladorExamen.getInstance().setResultadoEjercicioActual(false);
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
        findViewById(R.id.continuar_ca).setEnabled(true);
        findViewById(R.id.continuar_ca).setAlpha(1);
        ((Button)findViewById(R.id.continuar_an)).setText("Continuar");
        findViewById(R.id.continuar_an).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

}
