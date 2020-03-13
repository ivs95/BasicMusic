package com.example.prototipotfg.Acordes;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.ColorStateList;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.prototipotfg.Enumerados.Acordes;
import com.example.prototipotfg.Enumerados.Dificultad;
import com.example.prototipotfg.Enumerados.Instrumentos;
import com.example.prototipotfg.Enumerados.Notas;
import com.example.prototipotfg.Enumerados.Octavas;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.Controlador;
import com.example.prototipotfg.Singletons.FactoriaNotas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class ReproducirAdivinarAcordes extends Activity {

    private Acordes acordeCorrecto;
    private ArrayList<Acordes> acordesPosibles = new ArrayList<>();
    private ArrayList<ArrayList<Pair<Notas, Octavas>>> acordesReproducir = new ArrayList<>();
    private ArrayList<Pair<Notas, Octavas>> acordeCorrectoReproducir = new ArrayList<>();
    private Notas notaInicio;
    private View botonSeleccionado;
    private View respuestaCorrecta;
    private int numOpciones;
    private boolean comprobada = false;
    private Octavas octavaInicio;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nivel_adivinar_acorde);
        ponerComprobarVisible(View.GONE);

        this.numOpciones = Controlador.getInstance().getNum_opciones();
        this.octavaInicio = Controlador.getInstance().getOctavas().get((new Random()).nextInt(Controlador.getInstance().getOctavas().size()-1));
        this.acordesPosibles = seleccionaAcordesAleatorios(numOpciones, Controlador.getInstance().getAcordes());
        this.notaInicio = FactoriaNotas.getInstance().getNotaInicioIntervalo(Instrumentos.Piano, octavaInicio);
        this.acordeCorrecto = acordesPosibles.get(0);
        this.acordeCorrectoReproducir = Acordes.devuelveNotasAcorde(acordeCorrecto,octavaInicio,notaInicio);
        TextView lblNotaInicio = findViewById(R.id.lblNotaInicioAcorde);
        TextView lblOctavaInicio = findViewById(R.id.lblOctavaInicioAcorde);
        if (Controlador.getInstance().getDificultad().equals(Dificultad.Dificil)){
            lblNotaInicio.setVisibility(GONE);
            lblOctavaInicio.setVisibility(GONE);

            Button botonTutorial = findViewById(R.id.button_info_adivinarAcorde);
            botonTutorial.setVisibility(GONE);

            Button botonReferencia = findViewById(R.id.btnAcordeReferencia);
            botonReferencia.setVisibility(VISIBLE);
        }
        else {
            lblNotaInicio.setText(lblNotaInicio.getText() + notaInicio.getNombre());
            lblOctavaInicio.setText(lblOctavaInicio.getText() + octavaInicio.getNombre());
        }
        Collections.shuffle(acordesPosibles);
        for (Acordes a : acordesPosibles) {
            acordesReproducir.add(Acordes.devuelveNotasAcorde(a, this.octavaInicio, this.notaInicio));
        }

        LinearLayout opciones = findViewById(R.id.opcionesAcordes);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < numOpciones; i++) {
            Button button = new Button(this);
            button.setId(i + 1);
            //Asignamos propiedades de layout al boton
            button.setLayoutParams(lp);
            //Asignamos Texto al botón
            button.setText(acordesPosibles.get(i).getNombre());

            //Asignamose el Listener
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    respuesta_seleccionada(v);
                }
            });
            //Añadimos el botón a la botonera
            button.setPadding(0, 0, 0, 0);
            if (button.getText().equals(this.acordeCorrecto.getNombre())) {
                respuestaCorrecta = button;
            }
            opciones.addView(button);
        }


    }

    private void respuesta_seleccionada(View view) {

        if (!comprobada) {
            Button b = (Button) view;
            if (botonSeleccionado != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    botonSeleccionado.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_blue_300)));
                }
            }
            botonSeleccionado = b;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                botonSeleccionado.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_blue_700)));
            }
            int indiceSeleccionado = view.getId();
            if (Controlador.getInstance().getDificultad().equals(Dificultad.Facil)) {
                ArrayList<Pair<Notas, Octavas>> acordeSeleccionado = acordesReproducir.get(indiceSeleccionado - 1);
                ArrayList<MediaPlayer> mediaPlayers = inicializaMediaPlayers(acordeSeleccionado);
                for (MediaPlayer m : mediaPlayers){
                    m.start();
                }
                }
            }
            ponerComprobarVisible(1);
        }


    private void ponerComprobarVisible(int visible) {
        Button comprobar = findViewById(R.id.comprobarAcordes);
        comprobar.setVisibility(visible);

    }

    private ArrayList<Acordes> seleccionaAcordesAleatorios(int numOpciones, ArrayList<Acordes> acordes) {
        ArrayList<Acordes> retorno = new ArrayList<>();
        Random random = new Random();
        Acordes acorde;
        for (int i = 0; i < numOpciones; i++) {
            acorde = acordes.get(random.nextInt(acordes.size()));
            while (retorno.contains(acorde)) {
                acorde = acordes.get(random.nextInt(acordes.size()));
            }
            retorno.add(acorde);
        }
        return retorno;
    }

    public void comprobarAcordes(View view) {
        if (!comprobada) {
            this.comprobada = true;
            if (this.botonSeleccionado != this.respuestaCorrecta) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    botonSeleccionado.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_red_500)));
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                respuestaCorrecta.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
            }
        }
        ponerComprobarVisible(GONE);
    }

    public void reproducirAcorde(View view){
        ArrayList<MediaPlayer> mediaPlayers = inicializaMediaPlayers(acordeCorrectoReproducir);
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

    public void reproduceReferencia(View view){
        String ruta = Instrumentos.Piano.getPath() + this.octavaInicio.getPath() + Notas.LA.getPath();
        MediaPlayer mediaPlayer = new MediaPlayer();
        AssetFileDescriptor afd;
        try {
            afd = getAssets().openFd(ruta);
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();

    }

    public void muestraPosibles(View view){
        Intent i = new Intent(this, TutorialAdivinarAcordes.class);
        i.putExtra("octava", this.octavaInicio.getNombre());
        startActivity(i);

    }

    public void volverAtrasAcordes(View view) {
        finish();
    }
}