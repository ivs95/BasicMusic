package com.example.prototipotfg.Notas;

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

import com.example.prototipotfg.Enumerados.Instrumentos;
import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Enumerados.Octavas;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.Controlador;
import com.example.prototipotfg.Singletons.FactoriaNotas;
import com.example.prototipotfg.Singletons.GestorBBDD;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class SeleccionarNivelAdivinarNotas extends Activity {

    private PopupWindow popupWindow;
    private View popupView;
    private int tutorial = 1;
    private int puntuacion;
    Bundle savedInstanceState;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.niveles);
        this.savedInstanceState = savedInstanceState;

        boolean primeraVez = getIntent().getExtras().getBoolean("visitado");
        LinearLayout llBotonera = (LinearLayout) findViewById(R.id.Botonera);

        TextView rango = findViewById(R.id.rango_niveles);
        rango.setText(GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Adivinar_Notas.toString()).getRango());
        //Creamos las propiedades de layout que tendrán los botones.
        //Son LinearLayout.LayoutParams porque los botones van a estar en un LinearLayout.
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        Button tutorial = new Button(this);
        tutorial.setLayoutParams(lp);
        tutorial.setText("Tutorial");
        tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tutorial_notas(v);
            }
        });
        llBotonera.addView(tutorial);
        //Creamos los botones en bucle


        int nivelActual = GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Adivinar_Notas.getNombre()).getNivel();
        for (int i = 0; i < 10; i++) {
            Button button = new Button(this);
            button.setId(i + 1);
            //Asignamos propiedades de layout al boton
            button.setLayoutParams(lp);
            //Asignamos Texto al botón
            button.setText("Nivel " + String.format("%02d", i + 1));

            //Asignamose el Listener
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        nivel_seleccionado(v);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            //Añadimos el botón a la botonera
            llBotonera.addView(button);

            if(nivelActual < i+1) {
                button.setEnabled(false);
                button.setAlpha(.5f);
            }
        }
        if (primeraVez) {
            mostrarPopupTutorial(findViewById(android.R.id.content).getRootView());
        }
    }

    private void tutorial_notas(View v) {
        Intent i = new Intent(this, TutorialNotas.class);
        startActivity(i);
    }

    public void nivel_seleccionado(View view) throws IOException {
        Intent i = new Intent(this, SeleccionarAdivinarNotas.class);
        Random random = new Random();
        //Nivel que se ha seleccionado
        Controlador.getInstance().setNivel(view.getId());
        Controlador.getInstance().estableceDificultad();
        ArrayList<Octavas> octavas = Controlador.getInstance().getOctavas();
        HashMap<String, String> notas = null;
        notas = FactoriaNotas.getInstance().getNumNotasAleatorias(Controlador.getInstance().getNum_opciones(), Instrumentos.Piano, octavas);
        ArrayList<String> nombres = new ArrayList<>(notas.keySet());
        i.putStringArrayListExtra("nombres", nombres);
        startActivity(i);
    }

    public void mostrarPopupTutorial(View view){
            LayoutInflater inflater = (LayoutInflater)
                    getSystemService(LAYOUT_INFLATER_SERVICE);

            popupView = inflater.inflate(R.layout.popup_tutorial_adivinarnotas, null);

            // create the popup window
            //final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
            popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);

            // show the popup window
            // which view you pass in doesn't matter, it is only used for the window tolken


            findViewById(R.id.id_niveles).post(new Runnable() {
                public void run() {
                    popupWindow.showAtLocation(findViewById(R.id.id_niveles), Gravity.CENTER, 0, 0);
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
        Button button = view.findViewById(R.id.popup_adivinarnotas_next);
        if(tutorial == 1){
            view.findViewById(R.id.popup_adivinarnotas_mensaje2).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_adivinarnotas_scrollView2).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_adivinarnotas_prev).setVisibility(View.GONE);

            view.findViewById(R.id.popup_adivinarnotas_mensaje1).setVisibility(View.VISIBLE);
        }
        else if(tutorial == 2){

            view.findViewById(R.id.popup_adivinarnotas_mensaje1).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_adivinarnotas_mensaje3).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_adivinarnotas_linearLayout8).setVisibility(View.INVISIBLE);

            view.findViewById(R.id.popup_adivinarnotas_mensaje2).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_adivinarnotas_scrollView2).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_adivinarnotas_prev).setVisibility(View.VISIBLE);

            button.setText("Siguiente");
        }
        else if(tutorial == 3){
            view.findViewById(R.id.popup_adivinarnotas_mensaje2).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_adivinarnotas_scrollView2).setVisibility(View.INVISIBLE);

            view.findViewById(R.id.popup_adivinarnotas_mensaje3).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_adivinarnotas_linearLayout8).setVisibility(View.VISIBLE);

            button.setText("Cerrar");
        }
        else if(tutorial == 4){
            popupWindow.dismiss();
        }
    }

    public void onResume(){
        super.onResume();
        this.onCreate(this.savedInstanceState);
    }
}