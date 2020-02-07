package com.example.prototipotfg;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Random;

import static android.view.View.INVISIBLE;

public class SeleccionarAdivinarIntervalo extends Activity {
    private View botonSeleccionado;
    private View respuestaCorrecta;

    private ArrayList<String> nombres;
    private ArrayList<String> rutas;

    private String respuesta;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nivel_seleccionar_adivinar);
        ponerComprobarVisible(INVISIBLE);
        nombres = getIntent().getExtras().getStringArrayList("nombres");
        rutas = getIntent().getExtras().getStringArrayList("rutas");

        //inicializacion de botones



        //Obtenemos el linear layout donde colocar los botones
        LinearLayout opciones = (LinearLayout) findViewById(R.id.opciones);

        //Creamos las propiedades de layout que tendrán los botones.
        //Son LinearLayout.LayoutParams porque los botones van a estar en un LinearLayout.
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT );
        lp.setMargins(0,0,0,50);
        Random rand = new Random();

        int num_respuestas = nombres.size();

        int random1 = rand.nextInt(num_respuestas);
        ArrayList <Integer> aux = new ArrayList<Integer>();
        aux.add(random1);


        for(int i = 0; i< num_respuestas-1; i++) {
            while (aux.contains(random1))
                random1 = rand.nextInt(num_respuestas);

            aux.add(random1);
        }




        //Creamos los botones en bucle
        for (int i=0; i<num_respuestas; i++){
            Button button = new Button(this);
            button.setId(i+1);
            //Asignamos propiedades de layout al boton
            button.setLayoutParams(lp);
            //Asignamos Texto al botón
            button.setText(nombres.get(aux.get(i)));

            //Asignamose el Listener
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    respuesta_seleccionada(v);
                }
            });
            //Añadimos el botón a la botonera
            button.setPadding(0,0,0,0);

            opciones.addView(button);
        }


    }


    public void respuesta_seleccionada(View view){
        Button b = (Button)view;
        if (botonSeleccionado != null){
            botonSeleccionado.setBackgroundColor(ContextCompat.getColor(this, R.color.md_orange_400));
        }
        botonSeleccionado = b;
        botonSeleccionado.setBackgroundColor(ContextCompat.getColor(this, R.color.md_deep_orange_900));

        respuesta = b.getText().toString();
        ponerComprobarVisible(1);
    }


    public void volverAtras(View view){
        finish();
    }



    private void ponerComprobarVisible(int visible) {
        Button comprobar = (Button)findViewById(R.id.comprobar);
        comprobar.setVisibility(visible);
    }

    public void comprobarResultado(View view){
        TextView text = (TextView)findViewById(R.id.respuesta_correcta_id);
        if(respuesta == nombres.get(0)){
            text.setText("RESPUESTA CORRECTA");

        }
        else
            text.setText("RESPUESTA INCORRECTA");
    }
}
