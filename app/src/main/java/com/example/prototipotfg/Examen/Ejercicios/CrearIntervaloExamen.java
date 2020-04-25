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
        setContentView(R.layout.nivel_crear_intervalo);
        ponerComprobarVisible(INVISIBLE);
        Random r = new Random();
        this.num_opciones = Controlador.getInstance().getNum_opciones();
        notasIntervalo = FactoriaNotas.getInstance().getNotasIntervalo(Controlador.getInstance().getOctavas(), Controlador.getInstance().getRango());
        this.notaInicio =  notasIntervalo.get(0).first;
        this.octavaInicio = notasIntervalo.get(0).second;
        this.octavaReproducir = octavaInicio;
        int tono1 = notasIntervalo.get(0).first.getTono();
        int tono2 = notasIntervalo.get(1).first.getTono();
        Intervalos intervalo = getIntervaloConDif((tono2-tono1));
        this.notasPosibles = seleccionaNotasAleatorios(intervalo);
        findViewById(R.id.continuar_ci).setEnabled(false);
        findViewById(R.id.continuar_ci).setAlpha(.5f);
        intervalo_nombre = intervalo.getNombre();
        intervalo_dif = intervalo.getDiferencia();
        TextView nota = (TextView)findViewById(R.id.Id_nota_intervalo);
        nota.setText(nota.getText() + notasIntervalo.get(0).first.getNombre() + notasIntervalo.get(0).second.getOctava());
        TextView peticion = (TextView)findViewById(R.id.Id_intervalo);
        peticion.setText(peticion.getText() + intervalo_nombre);
        if (Controlador.getInstance().getNivel() == 1){
            peticion.setText(peticion.getText().toString() + " (" + intervalo_dif + " semitonos)");
        }
        if(Controlador.getInstance().getDificultad().equals(Dificultad.Dificil))
            this.adaptaVistaDificil();

        //Obtenemos el linear layout donde colocar los botones
        LinearLayout opciones = (LinearLayout) findViewById(R.id.opciones_crear_intervalo);
        //Creamos las propiedades de layout que tendrán los botones.
        //Son LinearLayout.LayoutParams porque los botones van a estar en un LinearLayout.
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT );
        lp.setMargins(0,0,0,50);
        ArrayList<Integer> aux = new ArrayList<Integer>();
        for(int i = 0; i < num_opciones; i++) {
            aux.add(i);
        }
        Collections.shuffle(aux);
        //Creamos los botones en bucle
        for (int i=0; i<num_opciones; i++){
            Button button = new Button(this);
            button.setId(i+1);
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
            button.setPadding(0,0,0,0);
            botonesOpciones.add(button);
            opciones.addView(button);
            if (button.getText().toString().equals(notasIntervalo.get(1).first.getNombre() + notasIntervalo.get(1).second.getOctava())){
                this.respuestaCorrecta=button;
                respuesta_correcta = button.getText().toString();
            }
        }


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
        ((Button)findViewById(R.id.continuar_an)).setText("Continuar");
        findViewById(R.id.continuar_an).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
