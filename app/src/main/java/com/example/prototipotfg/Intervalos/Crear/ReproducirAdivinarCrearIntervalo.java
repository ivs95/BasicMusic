package com.example.prototipotfg.Intervalos.Crear;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.prototipotfg.Enumerados.Dificultad;
import com.example.prototipotfg.Enumerados.Intervalos;
import com.example.prototipotfg.Enumerados.Notas;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.Controlador;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class   ReproducirAdivinarCrearIntervalo extends Activity {

    private ArrayList<String> nombres;
    private ArrayList<String> rutas;
    private Dificultad dificultad;
    private String intervalo_nombre;
    private int intervalo_dif;

    /*
    * En rutas[0] se tiene la nota de inicio del intervalo
    * En rutas[1] se tiene la nota de final del intervalo
    * El intervalo es la diferencia de tono entre notas[0] y notas[1]
    * En notas[1..size] se tienen las distintas opciones para elegir
    * */



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reproducir_adivinar_crear_intervalo);

        int nivel = Controlador.getInstance().getNivel();

        TextView titulo = (TextView)findViewById(R.id.tituloCreaIntervalo);
        titulo.setText(titulo.getText() + String.valueOf(nivel));

        nombres = getIntent().getExtras().getStringArrayList("nombres");
        rutas = getIntent().getExtras().getStringArrayList("rutas");
        dificultad = Controlador.getInstance().getDificultad();

        Notas notaInicio = Notas.devuelveNotaPorNombre(nombres.get(0).substring(0,nombres.get(0).length()-1));
        //Notas notaFinal = Notas.devuelveNotaPorNombre(nombres.get(1).substring(0,nombres.get(1).length()-1));
        ArrayList<Intervalos> intervalos_posibles = Intervalos.devuelveIntervalosPosibles(notaInicio);

        Random r = new Random();

        Intervalos intervalo = intervalos_posibles.get(r.nextInt(Controlador.getInstance().getRango()));
        intervalo_nombre = intervalo.getNombre();
        intervalo_dif = intervalo.getDiferencia();


        TextView nota = (TextView)findViewById(R.id.lblNotaInicioIntervalo);
        nota.setText(nota.getText() + nombres.get(0));

        TextView peticion = (TextView)findViewById(R.id.lblNombreIntervalo);
        peticion.setText(peticion.getText() + intervalo_nombre);

        if(Controlador.getInstance().getDificultad().equals(Dificultad.Dificil))
            this.adaptaVistaDificil();
    }


    public void reproducir(View view) throws IOException {
        MediaPlayer mediaPlayer =  new MediaPlayer();
        AssetFileDescriptor afd = getAssets().openFd(rutas.get(0));
        mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        mediaPlayer.prepare();
        mediaPlayer.start();

    }

    private void adaptaVistaDificil() {
        TextView nota = findViewById(R.id.lblNotaInicioIntervalo);
        nota.setVisibility(View.GONE);
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
