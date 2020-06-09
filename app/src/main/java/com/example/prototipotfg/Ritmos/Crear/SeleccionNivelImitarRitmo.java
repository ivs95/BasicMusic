package com.example.prototipotfg.Ritmos.Crear;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Enumerados.PuntosNiveles;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.Controlador;
import com.example.prototipotfg.Singletons.GestorBBDD;

public class SeleccionNivelImitarRitmo extends Activity{

    private Bundle savedInstanceState;

    private PopupWindow popupWindow;
    private View popupView;
    private int tutorial = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.niveles);
        this.savedInstanceState = savedInstanceState;
        boolean primeraVez = GestorBBDD.getInstance().esPrimeraVezModo(ModoJuego.Realiza_Ritmo);

        //Obtenemos el linear layout donde colocar los botones
        LinearLayout llBotonera = (LinearLayout) findViewById(R.id.Botonera);
        TextView rango = findViewById(R.id.rango_niveles);
        int puntuacion = GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Realiza_Ritmo.toString()).getPuntuacionTotal();
        rango.setText(GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Realiza_Ritmo.toString()).getRango() + " (" + puntuacion + " puntos)");

        //Creamos las propiedades de layout que tendrán los botones.
        //Son LinearLayout.LayoutParams porque los botones van a estar en un LinearLayout.
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        int nivelActual = GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Realiza_Ritmo.toString()).getNivel();

        //Creamos los botones en bucle
        for (int i=0; i<8; i++){
            Button button = new Button(this);
            button.setId(i+1);
            //Asignamos propiedades de layout al boton
            button.setLayoutParams(lp);
            //Asignamos Texto al botón
            button.setText("Nivel "+String.format("%02d", i+1 ));
            if (nivelActual > i) {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nivel_seleccionado(v);
                    }
                });
            }
            else{
                button.setEnabled(false);
                button.setAlpha(.5f);
            }
            //Asignamose el Listener

            //Añadimos el botón a la botonera
            llBotonera.addView(button);

            if (nivelActual == i+1 && nivelActual != ModoJuego.Realiza_Ritmo.getMax_level()){
                TextView texto = new TextView(this);
                texto.setText("Faltan " + (PuntosNiveles.values()[nivelActual].getMinPuntos() - puntuacion) +" puntos para desbloquear el siguiente nivel");
                texto.setLayoutParams(lp);
                texto.setTextColor(getResources().getColor(R.color.md_blue_900));
                texto.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                llBotonera.addView(texto);
            }
            if(primeraVez)
                mostrarPopupTutorial(findViewById(android.R.id.content).getRootView());
        }
    }

    public void nivel_seleccionado(View view) {
        Intent i = new Intent(this, ImitarRitmo.class);
        Controlador.getInstance().setNivel(view.getId());
        Controlador.getInstance().estableceDificultad();

        startActivity(i);
    }


    public void onResume(){
        super.onResume();
        this.onCreate(this.savedInstanceState);
    }

    public void mostrarPopupTutorial(final View view){
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);

        popupView = inflater.inflate(R.layout.popup_tutorial_crearitmos, null);

        // create the popup window
        //final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
        popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,true);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken


        view.post(new Runnable() {
            public void run() {
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
            }
        });

        // popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched

    }

    public void next(View view){
        tutorial++;
        actualizaPopUp(popupView);
    }

    public void prev(View view){
        tutorial--;
        actualizaPopUp(popupView);
    }



    public void actualizaPopUp(View view){
        Button button = view.findViewById(R.id.popup_crearitmos_next);
        if(tutorial == 1){
            view.findViewById(R.id.popup_crearitmos_mensaje2).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_crearitmos_linearRitmo).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_crearitmos_prev).setVisibility(View.GONE);

            view.findViewById(R.id.popup_crearitmos_mensaje1).setVisibility(View.VISIBLE);
        }
        else if(tutorial == 2){

            view.findViewById(R.id.popup_crearitmos_mensaje1).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_crearitmos_mensaje3).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_crearitmos_linearLayout6).setVisibility(View.INVISIBLE);

            view.findViewById(R.id.popup_crearitmos_mensaje2).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_crearitmos_linearRitmo).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_crearitmos_prev).setVisibility(View.VISIBLE);

        }
        else if(tutorial == 3){
            view.findViewById(R.id.popup_crearitmos_mensaje4).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_crearitmos_mensaje2).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_crearitmos_linearOpcionesComprobar).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_crearitmos_linearSoluciones).setVisibility(View.INVISIBLE);

            view.findViewById((R.id.popup_crearitmos_button1)).setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_blue_500)));
            view.findViewById((R.id.popup_crearitmos_button5)).setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_blue_500)));
            view.findViewById((R.id.popup_crearitmos_button9)).setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_blue_500)));
            view.findViewById((R.id.popup_crearitmos_button12)).setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_purple_800)));

            view.findViewById(R.id.popup_crearitmos_mensaje3).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_crearitmos_linearLayout6).setVisibility(View.VISIBLE);

            button.setText("Siguiente");
        }
        else if(tutorial == 4){
            view.findViewById(R.id.popup_crearitmos_mensaje3).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_crearitmos_linearLayout6).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_crearitmos_linearSoluciones).setVisibility(View.VISIBLE);

            view.findViewById((R.id.popup_crearitmos_button1)).setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
            view.findViewById((R.id.popup_crearitmos_button5)).setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_red_500)));
            view.findViewById((R.id.popup_crearitmos_button9)).setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
            view.findViewById((R.id.popup_crearitmos_button12)).setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_red_500)));

            view.findViewById(R.id.popup_crearitmos_mensaje4).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_crearitmos_linearOpcionesComprobar).setVisibility(View.VISIBLE);

            button.setText("Cerrar");
        }
        else if(tutorial == 5){
            popupWindow.dismiss();
            GestorBBDD.getInstance().modoRealizado(ModoJuego.Realiza_Ritmo);
        }
    }

}
