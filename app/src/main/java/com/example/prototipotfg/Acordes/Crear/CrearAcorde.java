package com.example.prototipotfg.Acordes.Crear;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.prototipotfg.Acordes.TutorialAdivinarAcordes;
import com.example.prototipotfg.BBDD.Entidades.NivelAdivinar;
import com.example.prototipotfg.Enumerados.Acordes;
import com.example.prototipotfg.Enumerados.Dificultad;
import com.example.prototipotfg.Enumerados.Instrumentos;
import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Enumerados.Notas;
import com.example.prototipotfg.Enumerados.Octavas;
import com.example.prototipotfg.Enumerados.RangosPuntuaciones;
import com.example.prototipotfg.Mix.Ejercicios.CrearAcordeMix;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.Controlador;
import com.example.prototipotfg.Singletons.FactoriaNotas;
import com.example.prototipotfg.Singletons.GestorBBDD;
import com.example.prototipotfg.Singletons.Reproductor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static android.view.View.GONE;

public class CrearAcorde extends Activity {

    protected Acordes acordeCorrecto;
    protected ArrayList<String> notasPosibles = new ArrayList<>();
    protected ArrayList<Acordes> acordesPosibles = new ArrayList<>();
    private ArrayList<ArrayList<Pair<Notas, Octavas>>> acordesReproducir = new ArrayList<>();
    protected ArrayList<Pair<Notas, Octavas>> acordeCorrectoReproducir = new ArrayList<>();
    protected ArrayList<Button> botonesOpciones = new ArrayList<>();

