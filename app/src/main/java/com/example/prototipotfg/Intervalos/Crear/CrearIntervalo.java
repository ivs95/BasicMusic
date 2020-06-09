package com.example.prototipotfg.Intervalos.Crear;


import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.prototipotfg.BBDD.Entidades.NivelAdivinar;
import com.example.prototipotfg.Enumerados.Dificultad;
import com.example.prototipotfg.Enumerados.Instrumentos;
import com.example.prototipotfg.Enumerados.Intervalos;
import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Enumerados.Notas;
import com.example.prototipotfg.Enumerados.Octavas;
import com.example.prototipotfg.Enumerados.RangosPuntuaciones;
import com.example.prototipotfg.Mix.Ejercicios.CrearIntervaloMix;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.Controlador;
import com.example.prototipotfg.Singletons.FactoriaNotas;
import com.example.prototipotfg.Singletons.GestorBBDD;
import com.example.prototipotfg.Singletons.Reproductor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static android.view.View.VISIBLE;

public class CrearIntervalo extends Activity {

    protected View botonSeleccionado;


    protected View respuestaCorrecta;
    private boolean comprobada = false;

    protected ArrayList<Pair<Notas,Octavas>> notasIntervalo = new ArrayList<>();
    protected ArrayList<String> notasPosibles = new ArrayList<>();
    protected ArrayList<Button> botonesOpciones = new ArrayList<>();
    protected Notas notaInicio;
    protected Octavas octavaInicio;
    protected Octavas octavaReproducir;
    protected int num_opciones;

    protected String intervalo_nombre;
    protected int intervalo_dif;

