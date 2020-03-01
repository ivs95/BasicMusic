package com.example.prototipotfg.Acordes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.Controlador;

public class SeleccionarModoAcordes extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccion_modo_acordes);
    }

    public void modo_acordes(View view){
        switch (view.getId()){
            case R.id.buttonAcordesModo1:
                Intent i = new Intent(this, SeleccionarNivelAdivinarAcordes.class);
                Controlador.getInstance().setModo_juego(ModoJuego.Adivinar_Acordes);
                startActivity(i);
                break;

            case R.id.buttonAcordesModo2:
                i = new Intent(this, SeleccionarNivelCrearAcordes.class);
                Controlador.getInstance().setModo_juego(ModoJuego.Crear_Acordes);
                startActivity(i);
                break;

            default: break;
        }
    }
}
