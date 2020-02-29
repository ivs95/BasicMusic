package com.example.prototipotfg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.prototipotfg.Enumerados.Instrumentos;
import com.example.prototipotfg.Enumerados.Octavas;
import com.example.prototipotfg.ImitarAudio.ReproducirImitar;
import com.example.prototipotfg.Singletons.FactoriaNotas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static com.example.prototipotfg.Enumerados.DuracionSonido.getSonidoPorSimbolo;

public class NivelesRitmos extends Activity {


    private int compas = 4;
    private int num = 4;
    private int longitud = compas*num;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.niveles);

        //Obtenemos el linear layout donde colocar los botones
        LinearLayout llBotonera = (LinearLayout) findViewById(R.id.Botonera);

        //Creamos las propiedades de layout que tendrán los botones.
        //Son LinearLayout.LayoutParams porque los botones van a estar en un LinearLayout.
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT );

        //Creamos los botones en bucle
        for (int i=0; i<15; i++){
            Button button = new Button(this);
            button.setId(i+1);
            //Asignamos propiedades de layout al boton
            button.setLayoutParams(lp);
            //Asignamos Texto al botón
            button.setText("Nivel "+String.format("%02d", i+1 ));

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
        }
    }

    public void nivel_seleccionado(View view) throws IOException {
        Intent i = new Intent(this, HallaRitmos.class);
        int nivel = view.getId();
        /*
         * Aquí hay que seleccionar la nota y las variables (strings de los nombre) y meterlas en el bundle
         * Crear clase para seleccionar notas aleatorias
         * Claves: respuesta, fallo1,...,falloN
         * */
        Random random = new Random();
        ArrayList<Integer> ritmos = new ArrayList<>(longitud);
        int nota = random.nextInt(3)+1;
        //Llenar aleatorios


        for(int j=getSonidoPorSimbolo(nota).getSilencio(); j<=longitud; j+=getSonidoPorSimbolo(nota).getSilencio()){
            agregaFigura(nota, ritmos, compas);
            if(longitud-j >= 4)
                nota= random.nextInt(4);
            else if(longitud-j == 3 || longitud-j == 2)
                nota= random.nextInt(2)+2;
            else if (longitud-j==1)
                nota= 3;
        }



        i.putExtra("nivel", nivel);
        i.putIntegerArrayListExtra("ritmos", ritmos);
        //i.putExtra("dificultad", getIntent().getExtras().getString("dificultad"));

        startActivity(i);
    }

    public void agregaFigura(int figura, ArrayList<Integer> ritmos, int compas){
        if(figura == 1){
            ritmos.add(1);
            for(int i = 0; i<(getSonidoPorSimbolo(figura).getSilencio()-1); i++){
                ritmos.add(0);
            }
        }
        if(figura == 2){
            ritmos.add(1);
            for(int i = 0; i<(getSonidoPorSimbolo(figura).getSilencio()-1); i++){
                ritmos.add(0);
            }
        }

        if (figura == 3){
            ritmos.add(1);
            for(int i = 0; i<(getSonidoPorSimbolo(figura).getSilencio()-1); i++){
                ritmos.add(0);
            }
        }
        if(figura==0){
            for(int i = 0; i<(getSonidoPorSimbolo(figura).getSilencio()); i++){
                ritmos.add(0);
            }
        }

    }


}
