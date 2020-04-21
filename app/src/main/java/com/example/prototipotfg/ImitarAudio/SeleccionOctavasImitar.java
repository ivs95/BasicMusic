package com.example.prototipotfg.ImitarAudio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.prototipotfg.Enumerados.Octavas;
import com.example.prototipotfg.Enumerados.RangosVocales;
import com.example.prototipotfg.R;

import java.util.ArrayList;

public class SeleccionOctavasImitar extends Activity {

    private ArrayList<String> octavas = new ArrayList<String>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.niveles);

          LinearLayout llBotonera = (LinearLayout) findViewById(R.id.Botonera);
                 //Creamos las propiedades de layout que tendrán los botones.
                 //Son LinearLayout.LayoutParams porque los botones van a estar en un LinearLayout.
                 LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                //Creamos los botones en bucle
                  for (int i=0; i<3; i++){
                      Button button = new Button(this);
                      button.setId(i+1);
                      //Asignamos propiedades de layout al boton
                      button.setLayoutParams(lp);
                      //Asignamos Texto al botón
                      button.setText(RangosVocales.values()[i].getNombre());

                      //Asignamose el Listener
                      button.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {
                                  octavaPulsada(((Button)v).getText().toString());

                          }
                      });
                      //Añadimos el botón a la botonera
                      llBotonera.addView(button);
                  }
              }



    private void octavaPulsada(String nombre) {
        Intent i = new Intent(this, NivelesImitar.class);
        RangosVocales rv = RangosVocales.devuelveRVPorNombre(nombre);
        for(int e = rv.getOctavaIni(); e<=rv.getOctavaFin(); e++)
            octavas.add(Octavas.devuelveOctavaPorNumero(e).getNombre());
        i.putStringArrayListExtra("octavas", octavas);
        i.putExtra("rangoVocal",nombre);
        startActivity(i);
    }
}
