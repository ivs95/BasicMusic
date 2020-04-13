package com.example.prototipotfg.Intervalos.Crear;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.prototipotfg.Enumerados.Instrumentos;
import com.example.prototipotfg.Enumerados.Intervalos;
import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Enumerados.Octavas;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.Controlador;
import com.example.prototipotfg.Singletons.FactoriaNotas;
import com.example.prototipotfg.Singletons.GestorBBDD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class SeleccionNivelCrearIntervalo extends Activity {
    private Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.niveles);
        this.savedInstanceState = savedInstanceState;
        boolean primeraVez = getIntent().getExtras().getBoolean("visitado");

        //Obtenemos el linear layout donde colocar los botones
        LinearLayout llBotonera = (LinearLayout) findViewById(R.id.Botonera);
        TextView rango = findViewById(R.id.rango_niveles);
        rango.setText(GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Crear_Intervalo.toString()).getRango());

        //Creamos las propiedades de layout que tendrán los botones.
        //Son LinearLayout.LayoutParams porque los botones van a estar en un LinearLayout.
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        int nivelActual = GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Crear_Intervalo.toString()).getNivel();

        //Creamos los botones en bucle
        for (int i = 0; i < 8; i++) {
            Button button = new Button(this);
            button.setId(i + 1);
            //Asignamos propiedades de layout al boton
            button.setLayoutParams(lp);
            //Asignamos Texto al botón
            button.setText("Nivel " + (i + 1));

            //Asignamose el Listener
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nivel_seleccionado(v);
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

    public void nivel_seleccionado(View view) {
        Intent i = new Intent(this, SeleccionarAdivinarCrearIntervalo.class);
        Random random = new Random();
        Controlador.getInstance().setNivel(view.getId());
        Controlador.getInstance().estableceDificultad();


        //Nivel que se ha seleccionado
        ArrayList<Octavas> octavas = Controlador.getInstance().getOctavas();
        ArrayList<Intervalos> intervalos = Controlador.getInstance().getIntervalos();
        HashMap<String, String> notas = null;

            Octavas octava_intervalos = octavas.get(random.nextInt(octavas.size()));
            notas = FactoriaNotas.getInstance().getNumNotasAleatorias(Controlador.getInstance().getNum_opciones()+1, Instrumentos.Piano, octava_intervalos);


        ArrayList<String> nombres = new ArrayList<>(notas.keySet());
        ArrayList<String> rutas = new ArrayList<>(notas.values());

        i.putStringArrayListExtra("nombres", nombres);
        i.putStringArrayListExtra("rutas", rutas);

        startActivity(i);
    }

    public void onResume(){
        super.onResume();
        this.onCreate(this.savedInstanceState);
    }
}