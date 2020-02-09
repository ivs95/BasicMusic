package com.example.prototipotfg;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class ReproducirAdivinarIntervalo extends Activity {

    private ArrayList<String> nombres;
    private ArrayList<String> rutas;
    private ArrayList<String> intervalos;
    private String modo;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reproducir_adivinar_intervalo);
        modo = getIntent().getExtras().getString("modo");


        int nivel = getIntent().getExtras().getInt("nivel");
        TextView titulo = (TextView)findViewById(R.id.tituloAdivinar_intervalo);
        titulo.setText(titulo.getText() + String.valueOf(nivel));
        TextView nota = (TextView)findViewById(R.id.nota);
        nombres = getIntent().getExtras().getStringArrayList("nombres");
        rutas = getIntent().getExtras().getStringArrayList("rutas");
        intervalos = getIntent().getExtras().getStringArrayList("intervalos");


    }


    public void reproducir_intervalo1(View view) throws IOException {
        MediaPlayer mediaPlayer =  new MediaPlayer();
        AssetFileDescriptor afd = getAssets().openFd(rutas.get(0));
        mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        mediaPlayer.prepare();
        mediaPlayer.start();

    }

    public void reproducir_intervalo2(View view) throws IOException {
        MediaPlayer mediaPlayer =  new MediaPlayer();
        AssetFileDescriptor afd = getAssets().openFd(rutas.get(1));
        mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        mediaPlayer.prepare();
        mediaPlayer.start();

    }


    public void adivina(View view){
        Intent i = new Intent(this, SeleccionarAdivinarIntervalo.class);
        i.putStringArrayListExtra("nombres", nombres);
        i.putStringArrayListExtra("rutas", rutas);
        i.putStringArrayListExtra("intervalos", intervalos);
        startActivity(i);
    }
}
