package com.example.prototipotfg.Notas;


import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.ColorStateList;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import com.example.prototipotfg.BBDD.NivelAdivinar;
import com.example.prototipotfg.Enumerados.Dificultad;
import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Enumerados.Notas;
import com.example.prototipotfg.Enumerados.Octavas;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.Controlador;
import com.example.prototipotfg.Singletons.FactoriaNotas;
import com.example.prototipotfg.Singletons.GestorBBDD;
import com.example.prototipotfg.Singletons.Reproductor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static android.view.View.INVISIBLE;

public class SeleccionarAdivinarNotas extends Activity {

    private View botonSeleccionado;
    private View respuestaCorrecta;
    private boolean comprobada = false;
    private ArrayList<String> nombres;
    private ArrayList<Button> botonesNotas = new ArrayList<>();

    private String respuesta;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nivel_seleccionar_adivinar_notas);
        ponerComprobarVisible(INVISIBLE);
        nombres = getIntent().getExtras().getStringArrayList("nombres");
        FactoriaNotas.getInstance().setReferencia(Octavas.devuelveOctavaPorNumero(Integer.parseInt(nombres.get(0).substring(nombres.get(0).length()-1))));
        adaptaVista(Controlador.getInstance().getDificultad());
        //Obtenemos el linear layout donde colocar los botones
        LinearLayout opciones = (LinearLayout) findViewById(R.id.opciones);

        //Creamos las propiedades de layout que tendrán los botones.
        //Son LinearLayout.LayoutParams porque los botones van a estar en un LinearLayout.
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT );
        Random rand = new Random();
        int num_respuestas = nombres.size();
        int random1 = rand.nextInt(num_respuestas);
        ArrayList <Integer> aux = new ArrayList<Integer>();
        aux.add(random1);
        for(int i = 0; i< num_respuestas-1; i++) {
            while (aux.contains(random1))
                random1 = rand.nextInt(num_respuestas);

            aux.add(random1);
        }

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
            opciones.addView(button);
            if (button.getText().toString() == nombres.get(0)){
                this.respuestaCorrecta=button;
            }
            botonesNotas.add(button);
        }
    }

    private void adaptaVista(Dificultad dificultad) {
        if (dificultad.equals(Dificultad.Facil)){
            Button referencia = findViewById(R.id.botonReferencia);
            referencia.setVisibility(View.GONE);
        }
        else if (dificultad.equals(Dificultad.Dificil)){
            Button referencia = findViewById(R.id.botonReferencia);
            referencia.setVisibility(View.GONE);
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

            if (Controlador.getInstance().getDificultad().equals(Dificultad.Facil)) {
                String ruta = devuelveRutaBoton(respuesta);
                MediaPlayer mediaPlayer = new MediaPlayer();
                try {
                    AssetFileDescriptor afd = getAssets().openFd(ruta);
                    Reproductor.getInstance().reproducirNota(afd);
                    afd.close();
                    /*mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                    mediaPlayer.prepare();*/
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //mediaPlayer.start();
            }

            ponerComprobarVisible(1);
        }
    }

    private String devuelveRutaBoton(String text) {
        Notas n = Notas.devuelveNotaPorNombre(text.substring(0, text.length()-1));
        Octavas o = Octavas.devuelveOctavaPorNumero(Integer.parseInt(text.substring(text.length()-1)));
        return FactoriaNotas.getInstance().getInstrumento().getPath()+o.getPath()+n.getPath();
    }

    public void reproduceNotaRespuesta(View view) throws IOException{
       // MediaPlayer mediaPlayer =  new MediaPlayer();
        AssetFileDescriptor afd = getAssets().openFd(devuelveRutaBoton(((Button)respuestaCorrecta).getText().toString()));
        Reproductor.getInstance().reproducirNota(afd);
        afd.close();
        /*mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        mediaPlayer.prepare();
        mediaPlayer.start();*/
    }

    public void volverAtras(View view){
        finish();
    }

    public void reproducirReferencia(View view) throws IOException {
       // MediaPlayer mediaPlayer =  new MediaPlayer();
        AssetFileDescriptor afd = getAssets().openFd(FactoriaNotas.getInstance().getReferencia());
        Reproductor.getInstance().reproducirNota(afd);
        afd.close();
       /* mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        mediaPlayer.prepare();
        mediaPlayer.start();*/
    }


    private void ponerComprobarVisible(int visible) {
        Button comprobar = (Button)findViewById(R.id.comprobar);
        comprobar.setVisibility(visible);
    }

    public void comprobarResultado(View view) {
        if (!comprobada) {
            NivelAdivinar nivel = null;
            this.comprobada = true;
            if (respuesta != nombres.get(0)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    botonSeleccionado.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_red_500)));
                    GestorBBDD.getInstance().actualizarPuntuacion(Controlador.getInstance().getNivel(), ModoJuego.Adivinar_Notas.toString(), false);
                    nivel = new NivelAdivinar(ModoJuego.Adivinar_Notas.toString(), Controlador.getInstance().getNivel(), false, GestorBBDD.getInstance().getUsuarioLoggeado().getCorreo(), 0 , 1);
                }
            }
            else {
                GestorBBDD.getInstance().actualizarPuntuacion(Controlador.getInstance().getNivel(), ModoJuego.Adivinar_Notas.toString(), true);
                nivel = new NivelAdivinar(ModoJuego.Adivinar_Notas.toString(), Controlador.getInstance().getNivel(), true, GestorBBDD.getInstance().getUsuarioLoggeado().getCorreo(), 1, 0);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                respuestaCorrecta.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
            }
            GestorBBDD.getInstance().insertaNivelAdivinar(nivel);
            deshabilitaBotones();
        }
        findViewById(R.id.comprobar).setVisibility(View.GONE);

    }

    private void deshabilitaBotones() {
        for (Button b : botonesNotas){
            b.setEnabled(false);
        }
        findViewById(R.id.botonReferencia).setEnabled(false);
        findViewById(R.id.botonNota).setEnabled(false);
    }


}
