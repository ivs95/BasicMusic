package com.example.prototipotfg.Intervalos.Adivinar;

import android.app.Activity;
import android.bluetooth.BluetoothGattServer;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.prototipotfg.Enumerados.Acordes;
import com.example.prototipotfg.Enumerados.Instrumentos;
import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Enumerados.Octavas;
import com.example.prototipotfg.Enumerados.PuntosNiveles;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.Controlador;
import com.example.prototipotfg.Singletons.FactoriaNotas;
import com.example.prototipotfg.Singletons.GestorBBDD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class NivelesAdivinarIntervalos extends Activity {

    private  Bundle savedInstanceState;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.niveles);
        this.savedInstanceState = savedInstanceState;
        boolean primeraVez = getIntent().getExtras().getBoolean("visitado");

        //Obtenemos el linear layout donde colocar los botones
        LinearLayout llBotonera = (LinearLayout) findViewById(R.id.Botonera);
        TextView rango = findViewById(R.id.rango_niveles);
        rango.setText(GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Adivinar_Intervalo.toString()).getRango());

        //Creamos las propiedades de layout que tendrán los botones.
        //Son LinearLayout.LayoutParams porque los botones van a estar en un LinearLayout.
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        int nivelActual = GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Adivinar_Intervalo.toString()).getNivel();

        //Creamos los botones en bucle
        for (int i=0; i<6; i++){
            Button button = new Button(this);
            button.setId(i+1);
            //Asignamos propiedades de layout al boton
            button.setLayoutParams(lp);
            //Asignamos Texto al botón
            button.setText("Nivel " + (i+1));

            //Asignamose el Listener
            if (nivelActual > i) {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nivel_seleccionado(v);
                    }
                });
            }
            else{
                button.setEnabled(false);
                button.setAlpha(.5f);
            }

            //Añadimos el botón a la botonera
            llBotonera.addView(button);

            if (nivelActual == i+1 && nivelActual != ModoJuego.Adivinar_Intervalo.getMax_level()){
                TextView texto = new TextView(this);
                texto.setText("Faltan " + (PuntosNiveles.values()[nivelActual].getMinPuntos() - GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Adivinar_Intervalo.toString()).getPuntuacionTotal()) +" puntos para desbloquear el siguiente nivel");
                texto.setLayoutParams(lp);
                texto.setTextColor(getResources().getColor(R.color.md_blue_900));
                texto.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                llBotonera.addView(texto);
            }
        }
    }


    public void nivel_seleccionado(View view) {
        Intent i = new Intent(this, SeleccionarAdivinarIntervalo.class);
        //Nivel que se ha seleccionado
        Controlador.getInstance().setNivel(view.getId());
        Controlador.getInstance().estableceDificultad();
        startActivity(i);
    }

    public void onResume(){
        super.onResume();
        this.onCreate(this.savedInstanceState);
    }

}
