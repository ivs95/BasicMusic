package com.example.prototipotfg;


import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.core.content.ContextCompat;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static android.view.View.INVISIBLE;

public class SeleccionarAdivinarCrearIntervalo extends Activity {

    private View botonSeleccionado;
    private View respuestaCorrecta;
    private boolean comprobada = false;

    private ArrayList<String> nombres;
    private ArrayList<String> rutas;
    private String dificultad;

    private String intervalo_nombre;
    private int intervalo_dif;

    private String respuesta;
    private String respuesta_correcta;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nivel_seleccionar_adivinar);
        ponerComprobarVisible(INVISIBLE);
        nombres = getIntent().getExtras().getStringArrayList("nombres");
        rutas = getIntent().getExtras().getStringArrayList("rutas");
        dificultad = getIntent().getExtras().getString("dificultad");

        intervalo_nombre = getIntent().getExtras().getString("peticion_nombre");
        intervalo_dif = getIntent().getExtras().getInt("peticion_nombre");

        if (dificultad.equals("dificil")){
            Button referencia = findViewById(R.id.botonReferencia);
            referencia.setVisibility(View.GONE);
            Button retroceder = findViewById(R.id.retroceder);
            retroceder.setVisibility(View.GONE);
        }

        //inicializacion de botones

        int tono = getTonoNota(nombres.get(0));
        respuesta_correcta = getNotaTono(tono+intervalo_dif);

        //Obtenemos el linear layout donde colocar los botones
        LinearLayout opciones = (LinearLayout) findViewById(R.id.opciones);

        //Creamos las propiedades de layout que tendrán los botones.
        //Son LinearLayout.LayoutParams porque los botones van a estar en un LinearLayout.
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT );
        lp.setMargins(0,0,0,50);
        Random rand = new Random();

        int num_respuestas = nombres.size();

        int random1 = rand.nextInt(num_respuestas);
        ArrayList <Integer> aux = new ArrayList<Integer>();
        aux.add(tono+intervalo_dif);


        for(int i = 1; i< num_respuestas; i++) {
            while (aux.contains(random1))
                random1 = rand.nextInt(num_respuestas);

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
            button.setText(nombres.get(aux.get(i)));

            //Asignamose el Listener
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    respuesta_seleccionada(v);
                }
            });
            //Añadimos el botón a la botonera
            button.setPadding(0,0,0,0);

            opciones.addView(button);
            if (button.getText().toString() == nombres.get(0)){
                this.respuestaCorrecta=button;
            }
        }


    }


    public void respuesta_seleccionada(View view){
        if (!comprobada) {
            Button b = (Button) view;
            if (botonSeleccionado != null) {
                botonSeleccionado.setBackgroundColor(ContextCompat.getColor(this, R.color.md_orange_400));
            }
            botonSeleccionado = b;
            botonSeleccionado.setBackgroundColor(ContextCompat.getColor(this, R.color.md_deep_orange_900));

            respuesta = b.getText().toString();

            if (dificultad.equals("facil")) {
                String ruta = devuelveRutaBoton(respuesta);

                MediaPlayer mediaPlayer = new MediaPlayer();
                AssetFileDescriptor afd = null;
                try {
                    afd = getAssets().openFd(ruta);
                    mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.start();
            }

            ponerComprobarVisible(1);
        }
    }

    private String devuelveRutaBoton(String text) {
        Notas n = Notas.devuelveNotaPorNombre(text.substring(0, text.length()-1));
        Octavas o = Octavas.devuelveOctavaPorNumero(Integer.parseInt(text.substring(text.length()-1)));
        return FactoriaNotas.getInstance().getInstrumento().getPath()+o.getPath()+n.getPath();
    }


    public void volverAtras(View view){
        finish();
    }

    public void reproducirReferencia(View view) throws IOException {
        MediaPlayer mediaPlayer =  new MediaPlayer();
        AssetFileDescriptor afd = getAssets().openFd(FactoriaNotas.getInstance().getReferencia());
        mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        mediaPlayer.prepare();
        mediaPlayer.start();
    }


    private void ponerComprobarVisible(int visible) {
        Button comprobar = (Button)findViewById(R.id.comprobar);
        comprobar.setVisibility(visible);
    }

    public void comprobarResultado(View view) {
        if (!comprobada) {
            this.comprobada = true;
            if (respuesta != nombres.get(0)) {
                botonSeleccionado.setBackgroundColor(ContextCompat.getColor(this, R.color.md_red_500));
            }
            respuestaCorrecta.setBackgroundColor(ContextCompat.getColor(this, R.color.md_green_500));
        }
        findViewById(R.id.comprobar).setVisibility(View.GONE);

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

    private String getNotaTono(int tono) {
        boolean OK = false;
        int i = 0;
        Notas[] lista_notas = new Notas[11];
        lista_notas = Notas.values();
        while (i < 11 && !OK) {
            if (lista_notas[i].getTono() == tono) OK = true;
            i++;

        }
        return lista_notas[i - 1].getNombre();

    }
}
