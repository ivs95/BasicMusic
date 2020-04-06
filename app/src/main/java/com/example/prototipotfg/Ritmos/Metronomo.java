package com.example.prototipotfg.Ritmos;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.IOException;

import static java.lang.Thread.sleep;


class Metronomo{
    private static double bpm;
    private static int measure;
    private static MediaPlayer mediaPlayer1 =  new MediaPlayer();
    private static MediaPlayer mediaPlayer2 =  new MediaPlayer();
    private boolean running = false;
    private Runnable aux = new Runnable() {
        @Override
        public void run() {
            int counter=0;
            while (running) {
                try {
                    sleep((long) (1000 * (60.0 / bpm)));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                counter++;
                if (counter % measure == 1) {
                    mediaPlayer1.start();
                } else {
                    mediaPlayer2.start();
                }
            }
        }
    };
    private Thread hiloMetronomo;

    public Metronomo(double bpm, int measure){
        this.bpm = bpm;
        this.measure = measure;
    }

    public void init(Context myContext) throws IOException {
        AssetFileDescriptor afd1 = myContext.getAssets().openFd("metronomo/tick.wav");
        AssetFileDescriptor afd2 = myContext.getAssets().openFd("metronomo/tock.wav");
        mediaPlayer1.setDataSource(afd1.getFileDescriptor(), afd1.getStartOffset(), afd1.getLength());
        mediaPlayer2.setDataSource(afd2.getFileDescriptor(), afd2.getStartOffset(), afd2.getLength());
        mediaPlayer1.prepare();
        mediaPlayer2.prepare();
    }
    public void start(){
        hiloMetronomo = new Thread(aux);
        running = true;
        hiloMetronomo.start();
    }

    public void stop() {
        running = false;
        mediaPlayer1.stop();
        mediaPlayer2.stop();
        mediaPlayer1.reset();
        mediaPlayer2.reset();

    }
    public void reinicia() throws IOException {
        this.start();
    }
    public void para(){
        running = false;
        mediaPlayer1.pause();
        mediaPlayer2.pause();
    }

    public void release(){
        mediaPlayer1.release();
        mediaPlayer2.release();
    }

}
