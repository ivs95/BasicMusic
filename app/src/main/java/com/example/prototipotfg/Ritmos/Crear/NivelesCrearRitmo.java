package com.example.prototipotfg.Ritmos.Crear;

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
import java.util.ArrayList;
import java.util.Random;

import static com.example.prototipotfg.Enumerados.DuracionSonido.getSonidoPorSimbolo;

public class NivelesCrearRitmo extends Activity{

    private int compas = 4;
    private int num = 4;
    private int longitud = compas*num;
    private Bundle savedInstanceState;

    private PopupWindow popupWindow;
    private View popupView;
    private int tutorial = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.niveles);
        this.savedInstanceState = savedInstanceState;
        boolean primeraVez = getIntent().getExtras().getBoolean("visitado");

        //Obtenemos el linear layout donde colocar los botones
        LinearLayout llBotonera = (LinearLayout) findViewById(R.id.Botonera);
        TextView rango = findViewById(R.id.rango_niveles);
        rango.setText(GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Realiza_Ritmo.toString()).getRango());

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
                texto.setText("Faltan " + (PuntosNiveles.values()[nivelActual].getMinPuntos() - GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Realiza_Ritmo.toString()).getPuntuacionTotal()) +" puntos para desbloquear el siguiente nivel");
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
        Intent i = new Intent(this, CrearRitmos.class);
        int nivel = view.getId();
        /*
         * Aquí hay que seleccionar la nota y las variables (strings de los nombre) y meterlas en el bundle
         * Crear clase para seleccionar notas aleatorias
         * Claves: respuesta, fallo1,...,falloN
         * */
        Random random = new Random();
        ArrayList<Integer> ritmos1 = new ArrayList<>();
        ArrayList<Integer> ritmos2 = new ArrayList<>();
        ArrayList<Integer> ritmos3 = new ArrayList<>();
        ArrayList<Integer> ritmos4 = new ArrayList<>();
        for(int x = 0; x<4; x++) {
            int nota = random.nextInt(3) + 1;
            //Llenar aleatorios

            for (int j = getSonidoPorSimbolo(nota).getSilencio(); j <= longitud; j += getSonidoPorSimbolo(nota).getSilencio()) {
                if(x == 0)
                    agregaFigura(nota, ritmos1, compas);
                else if (x==1)
                    agregaFigura(nota, ritmos2, compas);
                else if (x==2)
                    agregaFigura(nota, ritmos3, compas);
                else if (x==3)
                    agregaFigura(nota, ritmos4, compas);
                if (longitud - j >= 4)
                    nota = random.nextInt(4);
                else if (longitud - j == 3 || longitud - j == 2)
                    nota = random.nextInt(2) + 2;
                else if (longitud - j == 1)
                    nota = 3;
            }

        }
        i.putExtra("nivel", nivel);
        i.putIntegerArrayListExtra("ritmos1", ritmos1);
        i.putIntegerArrayListExtra("ritmos2", ritmos2);
        i.putIntegerArrayListExtra("ritmos3", ritmos3);
        i.putIntegerArrayListExtra("ritmos4", ritmos4);
        //i.putExtra("dificultad", getIntent().getExtras().getString("dificultad"));

        startActivity(i);
    }

    public void agregaFigura(int figura, ArrayList<Integer> ritmos, int compas){
        if(figura == 1){
            ritmos.add(1);
            for(int i = 0; i<(getSonidoPorSimbolo(figura).getSilencio()-1); i++){
                ritmos.add(0);
            }
        }
        if(figura == 2){
            ritmos.add(1);
            for(int i = 0; i<(getSonidoPorSimbolo(figura).getSilencio()-1); i++){
                ritmos.add(0);
            }
        }

        if (figura == 3){
            ritmos.add(1);
            for(int i = 0; i<(getSonidoPorSimbolo(figura).getSilencio()-1); i++){
                ritmos.add(0);
            }
        }
        if(figura==0){
            for(int i = 0; i<(getSonidoPorSimbolo(figura).getSilencio()); i++){
                ritmos.add(0);
            }
        }


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

            view.findViewById(R.id.popup_crearitmos_mensaje3).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_crearitmos_linearLayout6).setVisibility(View.VISIBLE);

            button.setText("Siguiente");
        }
        else if(tutorial == 4){
            view.findViewById(R.id.popup_crearitmos_mensaje3).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_crearitmos_linearLayout6).setVisibility(View.INVISIBLE);

            view.findViewById(R.id.popup_crearitmos_mensaje4).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_crearitmos_linearOpcionesComprobar).setVisibility(View.VISIBLE);

            button.setText("Cerrar");
        }
        else if(tutorial == 5){
            popupWindow.dismiss();
        }
    }

}
