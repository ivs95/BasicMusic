package com.example.prototipotfg.Intervalos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Enumerados.RangosPuntuaciones;
import com.example.prototipotfg.Intervalos.Adivinar.NivelesAdivinarIntervalos;
import com.example.prototipotfg.Intervalos.Crear.SeleccionNivelCrearIntervalo;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.Controlador;
import com.example.prototipotfg.Singletons.GestorBBDD;

public class SeleccionarModoIntervalos extends Activity {

    private Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccion_modo_intervalo);
        this.savedInstanceState = savedInstanceState;

        ImageView viewRangoAdivinar = findViewById(R.id.imageViewIntervalo1);   viewRangoAdivinar.setBackgroundResource(RangosPuntuaciones.getRangoPorNombre(GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Adivinar_Intervalo.toString()).getRango()).getImage());
        ImageView viewRangoCrear = findViewById(R.id.imageViewIntervalo2);      viewRangoCrear.setBackgroundResource(RangosPuntuaciones.getRangoPorNombre(GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Crear_Intervalo.toString()).getRango()).getImage());

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

    public void tutorialIntervalos(View view){
        Intent i = new Intent(this, TutorialIntervalos.class);
        startActivity(i);
    }

    public void onResume(){
        super.onResume();
        this.onCreate(this.savedInstanceState);
    }
}
