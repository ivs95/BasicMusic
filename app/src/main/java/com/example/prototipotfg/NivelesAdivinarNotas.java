package com.example.prototipotfg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class NivelesAdivinarNotas extends Activity {


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
                    nivel_seleccionado(v);
                }
            });
            //Añadimos el botón a la botonera
            llBotonera.addView(button);
        }
    }

    public void nivel_seleccionado(View view){
        Intent i = new Intent(this, ReproducirAdivinar.class);

        //Nivel que se ha seleccionado
        int nivel = view.getId();
        //Octavas[] octavas = getOctavasParaNivel(nivel);
        //int numNotas = getNotasParaNivel(nivel);
        ArrayList<Octavas>  octavas = getOctavasParaNivel(nivel);
        HashMap<String, String> notas = null;
        try {
            notas = FactoriaNotas.getInstance().getNumNotasAleatorias(getNotasParaNivel(nivel), Instrumentos.Piano,octavas);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        * Aquí hay que seleccionar la nota y las variables (strings de los nombre) y meterlas en el bundle
        * Crear clase para seleccionar notas aleatorias
        * Claves: respuesta, fallo1,...,falloN
        * */
        ArrayList<String> nombres = new ArrayList<>(notas.keySet());
        ArrayList<String> rutas = new ArrayList<>(notas.values());

        i.putExtra("nivel", nivel);
        i.putExtra("dificultad", getIntent().getExtras().getString("dificultad"));
        i.putStringArrayListExtra("nombres", nombres);
        i.putStringArrayListExtra("rutas", rutas);
        startActivity(i);
    }

    private int getNotasParaNivel(int nivel) {
        int numeroNotas = 0;
        numeroNotas = (nivel%5)+1;
        if (numeroNotas == 1){
            numeroNotas+=5;
        }
        return numeroNotas;


    }

    private ArrayList<Octavas>  getOctavasParaNivel(int nivel) {
        ArrayList<Octavas> octavas = new ArrayList<Octavas>() ;
        octavas.add(Octavas.Primera);
        if (nivel > 5){
            octavas.add(Octavas.Segunda);
            if (nivel > 10){
                octavas.add(Octavas.Tercera);
                if (nivel > 15){
                    octavas.add(Octavas.Cuarta);
                    if (nivel > 20){
                        octavas.add(Octavas.Quinta);
                        if (nivel > 25){
                            octavas.add(Octavas.Sexta);
                            if (nivel > 30){
                                octavas.add(Octavas.Septima);
                            }
                        }
                    }
                }
            }
        }
        return octavas;
    }
}
