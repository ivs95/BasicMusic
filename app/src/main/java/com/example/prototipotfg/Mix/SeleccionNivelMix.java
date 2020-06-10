package com.example.prototipotfg.Mix;

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
import com.example.prototipotfg.Singletons.ControladorMix;
import com.example.prototipotfg.Singletons.GestorBBDD;

public class SeleccionNivelMix extends Activity {

    private Bundle savedInstanceState;
    private PopupWindow popupWindow;
    private View popupView;
    private int tutorial = 1;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.niveles);
        this.savedInstanceState = savedInstanceState;
        LinearLayout llBotonera = findViewById(R.id.Botonera);

        boolean primeraVez = GestorBBDD.getInstance().esPrimeraVezModo(ModoJuego.Modo_Mix);

        Controlador.getInstance().setModo_juego(ModoJuego.Modo_Mix);
        TextView rango = findViewById(R.id.rango_niveles);
        int puntuacion = GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Modo_Mix.toString()).getPuntuacionTotal();
        rango.setText(GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Modo_Mix.toString()).getRango() + " (" + puntuacion + " puntos)");

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        int nivelActual = GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Modo_Mix.toString()).getNivel();
        for (int i = 0; i < 10; i++) {
            Button button = new Button(this);
            button.setId(i + 1);
            button.setLayoutParams(lp);
            button.setText("Nivel " + String.format("%02d", i + 1));

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

            llBotonera.addView(button);

            if (nivelActual == i + 1 && nivelActual != ModoJuego.Modo_Mix.getMax_level()) {
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
        ControladorMix.getInstance().setNivel(view.getId());
        ControladorMix.getInstance().iniciaModo();
        Controlador.getInstance().setMixIniciado(false);
        siguienteEjercicio();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode != RESULT_CANCELED) {
                ControladorMix.getInstance().setResultadoEjercicioActual(data.getBooleanExtra("resultado", false));
                siguienteEjercicio();
            } else {
                super.onCreate(this.savedInstanceState);
            }
        }
    }

    private void siguienteEjercicio() {
        GestorBBDD.getInstance().modoRealizado(ModoJuego.Modo_Mix);
        if (!ControladorMix.getInstance().finalMix()) {
            ControladorMix.getInstance().setEjercicio();
            Intent i = ControladorMix.getInstance().iniciaPrueba(this);
            startActivityForResult(i, 2);
        } else {
            ControladorMix.getInstance().setResultadoModo();
            Intent i = new Intent(this, ResultadoMix.class);
            startActivity(i);
        }
    }

    public void mostrarPopupTutorial(View view) {
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);

        popupView = inflater.inflate(R.layout.popup_tutorial_examen, null);

        popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);

        findViewById(R.id.id_niveles).post(new Runnable() {
            public void run() {
                popupWindow.showAtLocation(findViewById(R.id.id_niveles), Gravity.CENTER, 0, 0);
            }
        });

    }

    public void cerrar(View view) {
        popupWindow.dismiss();
    }

    public void onResume() {
        super.onResume();
        this.onCreate(this.savedInstanceState);
    }
}
