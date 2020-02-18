package com.example.prototipotfg;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

public class HallaRitmos extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nivel_reproducir_adivinar);

        int nivel = getIntent().getExtras().getInt("nivel");
        TextView titulo = (TextView)findViewById(R.id.tituloAdivinar);
        titulo.setText(titulo.getText() + String.valueOf(nivel));
        TextView nota = (TextView)findViewById(R.id.nota);

    }


    public void play(View view) throws IOException {
        MediaPlayer mediaPlayer =  new MediaPlayer();

        mediaPlayer.prepare();
        mediaPlayer.start();

    }

    public void stop(View view){

    }


}
