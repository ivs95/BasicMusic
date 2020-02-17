package com.example.prototipotfg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class NivelesAdivinarNotas extends Activity {

    private String modo;
    private String dificultad;
    private String modo_intervalo;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opciones_adivinar);
        modo = getIntent().getExtras().getString("modo");
        dificultad = getIntent().getExtras().getString("dificultad");
        modo_intervalo = getIntent().getExtras().getString("modo_intervalo");

        //Obtenemos el linear layout donde colocar los botones
        LinearLayout llBotonera = (LinearLayout) findViewById(R.id.Botonera);

        //Creamos las propiedades de layout que tendrán los botones.
        //Son LinearLayout.LayoutParams porque los botones van a estar en un LinearLayout.
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT );

        //Creamos los botones en bucle
        for (int i=1; i<6; i++){
            Button button = new Button(this);
            button.setId(i+1);
            //Asignamos propiedades de layout al boton
            button.setLayoutParams(lp);
            //Asignamos Texto al botón
            button.setText(i+1 + " opciones");

            //Asignamose el Listener
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nivel_seleccionado(v);
                }
            });
            //Añadimos el botón a la botonera
            llBotonera.addView(button);
        }
    }

    public void nivel_seleccionado(View view) {
        Intent i = null;
        Random random = new Random();
        if (this.modo.equals("intervalos") && this.modo_intervalo.equals("adivina_intervalo")) {
            i = new Intent(this, ReproducirAdivinarIntervalo.class);
        }
        else if(this.modo.equals("intervalos") && this.modo_intervalo.equals("crea_intervalo")) {
            i = new Intent(this, ReproducirAdivinarCrearIntervalo.class);
            Intervalos[] intervalos_lista = new Intervalos[12];
            intervalos_lista = Intervalos.values();
            Intervalos intervalo = intervalos_lista[random.nextInt(12)];
            i.putExtra("peticion_nombre", intervalo.getNombre());
            i.putExtra("peticion_dif", intervalo.getDiferencia());
        }
        else
            i = new Intent(this, ReproducirAdivinar.class);

        //Nivel que se ha seleccionado
        int nivel = view.getId();
        ArrayList<Octavas> octavas = Octavas.devuelveOctavas(getIntent().getExtras().getStringArrayList("octavas"));
        HashMap<String, String> notas = null;

        try {
            if (this.modo.equals("intervalos")) {

                ArrayList<Octavas> octavas_intervalos = new ArrayList<Octavas>();
                octavas_intervalos.add(octavas.get(random.nextInt(octavas.size())));
                notas = FactoriaNotas.getInstance().getNumNotasAleatorias(view.getId(), Instrumentos.Piano, octavas_intervalos);
            } else
                notas = FactoriaNotas.getInstance().getNumNotasAleatorias(view.getId(), Instrumentos.Piano, octavas);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
         * Aquí hay que seleccionar la nota y las variables (strings de los nombre) y meterlas en el bundle
         * Crear clase para seleccionar notas aleatorias
         * Claves: respuesta, fallo1,...,falloN
         * */
        ArrayList<String> nombres = new ArrayList<>(notas.keySet());
        ArrayList<String> rutas = new ArrayList<>(notas.values());


        i.putExtra("nivel", nivel);
        i.putExtra("modo", modo);
        i.putExtra("dificultad", dificultad);
        i.putExtra("modo_intervalo", modo_intervalo);
        i.putStringArrayListExtra("nombres", nombres);
        i.putStringArrayListExtra("rutas", rutas);

        startActivity(i);
    }



}