    protected String respuesta;
    protected String respuesta_correcta;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nivel_crear_intervalo);
        Random r = new Random();
        this.num_opciones = Controlador.getInstance().getNum_opciones();
        notasIntervalo = FactoriaNotas.getInstance().getNotasIntervalo(Controlador.getInstance().getOctavas(), Controlador.getInstance().getRango());
        this.notaInicio =  notasIntervalo.get(0).first;
        this.octavaInicio = notasIntervalo.get(0).second;
        this.octavaReproducir = octavaInicio;
        int tono1 = notasIntervalo.get(0).first.getTono();
        int tono2 = notasIntervalo.get(1).first.getTono();
        Intervalos intervalo = getIntervaloConDif((tono2-tono1));
        this.notasPosibles = seleccionaNotasAleatorios(intervalo);

        intervalo_nombre = intervalo.getNombre();
        intervalo_dif = intervalo.getDiferencia();


        TextView nota = (TextView)findViewById(R.id.Id_nota_intervalo);
        nota.setText(nota.getText() + notasIntervalo.get(0).first.getNombre() + notasIntervalo.get(0).second.getOctava());

        TextView peticion = (TextView)findViewById(R.id.Id_intervalo);
        peticion.setText(peticion.getText() + intervalo_nombre);
        if (Controlador.getInstance().getNivel() == 1){
            peticion.setText(peticion.getText().toString() + " (" + intervalo_dif + " semitonos)");
        }

        if(Controlador.getInstance().getDificultad().equals(Dificultad.Dificil))
            this.adaptaVistaDificil();


        //Obtenemos el linear layout donde colocar los botones
        LinearLayout opciones = (LinearLayout) findViewById(R.id.opciones_crear_intervalo);

        //Creamos las propiedades de layout que tendrán los botones.
        //Son LinearLayout.LayoutParams porque los botones van a estar en un LinearLayout.
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT );
        lp.setMargins(0,0,0,50);
        Random rand = new Random();
        ArrayList <Integer> aux = new ArrayList<Integer>();

        for(int i = 0; i < num_opciones; i++) {
            aux.add(i);
        }

        Collections.shuffle(aux);
        //Creamos los botones en bucle
        for (int i=0; i<num_opciones; i++){
            Button button = new Button(this);
            button.setId(i+1);
            //Asignamos propiedades de layout al boton
            button.setLayoutParams(lp);
            //Asignamos Texto al botón
            button.setText(notasPosibles.get(aux.get(i)));

            //Asignamose el Listener
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    respuesta_seleccionada(v);
                }
            });
            //Añadimos el botón a la botonera
            button.setPadding(0,0,0,0);
            botonesOpciones.add(button);
            opciones.addView(button);
            if (button.getText().toString().equals(notasIntervalo.get(1).first.getNombre() + notasIntervalo.get(1).second.getOctava())){
                this.respuestaCorrecta=button;
                respuesta_correcta = button.getText().toString();
            }
        }
        if (!(this instanceof CrearIntervaloMix)) {
            if (GestorBBDD.getInstance().esPrimerNivelAdivinar(Controlador.getInstance().getModo_juego(), Controlador.getInstance().getNivel()) && Controlador.getInstance().getNivel() != 1) {

                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);

                ModoJuego.mostrarPopUpNuevoNivel(inflater, ModoJuego.Crear_Intervalo, findViewById(android.R.id.content).getRootView(), false, 0, 0);
            }
        }
    }
    protected ArrayList<String> seleccionaNotasAleatorios(Intervalos intervalo) {
        Notas[] notas = new Notas[12];
        boolean siguiente_octava = false;
        ArrayList<Octavas> octavas = Controlador.getInstance().getOctavas();
        notas = Notas.values();
        ArrayList<String> retorno = new ArrayList<>();
        Random random = new Random();
        int i = 1;
        String nota;
        nota = notasIntervalo.get(1).first.getNombre()+notasIntervalo.get(1).second.getOctava();
        retorno.add(nota);

        for (i = i; i < num_opciones; i++) {

            if(intervalo.getDiferencia() < 0 && retorno.size() > (this.notaInicio.getTono()-2) && !siguiente_octava){
                this.octavaInicio = this.octavaInicio.devuelveAnteriorOctava(this.octavaInicio);
                siguiente_octava = true;

            }
            else if(intervalo.getDiferencia() > 0 && retorno.size() > (12 - this.notaInicio.getTono()-2) && !siguiente_octava){
                this.octavaInicio = this.octavaInicio.devuelveSiguienteOctava(this.octavaInicio);
                siguiente_octava = true;

            }
            if(intervalo.getDiferencia() > 0 && !siguiente_octava)
                nota = notas[random.nextInt((notas.length - notaInicio.getTono())) + notaInicio.getTono()].getNombre();
            else if(intervalo.getDiferencia() < 0 && !siguiente_octava)
                nota = notas[random.nextInt(notaInicio.getTono())].getNombre();
            else if(siguiente_octava)
                nota = notas[random.nextInt(12)].getNombre();


            while (retorno.contains(nota+this.octavaInicio.getOctava())
                    || nota == notasIntervalo.get(1).first.getNombre() || nota == notasIntervalo.get(0).first.getNombre()) {

                if(intervalo.getDiferencia() > 0 && !siguiente_octava)
                    nota = notas[random.nextInt((notas.length - notaInicio.getTono())) + notaInicio.getTono()].getNombre();
                else if(intervalo.getDiferencia() < 0 && !siguiente_octava)
                    nota = notas[random.nextInt(notaInicio.getTono())].getNombre();
                else if(siguiente_octava)
                    nota = notas[random.nextInt(12)].getNombre();
            }
            retorno.add(nota+this.octavaInicio.getOctava());
        }
        return retorno;
    }

    private Octavas devuelveOctavaAleatoria(ArrayList<Octavas> octavas) {
        Random random = new Random();
        return octavas.get(random.nextInt(octavas.size()));
    }

    public void respuesta_seleccionada(View view){
        if (!comprobada) {
            Button b = (Button) view;
            if (botonSeleccionado != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    botonSeleccionado.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_blue_300)));
                }
            }
            botonSeleccionado = b;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                botonSeleccionado.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_blue_700)));
            }

            respuesta = b.getText().toString();
                String ruta = devuelveRutaBoton(respuesta);
                try {
                    AssetFileDescriptor afd = getAssets().openFd(ruta);
                    Reproductor.getInstance().reproducirNota(afd);
                    afd.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            ponerComprobarVisible(VISIBLE);
            findViewById(R.id.comprobar_crear_intervalo).setEnabled(true);

        }
    }

    private String devuelveRutaBoton(String text) {
        Notas n = Notas.devuelveNotaPorNombre(text.substring(0, text.length()-1));
        Octavas o = Octavas.devuelveOctavaPorNumero(Integer.parseInt(text.substring(text.length()-1)));
        return FactoriaNotas.getInstance().getInstrumento().getPath()+o.getPath()+n.getPath();
    }


    public void volverAtras(View view){
        finish();
    }

    public void reproducirReferencia(View view) throws IOException {
        FactoriaNotas.getInstance().setReferencia(this.octavaReproducir);
        AssetFileDescriptor afd = getAssets().openFd(FactoriaNotas.getInstance().getReferencia());
        Reproductor.getInstance().reproducirNota(afd);
        afd.close();
    }


    protected void ponerComprobarVisible(int visible) {
        findViewById(R.id.comprobar_crear_intervalo).setVisibility(visible);
    }

    public void reproducir(View view) throws IOException {
        AssetFileDescriptor afd = getAssets().openFd(Instrumentos.Piano.getPath() + this.octavaReproducir.getPath() + this.notaInicio.getPath());
        Reproductor.getInstance().reproducirNota(afd);
        afd.close();

    }



    public void comprobarResultado(View view) {
        int nivelActual = GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Crear_Intervalo.toString()).getNivel();
        int rangoActual = RangosPuntuaciones.getRangoPorNombre(GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Crear_Intervalo.toString()).getRango()).ordinal();

        if (!comprobada) {
            NivelAdivinar nivel = null;
            this.comprobada = true;
            if (respuesta != respuesta_correcta) {
                if(Controlador.getInstance().getNivel() == GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Crear_Intervalo.toString()).getNivel())
                    GestorBBDD.getInstance().actualizarPuntuacion(Controlador.getInstance().getNivel(), ModoJuego.Crear_Intervalo.toString(), false);
                nivel = new NivelAdivinar(ModoJuego.Crear_Intervalo.getNombre(), Controlador.getInstance().getNivel(), false, GestorBBDD.getInstance().getUsuarioLoggeado().getCorreo(), 0, 1);
                botonSeleccionado.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_red_500)));


            }
            else {
                if(Controlador.getInstance().getNivel() == GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Crear_Intervalo.toString()).getNivel())
                    GestorBBDD.getInstance().actualizarPuntuacion(Controlador.getInstance().getNivel(), ModoJuego.Crear_Intervalo.toString(), true);
                nivel = new NivelAdivinar(ModoJuego.Crear_Intervalo.getNombre(), Controlador.getInstance().getNivel(), true, GestorBBDD.getInstance().getUsuarioLoggeado().getCorreo(), 1, 0);

            }
            respuestaCorrecta.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));

            GestorBBDD.getInstance().insertaNivelAdivinar(nivel);
            findViewById(R.id.comprobar_crear_intervalo).setVisibility(View.GONE);
            findViewById(R.id.Id_boton_reproduce_nota_intervalo).setEnabled(false);  findViewById(R.id.Id_boton_reproduce_nota_intervalo).setAlpha(.5f);
            findViewById(R.id.botonReferencia).setEnabled(false);                    findViewById(R.id.botonReferencia).setAlpha(.5f);

            for (Button b : botonesOpciones){
                b.setEnabled(false);
            }

            int nivelNuevo = GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Crear_Intervalo.toString()).getNivel();
            int rangoNuevo = RangosPuntuaciones.getRangoPorNombre(GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Crear_Intervalo.toString()).getRango()).ordinal();
            if(rangoActual != rangoNuevo) {
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                RangosPuntuaciones.mostrar_popUp_rango(view, rangoActual, rangoNuevo, inflater, ModoJuego.Crear_Intervalo.toString());
            }

            if(nivelActual != nivelNuevo){
                Controlador.getInstance().setNivel(nivelNuevo);
                LayoutInflater inflater = (LayoutInflater)
                            getSystemService(LAYOUT_INFLATER_SERVICE);

                ModoJuego.mostrarPopUpNuevoNivel(inflater, ModoJuego.Crear_Intervalo, findViewById(android.R.id.content).getRootView(), true, nivelActual, nivelNuevo);

                Controlador.getInstance().estableceDificultad();
            }

            findViewById(R.id.continuar_ci).setVisibility(VISIBLE);
        }
    }

    protected void adaptaVistaDificil() {
        TextView nota = findViewById(R.id.Id_nota_intervalo);
        nota.setVisibility(View.GONE);
    }

    public Intervalos getIntervaloConDif(int dif){
        boolean OK = false;
        int i = 0;
        Intervalos[] intervalos_lista = Intervalos.values();
        while(i < 24 && !OK){
            if(intervalos_lista[i].getDiferencia() == dif)
                OK = true;
            i++;
        }

        return intervalos_lista[i-1];
    }

    public void continuar(View view){
        finish();
        overridePendingTransition( 0, 0);
        startActivity(getIntent());
        overridePendingTransition( 0, 0);    }
}
