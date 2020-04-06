package com.example.prototipotfg.Ritmos.Crear;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.prototipotfg.BBDD.NivelAdivinar;
import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Ritmos.MediaPlayerRitmos;
import com.example.prototipotfg.Singletons.Controlador;
import com.example.prototipotfg.Singletons.GestorBBDD;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

public class CrearRitmos extends Activity {

    private ArrayList<Integer> ritmos1;
    private ArrayList<Integer> ritmos2;
    private ArrayList<Integer> ritmos3;
    private ArrayList<Integer> ritmos4;
    private ArrayList<Integer> resultado1 = new ArrayList<>();
    private ArrayList<Integer> resultado2 = new ArrayList<>();
    private ArrayList<Integer> resultado3 = new ArrayList<>();
    private ArrayList<Integer> resultado4 = new ArrayList<>();
    private ArrayList<Integer> metronomo = new ArrayList<>();
    private ArrayList<Button> botonesGuia = new ArrayList<>();
    private final int COMPASES = 16;
    private final int BPM = 60;
    private boolean running;
    private boolean go1 = false;
    private boolean go2 = false;
    private boolean go3 = false;
    private boolean go4 = false;
    private boolean goMetronomo = false;
    private boolean tick = false;
    private boolean end = false;
    private boolean play = false;
    private boolean pause = false;
    private int nivel;
    private int indice = 0;
    private MediaPlayerRitmos mediaPlayer1 =  new MediaPlayerRitmos();
    private MediaPlayerRitmos mediaPlayer2 =  new MediaPlayerRitmos();
    private MediaPlayerRitmos mediaPlayer3 =  new MediaPlayerRitmos();
    private MediaPlayerRitmos mediaPlayer4 =  new MediaPlayerRitmos();
    private MediaPlayerRitmos mediaPlayerMetronomo =  new MediaPlayerRitmos();

    private  Thread hilo_ritmos = new Thread(new Runnable(){
        @Override
        public void run() {
            while(!end) {
                while (running) {

                    play = false;
                    if (metronomo.get(indice) == 1) {
                        if (indice == 0)
                            tick = true;
                        goMetronomo = true;
                    }
                    int notaActual1 = ritmos1.get(indice);
                    if (notaActual1 == 1) {
                        go1 = true;
                    }
                    if (nivel > 2) {
                        int notaActual2 = ritmos2.get(indice);
                        if (notaActual2 == 1) {
                            go2 = true;
                        }
                        if (nivel > 4) {
                            int notaActual3 = ritmos3.get(indice);
                            if (notaActual3 == 1) {
                                go3 = true;
                            }
                            if (nivel > 6) {
                                int notaActual4 = ritmos4.get(indice);
                                if (notaActual4 == 1) {
                                    go4 = true;
                                }
                            }
                        }
                    }
                    try {
                        Thread.sleep(250);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (!play && running) {
                        botonesGuia.get(indice).setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.md_lime_A100)));
                        if (indice == 0){
                            botonesGuia.get(botonesGuia.size()-1).setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.md_blue_300)));
                        }
                        else{
                            botonesGuia.get(indice-1).setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.md_blue_300)));
                        }
                        indice++;
                    }
                    if (indice >= COMPASES)
                        indice = 0;
                }
            }
        }

    });

    private Thread hiloPlayer1 = new Thread(new Runnable() {

        @Override
        public void run() {
            try {
                while(!end) {
                    while (running) {

                        if (go1) {
                            mediaPlayer1.play();
                            go1 = false;
                        }
                        if (go2) {
                            mediaPlayer2.play();
                            go2 = false;
                        }
                        if (go3) {
                            mediaPlayer3.play();
                            go3 = false;
                        }
                        if (go4) {
                            mediaPlayer4.play();
                            go4 = false;
                        }
                        if (goMetronomo) {
                            mediaPlayerMetronomo.playMetronomo(tick);
                            goMetronomo = false;
                            tick = false;
                        }
                    }
                }
                botonesGuia.get(indice).setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.md_blue_300)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState){

        final Context contexto = this;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.crearritmos);


        nivel = getIntent().getExtras().getInt("nivel");
        ritmos1 = getIntent().getExtras().getIntegerArrayList("ritmos1");
        ritmos2 = getIntent().getExtras().getIntegerArrayList("ritmos2");
        ritmos3 = getIntent().getExtras().getIntegerArrayList("ritmos3");
        ritmos4 = getIntent().getExtras().getIntegerArrayList("ritmos4");

        TextView titulo = findViewById(R.id.tituloCrearRitmo);
        titulo.setText(titulo.getText() + String.valueOf(nivel));
        LinearLayout guia = findViewById(R.id.linearRitmo);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,1 );
        running = false;
        hilo_ritmos.start();
        hiloPlayer1.start();
        mediaPlayerMetronomo.initMetronomo(this);
        mediaPlayer1.init1(this);
        if(nivel>2) {
            mediaPlayer2.init2(this);
            findViewById(R.id.botonCaja).setVisibility(View.VISIBLE);
            if(nivel>4) {
                mediaPlayer3.init3(this);
                findViewById(R.id.botonTambor).setVisibility(View.VISIBLE);
                if(nivel > 6) {
                    mediaPlayer4.init4(this);
                    findViewById(R.id.botonPlatillo).setVisibility(View.VISIBLE);
                }
            }
        }
        for (int i = 0; i < COMPASES; i++){
            resultado1.add(0);
            resultado2.add(0);
            resultado3.add(0);
            resultado4.add(0);
            if(i%4==0){
                metronomo.add(i,1);
            }
            else{
                metronomo.add(i,0);
            }

            Button button = new Button(this);
            button.setEnabled(false);
            button.setText("");
            button.setLayoutParams(lp);
            guia.addView(button);
            botonesGuia.add(button);
        }
    }

    public void play(@NotNull final View view){
        if(running == true){
            botonesGuia.get(indice-1).setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.md_blue_300)));
            indice = 0;
            play = true;
        }
        else {
            running = true;
            if(pause == false)
                indice = 0;
            pause = false;
        }
    }


    private void pintaBotonAmarillo(int indice){

    }

    private void pintaBotonAzul(int indice){

    }


    public void para(@NotNull final View view){
        running = false;
        botonesGuia.get(indice-1).setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.md_blue_300)));

    }

    public void comprobar(View view){

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        running = false;
        end = true;
        hiloPlayer1.interrupt();
        mediaPlayer1.stop();
        mediaPlayer2.stop();
        mediaPlayer3.stop();
        mediaPlayer4.stop();
        mediaPlayerMetronomo.stopMetronomo();
    }



    public void registraCaja(View view){

    }
    public void registraPlatillo(View view){

    }
    public void registraTambor(View view){

    }
    public void registraPalmada(View view){

    }
}
