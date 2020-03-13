package com.example.prototipotfg.Acordes;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.prototipotfg.Enumerados.Acordes;
import com.example.prototipotfg.Enumerados.Instrumentos;
import com.example.prototipotfg.Enumerados.Notas;
import com.example.prototipotfg.Enumerados.Octavas;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.Controlador;


import java.io.IOException;
import java.util.ArrayList;

public class TutorialAdivinarAcordes extends Activity {

    private Octavas octavaAcordes;
    private ArrayList<ArrayList<Pair<Notas, Octavas>>> acordesReproducir = new ArrayList<>();
    private ArrayList<Acordes> acordes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.niveles);
        TextView titulo = findViewById(R.id.tituloNiveles);
        titulo.setText("Acordes sobre LA");
        octavaAcordes = Octavas.devuelveOctavaPorNombre(getIntent().getExtras().getString("octava"));

        LinearLayout llBotonera = (LinearLayout) findViewById(R.id.Botonera);
        //Creamos las propiedades de layout que tendrán los botones.
        //Son LinearLayout.LayoutParams porque los botones van a estar en un LinearLayout.
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(150, 0, 150, 0);
        acordes = Controlador.getInstance().getAcordes();
        //Creamos los botones en bucle

        for (Acordes a : acordes) {
            acordesReproducir.add(Acordes.devuelveNotasAcorde(a, this.octavaAcordes, Notas.LA));
        }
        for (int i = 0; i < acordes.size(); i++) {
            Button button = new Button(this);
            button.setId(i);
            //Asignamos propiedades de layout al boton
            button.setLayoutParams(lp);
            //Asignamos Texto al botón
            button.setText(acordes.get(i).getNombre());

            //Asignamose el Listener
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reproducirAcorde(v);
                }
            });
            //Añadimos el botón a la botonera
            llBotonera.addView(button);
        }
    }

    private void reproducirAcorde(View view){
        ArrayList<MediaPlayer> mediaPlayers = inicializaMediaPlayers(acordesReproducir.get(view.getId()));
        for (MediaPlayer m: mediaPlayers){
            m.start();
        }

    }

    private ArrayList<MediaPlayer> inicializaMediaPlayers(ArrayList<Pair<Notas, Octavas>> acordeCorrectoReproducir) {
        ArrayList<MediaPlayer> retorno = new ArrayList<>();
        for (Pair<Notas, Octavas> nota : acordeCorrectoReproducir) {
            String ruta = Instrumentos.Piano.getPath() + nota.second.getPath() + nota.first.getPath();
            MediaPlayer mediaPlayer = new MediaPlayer();
            AssetFileDescriptor afd;
            try {
                afd = getAssets().openFd(ruta);
                mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            retorno.add(mediaPlayer);
        }
        return retorno;
    }

}
