package com.example.prototipotfg.Acordes.Adivinar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.prototipotfg.Acordes.TutorialAdivinarAcordes;
import com.example.prototipotfg.BBDD.Puntuacion;
import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Notas.TutorialNotas;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.Controlador;
import com.example.prototipotfg.Singletons.GestorBBDD;

public class SeleccionarNivelAdivinarAcordes extends Activity {


    private  Bundle savedInstanceState;
    @Override
    protected void onCreate(Bundle savedInstanceState){
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
        tutorial.setText("Ayuda");
        tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tutorial_notas(v);
            }
        });
        llBotonera.addView(tutorial);
        int nivelActual = GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Adivinar_Acordes.toString()).getNivel();

        //Creamos los botones en bucle
        for (int i = 0; i < 6; i++) {
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
                        nivel_seleccionado(v);
                }
            });
            //Añadimos el botón a la botonera
            llBotonera.addView(button);

            if(nivelActual < i+1) {
                button.setEnabled(false);
                button.setAlpha(.5f);
            }

        }

    }


    private void tutorial_notas(View v) {
        Intent i = new Intent(this, TutorialNivelAdivinarAcordes.class);
        startActivity(i);
    }

    public void nivel_seleccionado(View view) {
        Intent i = new Intent(this, ReproducirAdivinarAcordes.class);
        Controlador.getInstance().setNivel(view.getId());
        Controlador.getInstance().estableceDificultad();
        /*
         * Aquí hay que seleccionar la nota y las variables (strings de los nombre) y meterlas en el bundle
         * Crear clase para seleccionar notas aleatorias
         * Claves: respuesta, fallo1,...,falloN
         * */
        startActivity(i);
    }

    public void onResume(){
        super.onResume();
        this.onCreate(this.savedInstanceState);
    }
}
