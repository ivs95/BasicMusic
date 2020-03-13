package com.example.prototipotfg.Singletons;

import android.media.MediaPlayer;
import android.util.Pair;

import com.example.prototipotfg.Enumerados.Acordes;
import com.example.prototipotfg.Enumerados.Intervalos;
import com.example.prototipotfg.Enumerados.Notas;
import com.example.prototipotfg.Enumerados.Octavas;

import java.util.ArrayList;

public class Reproductor {
    private static final Reproductor ourInstance = new Reproductor();

    public static Reproductor getInstance() {
        return ourInstance;
    }

    private Reproductor(){}

    public void reproducirNota(Octavas octava, Notas nota){

    }

    public void reproducirIntervalo(Intervalos intervalo, Octavas octavaInicio, Notas notaInicio){

    }

    private void reproducirNotasDeIntervalo(ArrayList<Pair<Octavas,Notas>> notas){

    }


    public void reproducirAcorde(Acordes acorde, Octavas octavaInicio, Notas notaInicio){

    }

    private ArrayList<MediaPlayer> preparaMediaPlayers(ArrayList<String> rutas){
        ArrayList<MediaPlayer> retorno = new ArrayList<>();

        return retorno;
    }

    private ArrayList<String> preparaRutasAcorde(){
        ArrayList<String> retorno = new ArrayList<>();

        return retorno;
    }


}
