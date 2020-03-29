package com.example.prototipotfg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.prototipotfg.Acordes.SeleccionarModoAcordes;
import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Notas.SeleccionarNivelAdivinarNotas;
import com.example.prototipotfg.Singletons.Controlador;

public class SeleccionModoAdivinar extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccion_adivinar);
    }

    public void modo(View view){
        Intent i;
        switch (view.getId()){
            case R.id.buttonNotas:
                Controlador.getInstance().setModo_juego(ModoJuego.Adivinar_Notas);
                seleccionarDificultadNotas();
                break;

            case R.id.buttonAcordes:
                Controlador.getInstance().setModo_juego(ModoJuego.Adivinar_Acordes);
                seleccionarModoAcordes();
                break;

            case R.id.buttonRealizar:
                seleccionarModoIntervalos();
                break;

            default: break;
        }
    }

    private void seleccionarModoAcordes() {
        Intent i = new Intent(this, SeleccionarModoAcordes.class);
        startActivity(i);
    }

    private void seleccionarModoIntervalos() {
        Intent i = new Intent(this, SeleccionarModoIntervalos.class);
        startActivity(i);
    }

    private void seleccionarDificultadNotas() {
        Intent i = new Intent(this, SeleccionarNivelAdivinarNotas.class);
        startActivity(i);
    }

}
