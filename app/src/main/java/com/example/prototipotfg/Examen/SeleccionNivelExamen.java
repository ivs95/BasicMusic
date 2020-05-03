package com.example.prototipotfg.Examen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Enumerados.PuntosNiveles;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.GestorBBDD;

public class SeleccionNivelExamen extends Activity {

    private boolean corregido = false;
    private Bundle savedInstanceState;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.niveles);
        this.savedInstanceState = savedInstanceState;
        LinearLayout llBotonera = (LinearLayout) findViewById(R.id.Botonera);

        TextView rango = findViewById(R.id.rango_niveles);
        int puntuacion = GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Adivinar_Notas.toString()).getPuntuacionTotal();
        rango.setText(GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Adivinar_Notas.toString()).getRango() + " (" + puntuacion + " puntos)");
        //Creamos las propiedades de layout que tendrán los botones.
        //Son LinearLayout.LayoutParams porque los botones van a estar en un LinearLayout.
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        //Creamos los botones en bucle


        int nivelActual = GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Adivinar_Notas.toString()).getNivel();
        for (int i = 0; i < 10; i++) {
            Button button = new Button(this);
            button.setId(i + 1);
            //Asignamos propiedades de layout al boton
            button.setLayoutParams(lp);
            //Asignamos Texto al botón
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
            //Asignamose el Listener

            //Añadimos el botón a la botonera
            llBotonera.addView(button);

            if (nivelActual == i + 1 && nivelActual != ModoJuego.Adivinar_Notas.getMax_level()) {
                TextView texto = new TextView(this);
                texto.setText("Faltan " + (PuntosNiveles.values()[nivelActual].getMinPuntos() - puntuacion) + " puntos para desbloquear el siguiente nivel");
                texto.setLayoutParams(lp);
                texto.setTextColor(getResources().getColor(R.color.md_blue_900));
                texto.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                llBotonera.addView(texto);
            }
        }

    }

    private void mostrarPopupTutorial(View rootView) {
    }


    public void nivel_seleccionado(View view) {
        ControladorExamen.getInstance().setNivel(view.getId());
        ControladorExamen.getInstance().setContext(this);
        ControladorExamen.getInstance().iniciaExamen();
        siguienteEjercicio();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            ControladorExamen.getInstance().setResultadoEjercicioActual(data.getBooleanExtra("resultado", false));
            siguienteEjercicio();
        }
    }

    private void siguienteEjercicio() {
        if(!ControladorExamen.getInstance().finalExamen()) {
            ControladorExamen.getInstance().setEjercicio();
            Intent i = ControladorExamen.getInstance().iniciaPrueba(this);
            startActivityForResult(i, 2);
        }
        else {
            ControladorExamen.getInstance().setResultadoExamen();
            Intent i = new Intent(this , ResultadoExamen.class);
            startActivity(i);
        }
        }


}
