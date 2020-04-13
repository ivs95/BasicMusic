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

import com.example.prototipotfg.Enumerados.Dificultad;
import com.example.prototipotfg.Enumerados.Instrumentos;
import com.example.prototipotfg.Enumerados.RangosVocales;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.FactoriaNotas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class NivelesImitar extends Activity {

    private PopupWindow popupWindow;
    private View popupView;
    private int tutorial = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.niveles);
        TextView titulo = findViewById(R.id.tituloNiveles);
        //Obtenemos el linear layout donde colocar los botones
        LinearLayout llBotonera = (LinearLayout) findViewById(R.id.Botonera);
        //Creamos las propiedades de layout que tendrán los botones.
        //Son LinearLayout.LayoutParams porque los botones van a estar en un LinearLayout.
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        boolean primeraVez = getIntent().getExtras().getBoolean("visitado");

        //Creamos los botones en bucle
        for (int i = 0; i < Dificultad.values().length; i++){
            Button button = new Button(this);
            button.setId(i+1);
            //Asignamos propiedades de layout al boton
            button.setLayoutParams(lp);
            //Asignamos Texto al botón
            button.setText(Dificultad.values()[i].toString());

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
        }
        mostrarPopupTutorial(findViewById(android.R.id.content).getRootView());
    }

    public void nivel_seleccionado(View view) throws IOException {
        Intent i = new Intent(this, ReproducirImitar.class);
        int nivel = view.getId();
        HashMap<String, String> notas = FactoriaNotas.getInstance().getNotasRV(RangosVocales.devuelveRVPorNombre(getIntent().getExtras().getString("rangoVocal")),1, Instrumentos.Piano);
        ArrayList<String> nombres = new ArrayList<>(notas.keySet());
        ArrayList<String> rutas = new ArrayList<>(notas.values());

        /*
         * Aquí hay que seleccionar la nota y las variables (strings de los nombre) y meterlas en el bundle
         * Crear clase para seleccionar notas aleatorias
         * Claves: respuesta, fallo1,...,falloN
         * */
        i.putExtra("rangoVocal" , getIntent().getExtras().getString("rangoVocal"));
        i.putExtra("nivel", ((Button)view).getText().toString().toLowerCase());
        i.putStringArrayListExtra("nombres", nombres);
        i.putStringArrayListExtra("rutas", rutas);
        //i.putExtra("dificultad", getIntent().getExtras().getString("dificultad"));

        startActivity(i);
    }

    public void mostrarPopupTutorial(View view){
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);

        popupView = inflater.inflate(R.layout.popup_tutorial_imitaraudio, null);

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
        Button button = view.findViewById(R.id.popup_imitaraudio_next);
        if(tutorial == 1){
            view.findViewById(R.id.popup_imitaraudio_mensaje2).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_imitaraudio_textoFrecuencia).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_imitaraudio_prev).setVisibility(View.GONE);

            view.findViewById(R.id.popup_imitaraudio_mensaje1).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_imitaraudio_mensaje12).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_imitaraudio_mensaje13).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_imitaraudio_botonGrabar).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_imitaraudio_button2).setVisibility(View.VISIBLE);
        }
        else if(tutorial == 2){

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
        }
        else if(tutorial == 3){
            view.findViewById(R.id.popup_imitaraudio_mensaje2).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_imitaraudio_textoFrecuencia).setVisibility(View.INVISIBLE);

            view.findViewById(R.id.popup_imitaraudio_mensaje3).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_imitaraudio_button4).setVisibility(View.VISIBLE);

            button.setText("Cerrar");
        }
        else if(tutorial == 4){
            popupWindow.dismiss();
        }
    }

}
