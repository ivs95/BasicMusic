package com.example.prototipotfg.Intervalos.Crear;


import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.ColorStateList;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.prototipotfg.Enumerados.Dificultad;
import com.example.prototipotfg.Enumerados.Intervalos;
import com.example.prototipotfg.Enumerados.Notas;
import com.example.prototipotfg.Enumerados.Octavas;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.Controlador;
import com.example.prototipotfg.Singletons.FactoriaNotas;

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

    private String intervalo_nombre;
    private int intervalo_dif;

    private String respuesta;
    private String respuesta_correcta;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nivel_crear_intervalo);
        ponerComprobarVisible(INVISIBLE);

        nombres = getIntent().getExtras().getStringArrayList("nombres");
        rutas = getIntent().getExtras().getStringArrayList("rutas");

        Notas notaInicio = Notas.devuelveNotaPorNombre(nombres.get(0).substring(0,nombres.get(0).length()-1));
        //Notas notaFinal = Notas.devuelveNotaPorNombre(nombres.get(1).substring(0,nombres.get(1).length()-1));
        ArrayList<Intervalos> intervalos_posibles = Intervalos.devuelveIntervalosPosibles(notaInicio);

        Random r = new Random();

        Intervalos intervalo = intervalos_posibles.get(r.nextInt(Controlador.getInstance().getRango()));
        intervalo_nombre = intervalo.getNombre();
        intervalo_dif = intervalo.getDiferencia();


        TextView nota = (TextView)findViewById(R.id.Id_nota_intervalo);
        nota.setText(nota.getText() + nombres.get(0));

        TextView peticion = (TextView)findViewById(R.id.Id_intervalo);
        peticion.setText(peticion.getText() + intervalo_nombre);

        if(Controlador.getInstance().getDificultad().equals(Dificultad.Dificil))
            this.adaptaVistaDificil();

        intervalo_nombre = getIntent().getExtras().getString("peticion_nombre");
        intervalo_dif = getIntent().getExtras().getInt("peticion_dif");


        //inicializacion de botones

        respuesta_correcta = nombres.get(1);

        //Obtenemos el linear layout donde colocar los botones
        LinearLayout opciones = (LinearLayout) findViewById(R.id.opciones_crear_intervalo);

        //Creamos las propiedades de layout que tendrán los botones.
        //Son LinearLayout.LayoutParams porque los botones van a estar en un LinearLayout.
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT );
        lp.setMargins(0,0,0,50);
        Random rand = new Random();

        int num_respuestas = Controlador.getInstance().getNum_opciones();
        ArrayList <Integer> aux = new ArrayList<Integer>();
        for(int i = 0; i < num_respuestas; i++) {
            aux.add(i);
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
            if (button.getText().toString() == nombres.get(1)){
                this.respuestaCorrecta=button;
            }
        }


    }


    public void respuesta_seleccionada(View view){
        if (!comprobada) {
            Button b = (Button) view;
            if (botonSeleccionado != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    botonSeleccionado.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_blue_300)));
                }
            }
            botonSeleccionado = b;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                botonSeleccionado.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_blue_700)));
            }

            respuesta = b.getText().toString();


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
        Button comprobar = (Button)findViewById(R.id.comprobar_crear_intervalo);
        comprobar.setVisibility(visible);
    }

    public void reproducir(View view) throws IOException {
        MediaPlayer mediaPlayer =  new MediaPlayer();
        AssetFileDescriptor afd = getAssets().openFd(rutas.get(0));
        mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        mediaPlayer.prepare();
        mediaPlayer.start();

    }



    public void comprobarResultado(View view) {
        if (!comprobada) {
            this.comprobada = true;
            if (respuesta != respuesta_correcta) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    botonSeleccionado.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_red_500)));
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                respuestaCorrecta.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
            }
        }
        findViewById(R.id.comprobar_crear_intervalo).setVisibility(View.GONE);

    }

    private void adaptaVistaDificil() {
        TextView nota = findViewById(R.id.Id_nota_intervalo);
        nota.setVisibility(View.GONE);
    }


}
