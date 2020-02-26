package com.example.prototipotfg.Intervalos.Adivinar;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import com.example.prototipotfg.Enumerados.Intervalos;
import com.example.prototipotfg.Enumerados.Notas;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.FactoriaNotas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static android.view.View.INVISIBLE;

public class SeleccionarAdivinarIntervalo extends Activity {
    private View botonSeleccionado;
    private View respuestaCorrecta;
    private ArrayList<String> nombres;


    private String respuesta;
    private String intervalo_correcto;
    private boolean comprobada = false;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nivel_seleccionar_adivinar);
        ponerComprobarVisible(INVISIBLE);
        nombres = getIntent().getExtras().getStringArrayList("nombres");

        int tono1 = getTonoNota(nombres.get(0));
        int tono2 = getTonoNota(nombres.get(1));
        Intervalos intervalo = getIntervaloConDif((tono2-tono1));
        this.intervalo_correcto = intervalo.getNombre();
        int posicion_intervalo = intervalo.getNumero();
        //inicializacion de botones



        //Obtenemos el linear layout donde colocar los botones
        LinearLayout opciones = (LinearLayout) findViewById(R.id.opciones);

        //Creamos las propiedades de layout que tendrán los botones.
        //Son LinearLayout.LayoutParams porque los botones van a estar en un LinearLayout.
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT );
        lp.setMargins(0,0,0,50);
        Random rand = new Random();

        int num_respuestas = getIntent().getExtras().getInt("opciones");
        int random1 = rand.nextInt(12) + 1;
        if (posicion_intervalo < 0){
            random1 = -random1;
        }
        ArrayList <Integer> aux = new ArrayList<Integer>();
        aux.add(posicion_intervalo);


        for(int i = 0; i < num_respuestas-1; i++) {
            while (aux.contains(random1)){
                random1 = rand.nextInt(12) + 1;
                if (posicion_intervalo < 0) {
                    random1 = -random1;
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
            opciones.addView(button);
        }


    }


    public void respuesta_seleccionada(View view){
        if (!comprobada) {
            Button b = (Button) view;
            if (botonSeleccionado != null) {
                botonSeleccionado.setBackgroundColor(ContextCompat.getColor(this, R.color.md_blue_300));
            }
            botonSeleccionado = b;
            botonSeleccionado.setBackgroundColor(ContextCompat.getColor(this, R.color.md_blue_700));

            respuesta = b.getText().toString();
            ponerComprobarVisible(1);
        }
    }


    public void volverAtras(View view){
        finish();
    }



    private void ponerComprobarVisible(int visible) {
        Button comprobar = (Button)findViewById(R.id.comprobar);
        comprobar.setVisibility(visible);
    }

    public void comprobarResultado(View view){
        if (!comprobada) {
            this.comprobada = true;
            if (respuesta != this.intervalo_correcto) {
                botonSeleccionado.setBackgroundColor(ContextCompat.getColor(this, R.color.md_red_500));
            }
            respuestaCorrecta.setBackgroundColor(ContextCompat.getColor(this, R.color.md_green_500));
    }
        findViewById(R.id.comprobar).setVisibility(View.GONE);
    }

    public Intervalos getIntervaloConDif(int dif){
        boolean OK = false;
        int i = 0;
        Intervalos[] intervalos_lista = new Intervalos[24];
        intervalos_lista = Intervalos.values();
        while(i < 24 && !OK){
            if(intervalos_lista[i].getDiferencia() == dif) OK = true;
            i++;

        }

        return intervalos_lista[i-1];
    }

    private int getTonoNota(String name){
        name = name.substring(0,name.length()-1);
        boolean OK = false;
        int i = 0;
        Notas[] lista_notas = new Notas[11];
        lista_notas = Notas.values();
        while(i < 11 && !OK){
            if(lista_notas[i].getNombre().equals(name)) OK = true;
            i++;

        }
        return lista_notas[i-1].getTono();
    }

    public void reproducirReferencia(View view) throws IOException {
        MediaPlayer mediaPlayer =  new MediaPlayer();
        AssetFileDescriptor afd = getAssets().openFd(FactoriaNotas.getInstance().getReferencia());
        mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        mediaPlayer.prepare();
        mediaPlayer.start();
    }

}