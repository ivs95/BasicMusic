package com.example.prototipotfg;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Thread.sleep;

public class HallaRitmos extends Activity {

    private ArrayList<Integer> ritmos;
    private Thread hilo_ritmos;
    private boolean running;

    private int bpm = 60;
    private Metronomo m = new Metronomo(bpm, 4);


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hallaritmos);


        int nivel = getIntent().getExtras().getInt("nivel");
        ritmos = getIntent().getExtras().getIntegerArrayList("ritmos");
        TextView titulo = (TextView)findViewById(R.id.tituloHallaRitmo);
        titulo.setText(titulo.getText() + String.valueOf(nivel));
        running = true;


    }


    public void play(final View view) throws IOException, InterruptedException {

        view.setEnabled(false);
        m.init(this);
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
                while(i < ritmos.size() && running){
                    MediaPlayer mediaPlayer =  new MediaPlayer();
                    AssetFileDescriptor afd = null;
                    try {
                        afd = getAssets().openFd("metronomo/Clap.wav");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(ritmos.get(i) == 1) {

                        try {
                            mediaPlayer.prepare();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        mediaPlayer.start();
                    }
                    try {
                        sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(mediaPlayer.isPlaying()){
                        mediaPlayer.stop();
                        mediaPlayer.release();
                    }
                    i++;
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
