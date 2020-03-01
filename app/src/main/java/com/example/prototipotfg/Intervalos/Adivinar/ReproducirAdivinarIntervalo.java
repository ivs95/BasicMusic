package com.example.prototipotfg.Intervalos.Adivinar;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.Controlador;

import java.io.IOException;
import java.util.ArrayList;

public class ReproducirAdivinarIntervalo extends Activity {

    private ArrayList<String> nombres;
    private ArrayList<String> rutas;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reproducir_adivinar_intervalo);
        TextView titulo = (TextView)findViewById(R.id.tituloAdivinar_intervalo);
        titulo.setText(titulo.getText() + Integer.toString(Controlador.getInstance().getNivel()));
        nombres = getIntent().getExtras().getStringArrayList("nombres");
        rutas = getIntent().getExtras().getStringArrayList("rutas");
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
        startActivity(i);
    }
}