    protected Notas notaInicio;
    protected int numOpciones;
    protected int num_notas;
    private boolean comprobada = false;
    private int num_marcadas = 0;
    protected ArrayList<String> respuestas = new ArrayList<String>();
    protected Octavas octavaInicio;
    protected View[] botonesSeleccionados;
    protected View[] respuestaCorrecta;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nivel_crear_acorde);

        this.numOpciones = Controlador.getInstance().getNum_opciones();
        this.num_notas = numOpciones + 3;
        this.octavaInicio = Controlador.getInstance().getOctavas().get((new Random()).nextInt(Controlador.getInstance().getOctavas().size()-1));
        this.acordesPosibles = seleccionaAcordesAleatorios(Controlador.getInstance().getAcordes());
        this.notaInicio = FactoriaNotas.getInstance().getNotaInicioIntervalo();
        this.acordeCorrecto = acordesPosibles.get(0);
        this.acordeCorrectoReproducir = Acordes.devuelveNotasAcorde(acordeCorrecto,octavaInicio,notaInicio);
        this.notasPosibles = seleccionaNotasAleatorios(acordeCorrectoReproducir);
        this.botonesSeleccionados = new View[num_notas];
        this.respuestaCorrecta = new View[num_notas];
        int nivel = Controlador.getInstance().getNivel();

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

        if(nivel > 5){
            Button info = findViewById(R.id.infoCrearAcordes);
            info.setVisibility(GONE);
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
            String text = button.getText().toString();
            Pair<Notas, Octavas> par = new Pair<>(Notas.devuelveNotaPorNombre(text.substring(0, text.length()-1)), Octavas.devuelveOctavaPorNumero(Integer.parseInt(text.substring(text.length()-1))));
            if (acordeCorrectoReproducir.contains(par)) {
                respuestaCorrecta[(int) button.getId() - 1] = button;
            }
            else respuestaCorrecta[(int) button.getId() - 1] = null;
            botonesOpciones.add(button);
            opciones.addView(button);
        }
        if (!(this instanceof CrearAcordeMix)) {
            GestorBBDD.getInstance().modoRealizado(ModoJuego.Crear_Acordes);
            if (GestorBBDD.getInstance().esPrimerNivelAdivinar(Controlador.getInstance().getModo_juego(), Controlador.getInstance().getNivel()) && Controlador.getInstance().getNivel() != 1) {

                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);

                ModoJuego.mostrarPopUpNuevoNivel(inflater, ModoJuego.Crear_Acordes, findViewById(android.R.id.content).getRootView(), false, 0, 0);
            }
        }
    }

    protected void respuesta_seleccionada(View view) {

        if (!comprobada) {
            Button b = (Button) view;
            if (botonesSeleccionados[(int) b.getId() - 1] != null) {

                b.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_blue_300)));

                botonesSeleccionados[(int) b.getId() - 1] = null;
                num_marcadas--;
                ponerComprobarVisible(GONE);
                respuestas.remove(b.getText().toString());
            } else{

                b.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_blue_700)));
                botonesSeleccionados[(int) b.getId() - 1] = b;
                respuestas.add(b.getText().toString());
                num_marcadas++;

            }


            if (Controlador.getInstance().getNivel() == 5) {
                String ruta = devuelveRutaBoton(b.getText().toString());
                reproduceNota(ruta);
            }

        }
        if(num_marcadas >= 2 && !comprobada)
            ponerComprobarVisible(1);

        if((Controlador.getInstance().getDificultad().equals(Dificultad.Facil) || Controlador.getInstance().getDificultad().equals(Dificultad.Medio)) && Controlador.getInstance().getNivel() != 5) {
            ArrayList<Pair<Notas, Octavas>> respuestasReproducir = new ArrayList<>();
            Pair<Notas, Octavas> par = new Pair<>(this.notaInicio, this.octavaInicio);
            respuestasReproducir.add(par);
            for (int i = 0; i < respuestas.size(); i++) {

                par = new Pair<>(Notas.devuelveNotaPorNombre(respuestas.get(i).substring(0, respuestas.get(i).length() - 1)), Octavas.devuelveOctavaPorNumero(Integer.parseInt(respuestas.get(i).substring(respuestas.get(i).length() - 1))));
                respuestasReproducir.add(par);
            }

            ArrayList<AssetFileDescriptor> afd = preparaAssets(respuestasReproducir);
            Reproductor.getInstance().reproducirAcorde(afd);
            cierraAssets(afd);
        }


    }

    private String devuelveRutaBoton(String text) {
        Notas n = Notas.devuelveNotaPorNombre(text.substring(0, text.length()-1));
        Octavas o = Octavas.devuelveOctavaPorNumero(Integer.parseInt(text.substring(text.length()-1)));
        return FactoriaNotas.getInstance().getInstrumento().getPath()+o.getPath()+n.getPath();
    }
    protected void ponerComprobarVisible(int visible) {
        Button comprobar = findViewById(R.id.comprobarCrearAcordes);
        comprobar.setVisibility(visible);

    }

    protected ArrayList<Acordes> seleccionaAcordesAleatorios(ArrayList<Acordes> acordes) {
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

    protected ArrayList<String> seleccionaNotasAleatorios(ArrayList<Pair<Notas, Octavas>> acordeCorrectoReproducir) {
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

        for (i = i; i <= num_notas; i++) {
            nota = notas[random.nextInt(12)].getNombre();
            while (retorno.contains(nota+this.octavaInicio.getOctava()) || nota == retorno.get(0).substring(0, retorno.get(0).length()-1)) {
                nota = notas[random.nextInt(12)].getNombre();
            }
            retorno.add(nota+this.octavaInicio.getOctava());
        }
        retorno.remove(0);
        return retorno;
    }
    public void comprobarCrearAcordes(View view) {
        int nivelActual = GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Crear_Acordes.toString()).getNivel();
        int rangoActual = RangosPuntuaciones.getRangoPorNombre(GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Crear_Acordes.toString()).getRango()).ordinal();

        if (!comprobada) {
            this.comprobada = true;

            for(int i = 0; i<num_notas; i++){
                if(respuestaCorrecta[i] != null)
                    respuestaCorrecta[i].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));

                if(botonesSeleccionados[i]!=null && respuestaCorrecta[i] == null){
                    botonesSeleccionados[i].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_red_500)));
                }
            }

            boolean correcta = true;
            for(int i = 0; i < respuestas.size();i++){
                String text = respuestas.get(i);
                Pair<Notas, Octavas> par = new Pair<>(Notas.devuelveNotaPorNombre(text.substring(0, text.length()-1)), Octavas.devuelveOctavaPorNumero(Integer.parseInt(text.substring(text.length()-1))));
                if (!acordeCorrectoReproducir.contains(par)) {
                    correcta = false;
                }
            }
            if(respuestas.size() != acordeCorrectoReproducir.size()-1) correcta = false;

            NivelAdivinar nivel = null;
            if(correcta){
                if(Controlador.getInstance().getNivel() == GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Crear_Acordes.toString()).getNivel())
                    GestorBBDD.getInstance().actualizarPuntuacion(Controlador.getInstance().getNivel(), ModoJuego.Crear_Acordes.toString(), true);
                nivel = new NivelAdivinar(ModoJuego.Crear_Acordes.getNombre(), Controlador.getInstance().getNivel(), true, GestorBBDD.getInstance().getUsuarioLoggeado().getCorreo(), 1, 0);
            }
            else {
                if(Controlador.getInstance().getNivel() == GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Crear_Acordes.toString()).getNivel())
                    GestorBBDD.getInstance().actualizarPuntuacion(Controlador.getInstance().getNivel(), ModoJuego.Crear_Acordes.toString(), false);
                nivel = new NivelAdivinar(ModoJuego.Crear_Acordes.getNombre(), Controlador.getInstance().getNivel(), false, GestorBBDD.getInstance().getUsuarioLoggeado().getCorreo(), 0, 1);
            }
            GestorBBDD.getInstance().insertaNivelAdivinar(nivel);

        }
        ArrayList<AssetFileDescriptor> afd = preparaAssets(acordeCorrectoReproducir);
        Reproductor.getInstance().reproducirAcorde(afd);
        cierraAssets(afd);
        findViewById(R.id.infoCrearAcordes).setVisibility(GONE);
        findViewById(R.id.botonReproduceCrearAcorde).setEnabled(false);
        findViewById(R.id.botonReproduceCrearAcorde).setAlpha(.5f);
        findViewById(R.id.btnCrearAcordeReferencia).setEnabled(false);
        findViewById(R.id.btnCrearAcordeReferencia).setAlpha(.5f);


        for (Button b : botonesOpciones)
            b.setEnabled(false);
        ponerComprobarVisible(GONE);

        int nivelNuevo = GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Crear_Acordes.toString()).getNivel();
        int rangoNuevo = RangosPuntuaciones.getRangoPorNombre(GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Crear_Acordes.toString()).getRango()).ordinal();
        if(rangoActual != rangoNuevo) {
            LayoutInflater inflater = (LayoutInflater)
                    getSystemService(LAYOUT_INFLATER_SERVICE);
            RangosPuntuaciones.mostrar_popUp_rango(view, rangoActual, rangoNuevo, inflater, ModoJuego.Crear_Acordes.toString());

        }

        if(nivelActual != nivelNuevo){
            Controlador.getInstance().setNivel(nivelNuevo);

            LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
            ModoJuego.mostrarPopUpNuevoNivel(inflater, ModoJuego.Crear_Acordes, findViewById(android.R.id.content).getRootView(), true, nivelActual, nivelNuevo);

            Controlador.getInstance().estableceDificultad();
        }
        findViewById(R.id.continuar_ca).setVisibility(View.VISIBLE);

    }

    public void reproducirNotaInicioAcorde(View view) throws IOException {
        String ruta = Instrumentos.Piano.getPath() + this.octavaInicio.getPath() + this.notaInicio.getPath();
        reproduceNota(ruta);
    }


    public void reproduceNota(String ruta) {
        AssetFileDescriptor afd = null;
        try {
            afd = getAssets().openFd(ruta);
            Reproductor.getInstance().reproducirNota(afd);
            afd.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected ArrayList<AssetFileDescriptor> preparaAssets(ArrayList<Pair<Notas, Octavas>> acorde){
        ArrayList<AssetFileDescriptor> retorno = new ArrayList<>();
        for (Pair<Notas, Octavas> nota : acorde){
            try {
                retorno.add(getAssets().openFd(Instrumentos.Piano.getPath() + nota.second.getPath() + nota.first.getPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return retorno;
    }


    protected void cierraAssets(ArrayList<AssetFileDescriptor> assetFileDescriptors) {
        for (AssetFileDescriptor afd : assetFileDescriptors) {
            try {
                afd.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public void reproduceReferenciaCrearAcorde(View view){
        String ruta = Instrumentos.Piano.getPath() + this.octavaInicio.getPath() + Notas.LA.getPath();
        reproduceNota(ruta);
    }

    public void muestraPosiblesCrearAcordes(View view){
        Intent i = new Intent(this, TutorialAdivinarAcordes.class);
        i.putExtra("octava", this.octavaInicio.getNombre());
        if (Controlador.getInstance().getNivel() == 1 || Controlador.getInstance().getNivel() == 2){
            i.putExtra("nota", this.notaInicio.getNombre());
        }
        else
            i.putExtra("nota", Notas.LA.getNombre());
        i.putExtra("nivel", Controlador.getInstance().getNivel());
        startActivity(i);

    }

    public void continuar(View view){
        finish();
        overridePendingTransition( 0, 0);
        startActivity(getIntent());
        overridePendingTransition( 0, 0);    }

}