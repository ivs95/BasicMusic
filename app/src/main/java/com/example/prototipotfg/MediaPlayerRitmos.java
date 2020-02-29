package com.example.prototipotfg;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.IOException;

public class MediaPlayerRitmos {
    private MediaPlayer player = new MediaPlayer();
    public void init(Context myContext){

        AssetFileDescriptor afd = null;
        try {
            afd = myContext.getAssets().openFd("metronomo/Clap.wav");
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        try {
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void play() throws IOException {
        if (this.player.isPlaying()) {
            player.stop();
            player.prepare();
        }
        player.start();
    }

    public void stop(){
        player.stop();
        player.release();
    }
}

