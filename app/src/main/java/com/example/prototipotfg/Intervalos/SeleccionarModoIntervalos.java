package com.example.prototipotfg.Intervalos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Intervalos.Adivinar.NivelesAdivinarIntervalos;
import com.example.prototipotfg.Intervalos.Crear.SeleccionNivelCrearIntervalo;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.Controlador;

public class SeleccionarModoIntervalos extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccion_modo_intervalo);
    }

    public void modo_intervalo(View view){
        switch (view.getId()){
            case R.id.buttonIntervalosModo2:
                Intent i = new Intent(this, NivelesAdivinarIntervalos.class);
                Controlador.getInstance().setModo_juego(ModoJuego.Adivinar_Intervalo);
                startActivity(i);
                break;

            case R.id.buttonIntervalosModo1:
                i = new Intent(this, SeleccionNivelCrearIntervalo.class);
                Controlador.getInstance().setModo_juego(ModoJuego.Crear_Intervalo);
                startActivity(i);
                break;

            default: break;
        }
    }
}