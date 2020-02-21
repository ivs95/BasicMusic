package com.example.prototipotfg;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;


import java.io.IOException;


class Metronomo{
    private static double bpm;
    private static int measure;
    private static MediaPlayer mediaPlayer1 =  new MediaPlayer();
    private static MediaPlayer mediaPlayer2 =  new MediaPlayer();
    private static Runnable aux = new Runnable() {
        @Override
        public void run() {
            int counter=0;
            while(!hiloMetronomo.isInterrupted()){
                try {
                    Thread.sleep((long)(1000*(60.0/bpm)));
                }catch(InterruptedException e) {
                    e.printStackTrace();
                }
                counter++;
                if (counter%measure==0){
                    mediaPlayer1.start();
                }else{
                    mediaPlayer2.start();
                }
            }
        }
    };
    private static Thread hiloMetronomo;

    public Metronomo(double bpm, int measure){
        this.bpm = bpm;
        this.measure = measure;
    }
    public static void start(Context myContext) throws IOException {

        AssetFileDescriptor afd1 = myContext.getAssets().openFd("metronomo/tick.wav");
        AssetFileDescriptor afd2 = myContext.getAssets().openFd("metronomo/tock.wav");
        mediaPlayer1.setDataSource(afd1.getFileDescriptor(), afd1.getStartOffset(), afd1.getLength());
        mediaPlayer2.setDataSource(afd2.getFileDescriptor(), afd2.getStartOffset(), afd2.getLength());
        mediaPlayer1.prepare();
        mediaPlayer2.prepare();
        hiloMetronomo= new Thread(aux);
        hiloMetronomo.start();

    }

    public void stop(){
        mediaPlayer1.stop();
        mediaPlayer2.stop();
        hiloMetronomo.interrupt();
    }

}
