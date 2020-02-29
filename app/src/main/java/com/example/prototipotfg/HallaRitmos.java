package com.example.prototipotfg;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.example.prototipotfg.Enumerados.DuracionSonido;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Thread.sleep;

public class HallaRitmos extends Activity {

    private ArrayList<Integer> ritmos;
    private Thread hilo_ritmos;
    private boolean running;
    private boolean go = false;
    private int bpm = 60;
    private Metronomo m = new Metronomo(bpm, 4);

    private MediaPlayerRitmos mediaPlayer =  new MediaPlayerRitmos();
    private Thread hiloPlayer = new Thread(new Runnable() {

        @Override
        public void run() {
            try {
                while(running) {
                    if (go) {
                        mediaPlayer.play();
                        go = false;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hallaritmos);


        int nivel = getIntent().getExtras().getInt("nivel");
        ritmos = getIntent().getExtras().getIntegerArrayList("ritmos");
        TextView titulo = (TextView)findViewById(R.id.tituloHallaRitmo);
        titulo.setText(titulo.getText() + String.valueOf(nivel));
        running = true;
        hiloPlayer.start();
        mediaPlayer.init(this);


    }


    public void play(@NotNull final View view){

        //view.setEnabled(false);
        try {
            m.init(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        hilo_ritmos = new Thread(new Runnable(){
            @Override
            public void run(){
                Metronomo m = new Metronomo(bpm, 4);
                m.start();
                try {
                    sleep((long) (1000 * (60.0 / bpm)));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int i = 0;
                int indice = 0;
                ;
                while(i < 32 && running){
                    //Lanzar todo lo del media player en un nuevo hilo y a dormir
                    int notaActual = ritmos.get(indice);

                    if(notaActual==1) {
                        go=true;
                    }
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    i++;
                    indice++;
                    if (indice >= 16)
                        indice = indice-16;
                    if(!running) {
                        m.stop();
                    }

                }
                m.stop();
            }

        });
        hilo_ritmos.start();


    }

    public void stop(View view){
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        running = false;
    }


}
