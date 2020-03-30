package com.example.prototipotfg.ImitarAudio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.prototipotfg.Enumerados.Instrumentos;
import com.example.prototipotfg.Enumerados.RangosVocales;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.FactoriaNotas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class NivelesImitar extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.niveles);
        TextView titulo = findViewById(R.id.tituloNiveles);
        titulo.setPadding(0,200,0,0);
        //Obtenemos el linear layout donde colocar los botones
        LinearLayout llBotonera = (LinearLayout) findViewById(R.id.Botonera);
        //Creamos las propiedades de layout que tendrán los botones.
        //Son LinearLayout.LayoutParams porque los botones van a estar en un LinearLayout.
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT );
        lp.setMargins(150, 0, 150, 40);
        //Creamos los botones en bucle
        for (int i=0; i<3; i++){
            Button button = new Button(this);
            button.setId(i+1);
            //Asignamos propiedades de layout al boton
            button.setLayoutParams(lp);
            //Asignamos Texto al botón
            if(i==0)
                button.setText("Facil");
            else if(i==1)
                button.setText("Medio");
            else
                button.setText("Dificil");
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
        Intent i = new Intent(this, ReproducirImitar.class);
        int nivel = view.getId();
        HashMap<String, String> notas = FactoriaNotas.getInstance().getNotasRV(RangosVocales.devuelveRVPorNombre(getIntent().getExtras().getString("rangoVocal")),1, Instrumentos.Piano);
        ArrayList<String> nombres = new ArrayList<>(notas.keySet());
        ArrayList<String> rutas = new ArrayList<>(notas.values());

        /*
         * Aquí hay que seleccionar la nota y las variables (strings de los nombre) y meterlas en el bundle
         * Crear clase para seleccionar notas aleatorias
         * Claves: respuesta, fallo1,...,falloN
         * */
        i.putExtra("nivel", ((Button)view).getText().toString().toLowerCase());
        i.putStringArrayListExtra("nombres", nombres);
        i.putStringArrayListExtra("rutas", rutas);
        //i.putExtra("dificultad", getIntent().getExtras().getString("dificultad"));

        startActivity(i);
    }


}
