package com.example.prototipotfg.Ritmos.Crear;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.prototipotfg.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static com.example.prototipotfg.Enumerados.DuracionSonido.getSonidoPorSimbolo;

public class NivelesCrearRitmo extends Activity{

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
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        //Creamos los botones en bucle
        for (int i=0; i<8; i++){
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
        Intent i = new Intent(this, CrearRitmos.class);
        int nivel = view.getId();
        /*
         * Aquí hay que seleccionar la nota y las variables (strings de los nombre) y meterlas en el bundle
         * Crear clase para seleccionar notas aleatorias
         * Claves: respuesta, fallo1,...,falloN
         * */
        Random random = new Random();
        ArrayList<Integer> ritmos1 = new ArrayList<>();
        ArrayList<Integer> ritmos2 = new ArrayList<>();
        ArrayList<Integer> ritmos3 = new ArrayList<>();
        ArrayList<Integer> ritmos4 = new ArrayList<>();
        for(int x = 0; x<4; x++) {
            int nota = random.nextInt(3) + 1;
            //Llenar aleatorios

            for (int j = getSonidoPorSimbolo(nota).getSilencio(); j <= longitud; j += getSonidoPorSimbolo(nota).getSilencio()) {
                if(x == 0)
                    agregaFigura(nota, ritmos1, compas);
                else if (x==1)
                    agregaFigura(nota, ritmos2, compas);
                else if (x==2)
                    agregaFigura(nota, ritmos3, compas);
                else if (x==3)
                    agregaFigura(nota, ritmos4, compas);
                if (longitud - j >= 4)
                    nota = random.nextInt(4);
                else if (longitud - j == 3 || longitud - j == 2)
                    nota = random.nextInt(2) + 2;
                else if (longitud - j == 1)
                    nota = 3;
            }

        }
        i.putExtra("nivel", nivel);
        i.putIntegerArrayListExtra("ritmos1", ritmos1);
        i.putIntegerArrayListExtra("ritmos2", ritmos2);
        i.putIntegerArrayListExtra("ritmos3", ritmos3);
        i.putIntegerArrayListExtra("ritmos4", ritmos4);
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
