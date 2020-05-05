package com.example.prototipotfg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Enumerados.RangosPuntuaciones;
import com.example.prototipotfg.Examen.SeleccionNivelExamen;
import com.example.prototipotfg.ImitarAudio.SeleccionOctavasImitar;
import com.example.prototipotfg.Ritmos.SeleccionModoRitmos;
import com.example.prototipotfg.Singletons.Controlador;
import com.example.prototipotfg.Singletons.GestorBBDD;

public class MenuJugar extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_jugar);

        ImageView viewRangoImitar = findViewById(R.id.imageViewImitar);
        viewRangoImitar.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        viewRangoImitar.setImageResource(RangosPuntuaciones.getRangoPorNombre(GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Modo_Mix.toString()).getRango()).getImage());

        ImageView viewRangoExamen = findViewById(R.id.imageViewExamen);
        viewRangoExamen.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        viewRangoExamen.setImageResource(RangosPuntuaciones.getRangoPorNombre(GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Modo_Mix.toString()).getRango()).getImage());
    }

    public void modo_adivinar(View view){
        Intent i = new Intent(this, SeleccionModoAdivinar.class);
        startActivity(i);
    }

    public void modo_imitar(View view){
        Controlador.getInstance().setModo_juego(ModoJuego.Imitar_Audio);
        Intent i = new Intent(this, SeleccionOctavasImitar.class);
        startActivity(i);
    }

    public void modo_ritmos(View view) {
        Intent i = new Intent(this, SeleccionModoRitmos.class);
        startActivity(i);
    }

    public void examen(View view){
        Intent i = new Intent(this, SeleccionNivelExamen.class);
        startActivity(i);
    }


}
