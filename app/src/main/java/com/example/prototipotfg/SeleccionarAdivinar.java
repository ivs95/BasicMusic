package com.example.prototipotfg;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static android.view.View.INVISIBLE;

public class SeleccionarAdivinar extends Activity {

    private View botonSeleccionado;
    private View respuestaCorrecta;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nivel_seleccionar_adivinar);
        ponerComprobarVisible(INVISIBLE);

    }

    public void volverAtras(View view){
        finish();
    }

    public void seleccionarRespuesta(View view){
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.d3);
        mediaPlayer.start();

        ponerComprobarVisible(View.VISIBLE);
        if (botonSeleccionado!=null) {
            botonSeleccionado.setBackgroundColor(getResources().getColor(R.color.md_orange_400));
        }
            botonSeleccionado=view;
            botonSeleccionado.setBackgroundColor(getResources().getColor(R.color.md_deep_orange_900));
    }

    private void ponerComprobarVisible(int visible) {
        Button comprobar = (Button)findViewById(R.id.comprobar);
        comprobar.setVisibility(visible);
    }

    public void comprobarResultado(View view){
    }


}
