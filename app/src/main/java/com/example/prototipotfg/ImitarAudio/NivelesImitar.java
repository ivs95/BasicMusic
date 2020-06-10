package com.example.prototipotfg.ImitarAudio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Enumerados.PuntosNiveles;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.Controlador;
import com.example.prototipotfg.Singletons.GestorBBDD;

import java.io.IOException;

public class NivelesImitar extends Activity {

    private Bundle savedInstanceState;

    private PopupWindow popupWindow;
    private View popupView;
    private int tutorial = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.niveles);
        this.savedInstanceState = savedInstanceState;

        //Obtenemos el linear layout donde colocar los botones
        LinearLayout llBotonera = findViewById(R.id.Botonera);
        //Creamos las propiedades de layout que tendrán los botones.
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        boolean primeraVez = GestorBBDD.getInstance().esPrimeraVezModo(ModoJuego.Imitar_Audio);

        TextView rango = findViewById(R.id.rango_niveles);
        int puntuacion = GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Imitar_Audio.toString()).getPuntuacionTotal();
        rango.setText(GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Imitar_Audio.toString()).getRango() + " (" + puntuacion + " puntos)");

        int nivelActual = GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Imitar_Audio.toString()).getNivel();

        //Creamos los botones en bucle
        for (int i = 0; i < 8; i++) {

            Button button = new Button(this);
            button.setId(i + 1);
            //Asignamos propiedades de layout al boton
            button.setLayoutParams(lp);
            //Asignamos Texto al botón
            button.setText("Nivel " + (i + 1));

            //Asignamos el Listener
            if (nivelActual > i) {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nivel_seleccionado(v);
                    }
                });
            } else {
                button.setEnabled(false);
                button.setAlpha(.5f);
            }

            //Añadimos el botón a la botonera
            llBotonera.addView(button);
            if (nivelActual == i + 1 && nivelActual != ModoJuego.Imitar_Audio.getMax_level()) {
                TextView texto = new TextView(this);
                texto.setText("Faltan " + (PuntosNiveles.values()[nivelActual + 10].getMinPuntos() - puntuacion) + " puntos para desbloquear el siguiente nivel");
                texto.setLayoutParams(lp);
                texto.setTextColor(getResources().getColor(R.color.md_blue_900));
                texto.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                llBotonera.addView(texto);
            }
        }
        if (primeraVez)
            mostrarPopupTutorial(findViewById(android.R.id.content).getRootView());
    }

    public void nivel_seleccionado(View view) {
        Intent i = new Intent(this, ReproducirImitar.class);
        int nivel = view.getId();


        String rangoVocal = getIntent().getExtras().getString("rangoVocal");
        i.putExtra("rangoVocal", rangoVocal);
        Controlador.getInstance().setNivel(nivel);

        startActivity(i);
    }

    public void mostrarPopupTutorial(View view) {
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);

        popupView = inflater.inflate(R.layout.popup_tutorial_imitaraudio, null);

        popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);

        findViewById(R.id.id_niveles).post(new Runnable() {
            public void run() {
                popupWindow.showAtLocation(findViewById(R.id.id_niveles), Gravity.CENTER, 0, 0);
            }
        });

    }

    public void next(View view) {
        tutorial++;
        actualizaPopUp(popupView);
    }

    public void prev(View view) {
        tutorial--;
        actualizaPopUp(popupView);
    }


    public void actualizaPopUp(View view) {
        Button button = view.findViewById(R.id.popup_imitaraudio_next);
        if (tutorial == 1) {
            view.findViewById(R.id.popup_imitaraudio_mensaje2).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_imitaraudio_textoFrecuencia).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_imitaraudio_prev).setVisibility(View.GONE);

            view.findViewById(R.id.popup_imitaraudio_mensaje1).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_imitaraudio_mensaje12).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_imitaraudio_mensaje13).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_imitaraudio_botonGrabar).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_imitaraudio_button2).setVisibility(View.VISIBLE);
        } else if (tutorial == 2) {

            view.findViewById(R.id.popup_imitaraudio_mensaje1).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_imitaraudio_mensaje12).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_imitaraudio_mensaje13).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_imitaraudio_mensaje3).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_imitaraudio_button4).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_imitaraudio_botonGrabar).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_imitaraudio_button2).setVisibility(View.INVISIBLE);

            view.findViewById(R.id.popup_imitaraudio_mensaje2).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_imitaraudio_textoFrecuencia).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_imitaraudio_prev).setVisibility(View.VISIBLE);

            button.setText("Siguiente");
        } else if (tutorial == 3) {
            view.findViewById(R.id.popup_imitaraudio_mensaje2).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_imitaraudio_textoFrecuencia).setVisibility(View.INVISIBLE);

            view.findViewById(R.id.popup_imitaraudio_mensaje3).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_imitaraudio_button4).setVisibility(View.VISIBLE);

            button.setText("Cerrar");
        } else if (tutorial == 4) {
            popupWindow.dismiss();
            GestorBBDD.getInstance().modoRealizado(ModoJuego.Imitar_Audio);

        }
    }

    public void onResume() {
        super.onResume();
        this.onCreate(this.savedInstanceState);

    }

}
