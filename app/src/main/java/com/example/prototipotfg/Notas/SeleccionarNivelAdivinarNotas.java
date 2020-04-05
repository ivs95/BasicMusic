package com.example.prototipotfg.Notas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.prototipotfg.Enumerados.Instrumentos;
import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Enumerados.Octavas;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.Controlador;
import com.example.prototipotfg.Singletons.FactoriaNotas;
import com.example.prototipotfg.Singletons.GestorBBDD;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class SeleccionarNivelAdivinarNotas extends Activity {

    private int puntuacion;
    Bundle savedInstanceState;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.niveles);
        this.savedInstanceState = savedInstanceState;




        LinearLayout llBotonera = (LinearLayout) findViewById(R.id.Botonera);
        //Creamos las propiedades de layout que tendrán los botones.
        //Son LinearLayout.LayoutParams porque los botones van a estar en un LinearLayout.
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //Creamos los botones en bucle
        int nivelActual = GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Adivinar_Notas.getNombre()).getNivel();
        for (int i = 0; i < 10; i++) {

            Button button = new Button(this);
            button.setId(i + 1);
            //Asignamos propiedades de layout al boton
            button.setLayoutParams(lp);
            //Asignamos Texto al botón
            button.setText("Nivel " + String.format("%02d", i + 1));

            //Asignamose el Listener
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        nivel_seleccionado(v);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            //Añadimos el botón a la botonera
            llBotonera.addView(button);

            if(nivelActual < i+1) {
                button.setEnabled(false);
                button.setAlpha(.5f);
            }


        }
    }

    public void nivel_seleccionado(View view) throws IOException {
        Intent i = new Intent(this, SeleccionarAdivinarNotas.class);
        Random random = new Random();
        //Nivel que se ha seleccionado
        Controlador.getInstance().setNivel(view.getId());
        Controlador.getInstance().estableceDificultad();
        ArrayList<Octavas> octavas = Controlador.getInstance().getOctavas();
        HashMap<String, String> notas = null;
        notas = FactoriaNotas.getInstance().getNumNotasAleatorias(Controlador.getInstance().getNum_opciones(), Instrumentos.Piano, octavas);
        ArrayList<String> nombres = new ArrayList<>(notas.keySet());
        i.putStringArrayListExtra("nombres", nombres);
        startActivity(i);
    }

    public void onResume(){
        super.onResume();
        this.onCreate(this.savedInstanceState);
    }
}