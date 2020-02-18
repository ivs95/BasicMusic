package com.example.prototipotfg;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;


import java.io.IOException;


class Metronomo{
    static double bpm;
    static int measure;
    static int counter;
    public Metronomo(double bpm, int measure){
        this.bpm = bpm;
        this.measure = measure;
    }
    public static void start(Context myContext) throws IOException {

        MediaPlayer mediaPlayer =  new MediaPlayer();
        AssetFileDescriptor afd1 = myContext.getAssets().openFd("metronomo/tick.wav");
        AssetFileDescriptor afd2 = myContext.getAssets().openFd("metronomo/tock.wav");


        while(true){
            try {
                Thread.sleep((long)(1000*(60.0/bpm)));
            }catch(InterruptedException e) {
                e.printStackTrace();
            }
            counter++;
            if (counter%measure==0){
                mediaPlayer.setDataSource(afd1.getFileDescriptor(), afd1.getStartOffset(), afd1.getLength());
                mediaPlayer.prepare();
                mediaPlayer.start();
                System.out.println("TICK");
            }else{
                mediaPlayer.setDataSource(afd2.getFileDescriptor(), afd2.getStartOffset(), afd2.getLength());
                mediaPlayer.prepare();
                mediaPlayer.start();
                System.out.println("TOCK");
            }
        }
    }

}
