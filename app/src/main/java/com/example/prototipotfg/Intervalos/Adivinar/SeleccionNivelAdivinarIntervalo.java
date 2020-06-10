package com.example.prototipotfg.Intervalos.Adivinar;

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

public class SeleccionNivelAdivinarIntervalo extends Activity {

    private PopupWindow popupWindow;
    private View popupView;
    private int tutorial = 1;

    private Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.niveles);
        this.savedInstanceState = savedInstanceState;
        boolean primeraVez = GestorBBDD.getInstance().esPrimeraVezModo(ModoJuego.Adivinar_Intervalo);

        LinearLayout llBotonera = findViewById(R.id.Botonera);
        TextView rango = findViewById(R.id.rango_niveles);
        int puntuacion = GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Adivinar_Intervalo.toString()).getPuntuacionTotal();
        rango.setText(GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Adivinar_Intervalo.toString()).getRango() + " (" + puntuacion + " puntos)");

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        int nivelActual = GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Adivinar_Intervalo.toString()).getNivel();

        for (int i = 0; i < 6; i++) {
            Button button = new Button(this);
            button.setId(i + 1);
            button.setLayoutParams(lp);
            button.setText("Nivel " + (i + 1));

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

            if (nivelActual == i + 1 && nivelActual != ModoJuego.Adivinar_Intervalo.getMax_level()) {
                TextView texto = new TextView(this);
                texto.setText("Faltan " + (PuntosNiveles.values()[nivelActual].getMinPuntos() - puntuacion) + " puntos para desbloquear el siguiente nivel");
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
        Intent i = new Intent(this, AdivinarIntervalo.class);
        Controlador.getInstance().setNivel(view.getId());
        Controlador.getInstance().estableceDificultad();
        startActivity(i);
    }

    public void mostrarPopupTutorial(View view) {
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);

        popupView = inflater.inflate(R.layout.popup_tutorial_adivinaintervalo, null);
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
        Button button = view.findViewById(R.id.popup_adivinaintervalo_next);
        if (tutorial == 1) {
            view.findViewById(R.id.popup_adivinaintervalo_mensaje2).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_adivinaintervalo_scrollView2).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_adivinaintervalo_prev).setVisibility(View.GONE);

            view.findViewById(R.id.popup_adivinaintervalo_mensaje1).setVisibility(View.VISIBLE);
        } else if (tutorial == 2) {

            view.findViewById(R.id.popup_adivinaintervalo_mensaje1).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_adivinaintervalo_mensaje3).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_adivinaintervalo_linearLayout8).setVisibility(View.INVISIBLE);

            view.findViewById(R.id.popup_adivinaintervalo_mensaje2).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_adivinaintervalo_scrollView2).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_adivinaintervalo_prev).setVisibility(View.VISIBLE);

            button.setText("Siguiente");
        } else if (tutorial == 3) {
            view.findViewById(R.id.popup_adivinaintervalo_mensaje2).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_adivinaintervalo_scrollView2).setVisibility(View.INVISIBLE);

            view.findViewById(R.id.popup_adivinaintervalo_mensaje3).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_adivinaintervalo_linearLayout8).setVisibility(View.VISIBLE);

            button.setText("Cerrar");
        } else if (tutorial == 4) {
            popupWindow.dismiss();
            GestorBBDD.getInstance().modoRealizado(ModoJuego.Adivinar_Intervalo);
        }
    }

    public void onResume() {
        super.onResume();
        this.onCreate(this.savedInstanceState);
    }

}
