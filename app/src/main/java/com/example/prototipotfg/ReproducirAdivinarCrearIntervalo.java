package com.example.prototipotfg;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ReproducirAdivinarCrearIntervalo extends Activity {

    private ArrayList<String> nombres;
    private ArrayList<String> rutas;
    private String dificultad;
    private String intervalo_nombre;
    private int intervalo_dif;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reproducir_adivinar_crear_intervalo);

        int nivel = getIntent().getExtras().getInt("nivel");

        TextView titulo = (TextView)findViewById(R.id.tituloAdivinar);
        titulo.setText(titulo.getText() + String.valueOf(nivel));

        nombres = getIntent().getExtras().getStringArrayList("nombres");
        rutas = getIntent().getExtras().getStringArrayList("rutas");
        dificultad = getIntent().getExtras().getString("dificultad");

        Notas n = Notas.devuelveNotaPorNombre(nombres.get(0));
        ArrayList<Intervalos> intervalos_posibles = Intervalos.devuelveIntervalosPosibles(n);
        Intervalos intervalo = intervalos_posibles.get(new Random().nextInt(intervalos_posibles.size()));
        intervalo_nombre = intervalo.getNombre();
        intervalo_dif = intervalo.getDiferencia();


        TextView nota = (TextView)findViewById(R.id.peticion_intervalo_id4);
        nota.setText(n.getNombre());

        TextView peticion = (TextView)findViewById(R.id.peticion_intervalo_id2);
        peticion.setText(intervalo_nombre);




    }


    public void reproducir(View view) throws IOException {
        MediaPlayer mediaPlayer =  new MediaPlayer();
        AssetFileDescriptor afd = getAssets().openFd(rutas.get(0));
        mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        mediaPlayer.prepare();
        mediaPlayer.start();

    }

    public void adivina(View view){
        Intent i = new Intent(this, SeleccionarAdivinarCrearIntervalo.class);
        i.putStringArrayListExtra("nombres", nombres);
        i.putStringArrayListExtra("rutas", rutas);
        i.putExtra("dificultad", dificultad);
        i.putExtra("peticion_nombre", intervalo_nombre);
        i.putExtra("peticion_dif", intervalo_dif);

        startActivity(i);
    }


}
