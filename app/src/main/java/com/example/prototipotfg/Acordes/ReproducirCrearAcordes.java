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

public class ReproducirCrearAcordes extends Activity {

    private Acordes acordeCorrecto;
    private ArrayList<String> notasPosibles = new ArrayList<>();
    private ArrayList<Acordes> acordesPosibles = new ArrayList<>();
    private ArrayList<ArrayList<Pair<Notas, Octavas>>> acordesReproducir = new ArrayList<>();
    private ArrayList<Pair<Notas, Octavas>> acordeCorrectoReproducir = new ArrayList<>();
    private Notas notaInicio;
    private View botonSeleccionado;
    private View respuestaCorrecta;
    private int numOpciones;
    private int num_notas;
    private boolean comprobada = false;
    private int num_marcadas = 0;
    private ArrayList<String> respuestas = new ArrayList<String>();
    private Octavas octavaInicio;
    private View botonesSeleccionados[];

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nivel_crear_acorde);
        ponerComprobarVisible(View.GONE);

        this.numOpciones = Controlador.getInstance().getNum_opciones();
        this.num_notas = numOpciones + 3;
        this.octavaInicio = Controlador.getInstance().getOctavas().get((new Random()).nextInt(Controlador.getInstance().getOctavas().size()-1));
        this.acordesPosibles = seleccionaAcordesAleatorios(Controlador.getInstance().getAcordes());
        this.notaInicio = FactoriaNotas.getInstance().getNotaInicioIntervalo(Instrumentos.Piano, octavaInicio);
        this.acordeCorrecto = acordesPosibles.get(0);
        this.acordeCorrectoReproducir = Acordes.devuelveNotasAcorde(acordeCorrecto,octavaInicio,notaInicio);
        this.notasPosibles = seleccionaNotasAleatorios(acordeCorrectoReproducir);
        this.botonesSeleccionados = new View[num_notas];

        TextView lblNotaInicio = findViewById(R.id.notaInicioCrearAcorde);
        TextView peticionAcorde = findViewById(R.id.lblPeticionCrearAcorde);

        //Button botonReferencia = findViewById(R.id.btnAcordeReferencia);
        //botonReferencia.setVisibility(VISIBLE);
        lblNotaInicio.setText(lblNotaInicio.getText() + acordeCorrectoReproducir.get(0).first.getNombre() + acordeCorrectoReproducir.get(0).second.getOctava());
        peticionAcorde.setText(peticionAcorde.getText() + acordeCorrecto.getNombre());

        Collections.shuffle(notasPosibles);

        Random rand = new Random();
        ArrayList <Integer> aux = new ArrayList<Integer>();
        for(int i = 0; i < num_notas; i++) {
            aux.add(i);
        }


        LinearLayout opciones = findViewById(R.id.opcionesCrearAcordes);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < num_notas; i++) {
            Button button = new Button(this);
            button.setId(i + 1);
            //Asignamos propiedades de layout al boton
            button.setLayoutParams(lp);
            //Asignamos Texto al botón
            button.setText(notasPosibles.get(aux.get(i)));

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
            if (botonesSeleccionados[(int) b.getId() - 1] != null) {

                b.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_blue_300)));

                botonesSeleccionados[(int) b.getId() - 1] = null;
                num_marcadas--;
                ponerComprobarVisible(GONE);
                respuestas.remove(b.getText().toString());
            } else if(num_marcadas < 3){

                b.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_blue_700)));
                botonesSeleccionados[(int) b.getId() - 1] = b;
                respuestas.add(b.getText().toString());
                num_marcadas++;
            }

            botonSeleccionado = b;

            if (!Controlador.getInstance().getDificultad().equals(Dificultad.Dificil)) {
                String ruta = devuelveRutaBoton(b.getText().toString());
                MediaPlayer mediaPlayer = new MediaPlayer();
                try {
                    AssetFileDescriptor afd = getAssets().openFd(ruta);
                    mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.start();
            }

        }
        if(num_marcadas == 3)
            ponerComprobarVisible(1);
    }

    private String devuelveRutaBoton(String text) {
        Notas n = Notas.devuelveNotaPorNombre(text.substring(0, text.length()-1));
        Octavas o = Octavas.devuelveOctavaPorNumero(Integer.parseInt(text.substring(text.length()-1)));
        return FactoriaNotas.getInstance().getInstrumento().getPath()+o.getPath()+n.getPath();
    }
    private void ponerComprobarVisible(int visible) {
        Button comprobar = findViewById(R.id.comprobarCrearAcordes);
        comprobar.setVisibility(visible);

    }

    private ArrayList<Acordes> seleccionaAcordesAleatorios(ArrayList<Acordes> acordes) {
        ArrayList<Acordes> retorno = new ArrayList<>();
        Random random = new Random();
        Acordes acorde;
        for (int i = 0; i < this.numOpciones; i++) {
            acorde = acordes.get(random.nextInt(acordes.size()));
            while (retorno.contains(acorde)) {
                acorde = acordes.get(random.nextInt(acordes.size()));
            }
            retorno.add(acorde);
        }
        return retorno;
    }

    private ArrayList<String> seleccionaNotasAleatorios(ArrayList<Pair<Notas, Octavas>> acordeCorrectoReproducir) {
        Notas[] notas = new Notas[12];
        notas = Notas.values();
        ArrayList<String> retorno = new ArrayList<>();
        Random random = new Random();
        int j = 0;
        int i = 0;
        String nota;
        while(j < acordeCorrectoReproducir.size()){
            if(!retorno.contains(acordeCorrectoReproducir.get(j).first.getNombre() + acordeCorrectoReproducir.get(j).second.getOctava())) {
                nota = acordeCorrectoReproducir.get(j).first.getNombre() + acordeCorrectoReproducir.get(j).second.getOctava();
                retorno.add(nota);
                i++;
            }
            j++;
        }

        retorno.remove(0);
        i--;
        for (i = i; i < num_notas; i++) {
            nota = notas[random.nextInt(12)].getNombre();
            while (retorno.contains(nota)) {
                nota = notas[random.nextInt(12)].getNombre();
            }
            retorno.add(nota+this.octavaInicio.getOctava());
        }
        return retorno;
    }

    public void comprobarCrearAcordes(View view) {
        if (!comprobada) {
            this.comprobada = true;
            if(1 == 1){
                //Correct
                for(int i = 0; i<numOpciones; i++){
                    if(botonesSeleccionados[i]!=null){
                        botonesSeleccionados[i].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
                    }
                }

            }
            else{
                //Incorrect
                for(int i = 0; i<numOpciones; i++){
                    if(botonesSeleccionados[i]!=null){
                        botonesSeleccionados[i].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_red_500)));
                    }
                }

            }

        }
        ponerComprobarVisible(GONE);
    }

    public void reproducirNotaInicioAcorde(View view) throws IOException {
        String ruta = Instrumentos.Piano.getPath() + this.octavaInicio.getPath() + this.notaInicio.getPath();
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

    public void reproduceReferenciaCrearAcorde(View view){
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