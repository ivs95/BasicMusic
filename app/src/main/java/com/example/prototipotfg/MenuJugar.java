package com.example.prototipotfg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.ImitarAudio.SeleccionOctavasImitar;
import com.example.prototipotfg.Notas.SeleccionOctavasNotas;
import com.example.prototipotfg.Singletons.Controlador;

public class MenuJugar extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_jugar);
    }

    public void modo_adivinar(View view){
        Controlador.getInstance().setModo_juego(ModoJuego.Adivinar);
        Intent i = new Intent(this, SeleccionModoAdivinar.class);
        startActivity(i);
    }

    public void modo_imitar(View view){
        Controlador.getInstance().setModo_juego(ModoJuego.Imitar);
        Intent i = new Intent(this, SeleccionOctavasImitar.class);
        startActivity(i);
    }

    public void modo_ritmos(View view) {
        Controlador.getInstance().setModo_juego(ModoJuego.Ritmos);
        Intent i = new Intent(this, SeleccionModoRitmos.class);
        startActivity(i);
    }
}
