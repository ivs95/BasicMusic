package com.example.prototipotfg.Singletons;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.util.Pair;

import com.example.prototipotfg.Enumerados.Acordes;
import com.example.prototipotfg.Enumerados.Instrumentos;
import com.example.prototipotfg.Enumerados.Intervalos;
import com.example.prototipotfg.Enumerados.Notas;
import com.example.prototipotfg.Enumerados.Octavas;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

public final class Reproductor {
    private static final Reproductor INSTANCE = new Reproductor();
    private MediaPlayer reproductorNotas;
    private ArrayList<MediaPlayer> reproductoresAcordes;

    public static Reproductor getInstance() {
        return INSTANCE;
    }

    private Reproductor(){}


    public void reproducirNota(@NotNull AssetFileDescriptor afd) throws IOException {
        reproductorNotas = new MediaPlayer();
        reproductorNotas.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        reproductorNotas.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.stop();
                if (mp != null)
                    mp.reset();
                    mp.release();
            }
        });
        reproductorNotas.prepare();
        reproductorNotas.start();

    }



    public void reproducirAcorde(ArrayList<AssetFileDescriptor> afds) {
        try {
            reproductoresAcordes = preparaMediaPlayers(afds);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (MediaPlayer mp : reproductoresAcordes){
            mp.start();
        }
    }

    private ArrayList<MediaPlayer> preparaMediaPlayers(ArrayList<AssetFileDescriptor> afds) throws IOException {
        ArrayList<MediaPlayer> retorno = new ArrayList<>();
        for (AssetFileDescriptor assetFileDescriptor : afds) {
            MediaPlayer reproductor = new MediaPlayer();
            reproductor.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
            reproductor.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.stop();
                    if (mp != null)
                        mp.release();
                }
            });
            reproductor.prepare();
            retorno.add(reproductor);
        }
        return retorno;
    }
}
