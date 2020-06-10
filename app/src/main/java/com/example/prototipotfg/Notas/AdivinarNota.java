package com.example.prototipotfg.Notas;


import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import com.example.prototipotfg.BBDD.Entidades.NivelAdivinar;
import com.example.prototipotfg.Enumerados.Dificultad;
import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Enumerados.Notas;
import com.example.prototipotfg.Enumerados.Octavas;
import com.example.prototipotfg.Enumerados.RangosPuntuaciones;
import com.example.prototipotfg.Mix.Ejercicios.AdivinarNotaMix;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.Controlador;
import com.example.prototipotfg.Singletons.FactoriaNotas;
import com.example.prototipotfg.Singletons.GestorBBDD;
import com.example.prototipotfg.Singletons.Reproductor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static android.view.View.VISIBLE;

public class AdivinarNota extends Activity {

    protected View botonSeleccionado;
    protected View respuestaCorrecta;
    protected boolean comprobada = false;
    protected ArrayList<String> nombres;
    protected ArrayList<Button> botonesNotas = new ArrayList<>();

    protected String respuesta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nivel_seleccionar_adivinar_notas);


        ArrayList<Octavas> octavas = Controlador.getInstance().getOctavas();
        HashMap<String, String> notas;
        notas = FactoriaNotas.getInstance().getNumNotasAleatorias(Controlador.getInstance().getNum_opciones(),  octavas);
        nombres = new ArrayList<>(notas.keySet());

        FactoriaNotas.getInstance().setReferencia(Octavas.devuelveOctavaPorNumero(Integer.parseInt(nombres.get(0).substring(nombres.get(0).length() - 1))));
        FactoriaNotas.getInstance().setReferenciaDo(Octavas.devuelveOctavaPorNumero(Integer.parseInt(nombres.get(0).substring(nombres.get(0).length() - 1))));
        adaptaVista(Controlador.getInstance().getDificultad());
        LinearLayout opciones = findViewById(R.id.opciones);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        Random rand = new Random();

        int num_respuestas = nombres.size();
        int random1 = rand.nextInt(num_respuestas);
        ArrayList<Integer> aux = new ArrayList<Integer>();
        aux.add(random1);
        for (int i = 0; i < num_respuestas - 1; i++) {
            while (aux.contains(random1))
                random1 = rand.nextInt(num_respuestas);

            aux.add(random1);
        }

        for (int i = 0; i < num_respuestas; i++) {
            Button button = new Button(this);
            button.setId(i + 1);
            button.setLayoutParams(lp);
            button.setText(nombres.get(aux.get(i)));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    respuesta_seleccionada(v);
                }
            });
            opciones.addView(button);

            if (button.getText().toString() == nombres.get(0)) {
                this.respuestaCorrecta = button;
            }
            botonesNotas.add(button);
        }
        if (!(this instanceof AdivinarNotaMix)) {

            if (GestorBBDD.getInstance().esPrimerNivelAdivinar(Controlador.getInstance().getModo_juego(), Controlador.getInstance().getNivel()) && Controlador.getInstance().getNivel() != 1) {

                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);

                ModoJuego.mostrarPopUpNuevoNivel(inflater, ModoJuego.Adivinar_Notas, findViewById(android.R.id.content).getRootView(), false, 0, 0);
            }
        }
    }

    protected void adaptaVista(Dificultad dificultad) {
        int nivel = Controlador.getInstance().getNivel();
        if (dificultad.equals(Dificultad.Facil)) {
            Button referencia = findViewById(R.id.botonReferencia);
            referencia.setVisibility(View.GONE);
        } else if (dificultad.equals(Dificultad.Dificil)) {
            Button referencia = findViewById(R.id.botonReferencia);
            referencia.setVisibility(View.GONE);
        }
        if (nivel < 2 || nivel > 3) {
            Button referenciaDo = findViewById(R.id.botonReferenciaDo);
            referenciaDo.setVisibility(View.GONE);
        }

    }


    public void respuesta_seleccionada(View view) {
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

            respuesta = b.getText().toString();

            if (Controlador.getInstance().getDificultad().equals(Dificultad.Facil)) {
                String ruta = devuelveRutaBoton(respuesta);
                try {
                    AssetFileDescriptor afd = getAssets().openFd(ruta);
                    Reproductor.getInstance().reproducirNota(afd);
                    afd.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            ponerComprobarVisible(VISIBLE);
            findViewById(R.id.comprobar).setEnabled(true);
        }
    }

    private String devuelveRutaBoton(String text) {
        Notas n = Notas.devuelveNotaPorNombre(text.substring(0, text.length() - 1));
        Octavas o = Octavas.devuelveOctavaPorNumero(Integer.parseInt(text.substring(text.length() - 1)));
        return "piano/" + o.getPath() + n.getPath();
    }

    public void reproduceNotaRespuesta(View view) throws IOException {
        AssetFileDescriptor afd = getAssets().openFd(devuelveRutaBoton(((Button) respuestaCorrecta).getText().toString()));
        Reproductor.getInstance().reproducirNota(afd);
        afd.close();
    }

    public void reproducirReferencia(View view) throws IOException {
        AssetFileDescriptor afd = getAssets().openFd(FactoriaNotas.getInstance().getReferencia());
        Reproductor.getInstance().reproducirNota(afd);
        afd.close();

    }

    public void reproducirReferenciaDo(View view) throws IOException {
        AssetFileDescriptor afd = getAssets().openFd(FactoriaNotas.getInstance().getReferenciaDo());
        Reproductor.getInstance().reproducirNota(afd);
        afd.close();
    }


    protected void ponerComprobarVisible(int visible) {
        findViewById(R.id.comprobar).setVisibility(visible);
    }

    public void comprobarResultado(View view) {
        int nivelActual = GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Adivinar_Notas.toString()).getNivel();
        int rangoActual = RangosPuntuaciones.getRangoPorNombre(GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Adivinar_Notas.toString()).getRango()).ordinal();
        if (!comprobada) {
            NivelAdivinar nivel = null;
            this.comprobada = true;
            if (respuesta != nombres.get(0)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    botonSeleccionado.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_red_500)));
                    if (Controlador.getInstance().getNivel() == GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Adivinar_Notas.toString()).getNivel())
                        GestorBBDD.getInstance().actualizarPuntuacion(Controlador.getInstance().getNivel(), ModoJuego.Adivinar_Notas.toString(), false);
                    nivel = new NivelAdivinar(ModoJuego.Adivinar_Notas.getNombre(), Controlador.getInstance().getNivel(), false, GestorBBDD.getInstance().getUsuarioLoggeado().getCorreo(), 0, 1);
                }
            } else {
                if (Controlador.getInstance().getNivel() == GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Adivinar_Notas.toString()).getNivel())
                    GestorBBDD.getInstance().actualizarPuntuacion(Controlador.getInstance().getNivel(), ModoJuego.Adivinar_Notas.toString(), true);
                nivel = new NivelAdivinar(ModoJuego.Adivinar_Notas.getNombre(), Controlador.getInstance().getNivel(), true, GestorBBDD.getInstance().getUsuarioLoggeado().getCorreo(), 1, 0);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                respuestaCorrecta.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
            }
            GestorBBDD.getInstance().insertaNivelAdivinar(nivel);
            deshabilitaBotones();
        }
        findViewById(R.id.comprobar).setVisibility(View.GONE);

        int nivelNuevo = GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Adivinar_Notas.toString()).getNivel();
        int rangoNuevo = RangosPuntuaciones.getRangoPorNombre(GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Adivinar_Notas.toString()).getRango()).ordinal();
        if (rangoActual != rangoNuevo) {
            LayoutInflater inflater = (LayoutInflater)
                    getSystemService(LAYOUT_INFLATER_SERVICE);
            RangosPuntuaciones.mostrar_popUp_rango(view, rangoActual, rangoNuevo, inflater, ModoJuego.Adivinar_Notas.toString());
        }

        if (nivelActual != nivelNuevo) {
            Controlador.getInstance().setNivel(nivelNuevo);
            LayoutInflater inflater = (LayoutInflater)
                    getSystemService(LAYOUT_INFLATER_SERVICE);
            ModoJuego.mostrarPopUpNuevoNivel(inflater, ModoJuego.Adivinar_Notas, findViewById(android.R.id.content).getRootView(), true, nivelActual, nivelNuevo);

            Controlador.getInstance().estableceDificultad();
        }


        findViewById(R.id.continuar_an).setVisibility(VISIBLE);
    }

    protected void deshabilitaBotones() {
        for (Button b : botonesNotas) {
            b.setEnabled(false);
        }

        findViewById(R.id.botonReferencia).setEnabled(false);
        findViewById(R.id.botonReferencia).setAlpha(.5f);
        findViewById(R.id.botonReferenciaDo).setEnabled(false);
        findViewById(R.id.botonReferenciaDo).setAlpha(.5f);
        findViewById(R.id.botonNota).setEnabled(false);
        findViewById(R.id.botonNota).setAlpha(.5f);

    }

    public void continuar(View view) {

        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

}
