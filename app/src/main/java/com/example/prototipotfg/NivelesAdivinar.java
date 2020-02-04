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

public class NivelesAdivinar extends Activity {


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
        Octavas[] octavas = {Octavas.Segunda};
        HashMap<String, String> notas = null;
        try {
            notas = FactoriaNotas.getInstance().getNumNotasAleatorias(4, Instrumentos.Piano,octavas);
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
        i.putStringArrayListExtra("nombres", nombres);
        i.putStringArrayListExtra("rutas", rutas);
        startActivity(i);
    }

    private int getNotasParaNivel(int nivel) {
        return 0;


    }

    private Octavas[] getOctavasParaNivel(int nivel) {
        if (nivel < 5){

        }
        return null;
    }
}
