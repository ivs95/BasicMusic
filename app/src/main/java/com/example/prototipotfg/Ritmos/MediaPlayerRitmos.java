package com.example.prototipotfg.Ritmos;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.IOException;

public class MediaPlayerRitmos {
    private MediaPlayer player = new MediaPlayer();
    private MediaPlayer player2 = new MediaPlayer();

    public void init1(Context myContext) {

        AssetFileDescriptor afd = null;
        try {
            afd = myContext.getAssets().openFd("metronomo/Clap.wav");
        } catch (IOException e) {
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

    public void init2(Context myContext) {

        AssetFileDescriptor afd = null;
        try {
            afd = myContext.getAssets().openFd("metronomo/Caja.wav");
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

    public void init3(Context myContext) {

        AssetFileDescriptor afd = null;
        try {
            afd = myContext.getAssets().openFd("metronomo/Tambor.wav");
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

    public void init4(Context myContext) {

        AssetFileDescriptor afd = null;
        try {
            afd = myContext.getAssets().openFd("metronomo/Platillo.wav");
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

    public void initMetronomo(Context myContext) {
        AssetFileDescriptor afd1 = null;
        AssetFileDescriptor afd2 = null;
        try {
            afd1 = myContext.getAssets().openFd("metronomo/tick.wav");
            afd2 = myContext.getAssets().openFd("metronomo/tock.wav");
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        try {
            player.setDataSource(afd1.getFileDescriptor(), afd1.getStartOffset(), afd1.getLength());
            player2.setDataSource(afd2.getFileDescriptor(), afd2.getStartOffset(), afd2.getLength());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            player.prepare();
            player2.prepare();
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

    public void playMetronomo(boolean tick) throws IOException {
        if (tick == true) {
            if (this.player.isPlaying()) {
                player.stop();
                player.prepare();
            }
            player.start();
        } else {
            if (this.player2.isPlaying()) {
                player2.stop();
                player2.prepare();
            }
            player2.start();
        }
    }

    public void stop() {
        player.stop();
        player.reset();
        player.release();
        player2.release();
    }

    public void stopMetronomo() {
        player.stop();
        player2.stop();
        player.reset();
        player2.reset();
        player.release();
        player2.release();
    }
}

