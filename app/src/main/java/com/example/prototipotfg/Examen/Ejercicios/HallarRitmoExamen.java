package com.example.prototipotfg.Examen.Ejercicios;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.prototipotfg.BBDD.NivelAdivinar;
import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Enumerados.RangosPuntuaciones;
import com.example.prototipotfg.Examen.ControladorExamen;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Ritmos.Hallar.HallarRitmo;
import com.example.prototipotfg.Singletons.Controlador;
import com.example.prototipotfg.Singletons.GestorBBDD;

import java.util.ArrayList;
import java.util.Random;

import static com.example.prototipotfg.Enumerados.DuracionSonido.getSonidoPorSimbolo;

public class HallarRitmoExamen extends HallarRitmo {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hallaritmos);
        this.savedInstanceState = savedInstanceState;

        findViewById(R.id.continuar_hr).setEnabled(false);
        findViewById(R.id.continuar_hr).setAlpha(.5f);

        Random random = new Random();
        ritmos1 = new ArrayList<>(longitud);
        ritmos2 = new ArrayList<>(longitud);
        ritmos3 = new ArrayList<>(longitud);
        ritmos4 = new ArrayList<>(longitud);
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

        nivel = Controlador.getInstance().getNivel();

        if (nivel % 2 == 0){
            pausa = 325;
        }
        else{
            pausa = 600;
        }

        if(nivel == 8) pausa = 275;

        TextView titulo = (TextView)findViewById(R.id.tituloHallaRitmo);
        titulo.setText(titulo.getText() + String.valueOf(nivel));

        running = false;
        hilo_ritmos.start();
        hiloPlayer1.start();

        mediaPlayerMetronomo.initMetronomo(this);
        mediaPlayer1.init1(this);
        findViewById(R.id.textRitmos1).setVisibility(View.VISIBLE);
        if(nivel>2) {
            //hiloPlayer2.start();
            mediaPlayer2.init2(this);
            findViewById(R.id.textRitmos2).setVisibility(View.VISIBLE);
            if(nivel>4) {
                //hiloPlayer3.start();
                mediaPlayer3.init3(this);
                findViewById(R.id.textRitmos3).setVisibility(View.VISIBLE);
                if(nivel > 6) {
                    //   hiloPlayer4.start();
                    mediaPlayer4.init4(this);
                    findViewById(R.id.textRitmos4).setVisibility(View.VISIBLE);
                }
            }
        }

        final Context contexto = this;


        //Obtenemos el linear layout donde colocar los botones
        LinearLayout llBotonera1 = (LinearLayout) findViewById(R.id.botoneraRitmos1);
        LinearLayout llBotonera2 = (LinearLayout) findViewById(R.id.botoneraRitmos2);
        LinearLayout llBotonera3 = (LinearLayout) findViewById(R.id.botoneraRitmos3);
        LinearLayout llBotonera4 = (LinearLayout) findViewById(R.id.botoneraRitmos4);
        //Creamos las propiedades de layout que tendrán los botones.
        //Son LinearLayout.LayoutParams porque los botones van a estar en un LinearLayout.
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(80, LinearLayout.LayoutParams.WRAP_CONTENT);
        for(int j=0; j<(nivel+1)/2; j++) {
            //Creamos los botones en bucle
            final int finalJ = j;
            for (int i = 0; i < 16; i++) {
                final Button button = new Button(this);
                button.setId(i + 1);
                //Asignamos propiedades de layout al boton
                button.setLayoutParams(lp);
                //Asignamos el Listener
                final int finalI = i;
                //Añadimos el botón a la botonera
                if (i < 4) {

                    if(!comprobado) {
                        button.setOnClickListener(new View.OnClickListener() {
                            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                            @Override
                            public void onClick(View v) {
                                if (!comprobado) {
                                    if (finalJ == 0) {
                                        if (resultado1.get((int) button.getId() - 1) == 1) {

                                            button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_blue_300)));

                                            resultado1.set(((int) button.getId() - 1), 0);
                                            botonesSeleccionados1[(int) button.getId() - 1] = null;
                                        } else {

                                            button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_blue_700)));

                                            resultado1.set(((int) button.getId() - 1), 1);
                                            botonesSeleccionados1[(int) button.getId() - 1] = button;
                                        }
                                    }
                                    if (finalJ == 1) {
                                        if (resultado2.get((int) button.getId() - 1) == 1) {

                                            button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_blue_300)));

                                            resultado2.set(((int) button.getId() - 1), 0);
                                            botonesSeleccionados2[(int) button.getId() - 1] = null;
                                        } else {

                                            button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_blue_700)));

                                            resultado2.set(((int) button.getId() - 1), 1);
                                            botonesSeleccionados2[(int) button.getId() - 1] = button;
                                        }
                                    }
                                    if (finalJ == 2) {
                                        if (resultado3.get((int) button.getId() - 1) == 1) {

                                            button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_blue_300)));

                                            resultado3.set(((int) button.getId() - 1), 0);
                                            botonesSeleccionados3[(int) button.getId() - 1] = null;
                                        } else {

                                            button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_blue_700)));

                                            resultado3.set(((int) button.getId() - 1), 1);
                                            botonesSeleccionados3[(int) button.getId() - 1] = button;
                                        }
                                    }
                                    if (finalJ == 3) {
                                        if (resultado4.get((int) button.getId() - 1) == 1) {

                                            button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_blue_300)));

                                            resultado4.set(((int) button.getId() - 1), 0);
                                            botonesSeleccionados4[(int) button.getId() - 1] = null;
                                        } else {

                                            button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_blue_700)));

                                            resultado4.set(((int) button.getId() - 1), 1);
                                            botonesSeleccionados4[(int) button.getId() - 1] = button;
                                        }
                                    }
                                }
                            }
                        });
                    }
                    if(j==0) {
                        llBotonera1.addView(button);
                        if(ritmos1.get(i)==1){
                            botonesResultado1[(int) button.getId() - 1]=button;
                        }
                    }
                    else if(j==1) {
                        llBotonera4.addView(button);
                        if(ritmos2.get(i)==1){
                            botonesResultado2[(int) button.getId() - 1]=button;
                        }
                    }
                    else if(j==2) {
                        llBotonera3.addView(button);
                        if(ritmos3.get(i)==1){
                            botonesResultado3[(int) button.getId() - 1]=button;
                        }
                    }
                    else if(j==3) {
                        llBotonera2.addView(button);
                        if(ritmos4.get(i)==1){
                            botonesResultado4[(int) button.getId() - 1]=button;
                        }
                    }
                } else if (i >= 4 && i < 8) {

                    button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_cyan_300)));

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!comprobado) {
                                if (finalJ == 0) {
                                    if (resultado1.get((int) button.getId() - 1) == 1) {

                                        button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_cyan_300)));

                                        resultado1.set(((int) button.getId() - 1), 0);
                                        botonesSeleccionados1[(int) button.getId() - 1] = null;
                                    } else {

                                        button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_cyan_800)));

                                        resultado1.set(((int) button.getId() - 1), 1);
                                        botonesSeleccionados1[(int) button.getId() - 1] = button;
                                    }
                                }
                                if (finalJ == 1) {
                                    if (resultado2.get((int) button.getId() - 1) == 1) {

                                        button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_cyan_300)));

                                        resultado2.set(((int) button.getId() - 1), 0);
                                        botonesSeleccionados2[(int) button.getId() - 1] = null;
                                    } else {

                                        button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_cyan_800)));

                                        resultado2.set(((int) button.getId() - 1), 1);
                                        botonesSeleccionados2[(int) button.getId() - 1] = button;
                                    }
                                }
                                if (finalJ == 2) {
                                    if (resultado3.get((int) button.getId() - 1) == 1) {

                                        button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_cyan_300)));

                                        resultado3.set(((int) button.getId() - 1), 0);
                                        botonesSeleccionados3[(int) button.getId() - 1] = null;
                                    } else {

                                        button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_cyan_800)));

                                        resultado3.set(((int) button.getId() - 1), 1);
                                        botonesSeleccionados3[(int) button.getId() - 1] = button;
                                    }
                                }
                                if (finalJ == 3) {
                                    if (resultado4.get((int) button.getId() - 1) == 1) {

                                        button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_cyan_300)));

                                        resultado4.set(((int) button.getId() - 1), 0);
                                        botonesSeleccionados4[(int) button.getId() - 1] = null;
                                    } else {

                                        button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_cyan_800)));

                                        resultado4.set(((int) button.getId() - 1), 1);
                                        botonesSeleccionados4[(int) button.getId() - 1] = button;
                                    }
                                }

                            }
                        }
                    });
                    if(j==0) {
                        llBotonera1.addView(button);
                        if(ritmos1.get(i)==1){
                            botonesResultado1[(int) button.getId() - 1]=button;
                        }
                    }
                    else if(j==1) {
                        llBotonera4.addView(button);
                        if(ritmos2.get(i)==1){
                            botonesResultado2[(int) button.getId() - 1]=button;
                        }
                    }
                    else if(j==2) {
                        llBotonera3.addView(button);
                        if(ritmos3.get(i)==1){
                            botonesResultado3[(int) button.getId() - 1]=button;
                        }
                    }
                    else if(j==3) {
                        llBotonera2.addView(button);
                        if(ritmos4.get(i)==1){
                            botonesResultado4[(int) button.getId() - 1]=button;
                        }
                    }
                } else if (i >= 8 && i < 12) {
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!comprobado) {
                                if (finalJ == 0) {
                                    if (resultado1.get((int) button.getId() - 1) == 1) {

                                        button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_blue_300)));

                                        resultado1.set(((int) button.getId() - 1), 0);
                                        botonesSeleccionados1[(int) button.getId() - 1] = null;
                                    } else {

                                        button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_blue_700)));

                                        resultado1.set(((int) button.getId() - 1), 1);
                                        botonesSeleccionados1[(int) button.getId() - 1] = button;
                                    }
                                }
                                if (finalJ == 1) {
                                    if (resultado2.get((int) button.getId() - 1) == 1) {

                                        button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_blue_300)));

                                        resultado2.set(((int) button.getId() - 1), 0);
                                        botonesSeleccionados2[(int) button.getId() - 1] = null;
                                    } else {

                                        button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_blue_700)));

                                        resultado2.set(((int) button.getId() - 1), 1);
                                        botonesSeleccionados2[(int) button.getId() - 1] = button;
                                    }
                                }
                                if (finalJ == 2) {
                                    if (resultado3.get((int) button.getId() - 1) == 1) {

                                        button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_blue_300)));

                                        resultado3.set(((int) button.getId() - 1), 0);
                                        botonesSeleccionados3[(int) button.getId() - 1] = null;
                                    } else {

                                        button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_blue_700)));

                                        resultado3.set(((int) button.getId() - 1), 1);
                                        botonesSeleccionados3[(int) button.getId() - 1] = button;
                                    }
                                }
                                if (finalJ == 3) {
                                    if (resultado4.get((int) button.getId() - 1) == 1) {

                                        button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_blue_300)));

                                        resultado4.set(((int) button.getId() - 1), 0);
                                        botonesSeleccionados4[(int) button.getId() - 1] = null;
                                    } else {

                                        button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_blue_700)));

                                        resultado4.set(((int) button.getId() - 1), 1);
                                        botonesSeleccionados4[(int) button.getId() - 1] = button;
                                    }
                                }

                            }
                        }
                    });
                    if(j==0) {
                        llBotonera1.addView(button);
                        if(ritmos1.get(i)==1){
                            botonesResultado1[(int) button.getId() - 1]=button;
                        }
                    }
                    else if(j==1) {
                        llBotonera4.addView(button);
                        if(ritmos2.get(i)==1){
                            botonesResultado2[(int) button.getId() - 1]=button;
                        }
                    }
                    else if(j==2) {
                        llBotonera3.addView(button);
                        if(ritmos3.get(i)==1){
                            botonesResultado3[(int) button.getId() - 1]=button;
                        }
                    }
                    else if(j==3) {
                        llBotonera2.addView(button);
                        if(ritmos4.get(i)==1){
                            botonesResultado4[(int) button.getId() - 1]=button;
                        }
                    }
                } else if (i >= 12 && i < 16) {
                    button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_cyan_300)));
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!comprobado) {
                                if (finalJ == 0) {
                                    if (resultado1.get((int) button.getId() - 1) == 1) {

                                        button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_cyan_300)));

                                        resultado1.set(((int) button.getId() - 1), 0);
                                        botonesSeleccionados1[(int) button.getId() - 1] = null;
                                    } else {

                                        button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_cyan_800)));

                                        resultado1.set(((int) button.getId() - 1), 1);
                                        botonesSeleccionados1[(int) button.getId() - 1] = button;
                                    }
                                }
                                if (finalJ == 1) {
                                    if (resultado2.get((int) button.getId() - 1) == 1) {

                                        button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_cyan_300)));

                                        resultado2.set(((int) button.getId() - 1), 0);
                                        botonesSeleccionados2[(int) button.getId() - 1] = null;
                                    } else {

                                        button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_cyan_800)));

                                        resultado2.set(((int) button.getId() - 1), 1);
                                        botonesSeleccionados2[(int) button.getId() - 1] = button;
                                    }
                                }
                                if (finalJ == 2) {
                                    if (resultado3.get((int) button.getId() - 1) == 1) {

                                        button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_cyan_300)));

                                        resultado3.set(((int) button.getId() - 1), 0);
                                        botonesSeleccionados3[(int) button.getId() - 1] = null;
                                    } else {

                                        button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_cyan_800)));

                                        resultado3.set(((int) button.getId() - 1), 1);
                                        botonesSeleccionados3[(int) button.getId() - 1] = button;
                                    }
                                }
                                if (finalJ == 3) {
                                    if (resultado4.get((int) button.getId() - 1) == 1) {

                                        button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_cyan_300)));

                                        resultado4.set(((int) button.getId() - 1), 0);
                                        botonesSeleccionados4[(int) button.getId() - 1] = null;
                                    } else {

                                        button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_cyan_800)));

                                        resultado4.set(((int) button.getId() - 1), 1);
                                        botonesSeleccionados4[(int) button.getId() - 1] = button;
                                    }
                                }

                            }
                        }
                    });
                    if(j==0) {
                        llBotonera1.addView(button);
                        if(ritmos1.get(i)==1){
                            botonesResultado1[(int) button.getId() - 1]=button;
                        }
                    }
                    else if(j==1) {
                        llBotonera4.addView(button);
                        if(ritmos2.get(i)==1){
                            botonesResultado2[(int) button.getId() - 1]=button;
                        }
                    }
                    else if(j==2) {
                        llBotonera3.addView(button);
                        if(ritmos3.get(i)==1){
                            botonesResultado3[(int) button.getId() - 1]=button;
                        }
                    }
                    else if(j==3) {
                        llBotonera2.addView(button);
                        if(ritmos4.get(i)==1){
                            botonesResultado4[(int) button.getId() - 1]=button;
                        }
                    }
                }
                //Aprovecho el bucle para rellenar el array
                resultado1.add(0);
                resultado2.add(0);
                resultado3.add(0);
                resultado4.add(0);
                if(i%4==0){
                    metronomo.add(i,1);
                }
                else{
                    metronomo.add(i,0);
                }
            }
        }

    }
    @Override
    public void stop(View view){
        para(view);

        this.comprobado = true;
        view.setEnabled(false);
        view.setAlpha(0.5f);
        int aciertos=0;
        if(resultado1.equals(ritmos1)){
            //Correct
            for(int i = 0; i<16; i++){
                if(botonesSeleccionados1[i]!=null){
                    botonesSeleccionados1[i].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
                }
            }
            aciertos++;
        }
        else{
            //Incorrect
            for(int i = 0; i<16; i++){
                if(botonesSeleccionados1[i]!=null){
                    botonesSeleccionados1[i].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_red_500)));
                }
                if(botonesResultado1[i]!=null)
                    botonesResultado1[i].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
            }

        }
        if(resultado2.equals(ritmos2)){
            //Correct
            for(int i = 0; i<16; i++){
                if(botonesSeleccionados2[i]!=null){
                    botonesSeleccionados2[i].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
                }
            }
            aciertos++;
        }
        else{
            //Incorrect
            for(int i = 0; i<16; i++){
                if(botonesSeleccionados2[i]!=null){
                    botonesSeleccionados2[i].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_red_500)));
                }
                if(botonesResultado2[i]!=null)
                    botonesResultado2[i].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
            }

        }
        if(resultado3.equals(ritmos3)){
            //Correct
            for(int i = 0; i<16; i++){
                if(botonesSeleccionados3[i]!=null){
                    botonesSeleccionados3[i].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
                }
            }
            aciertos++;
        }
        else{
            //Incorrect
            for(int i = 0; i<16; i++){
                if(botonesSeleccionados3[i]!=null){
                    botonesSeleccionados3[i].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_red_500)));
                }
                if(botonesResultado3[i]!=null)
                    botonesResultado3[i].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
            }
        }
        if(resultado4.equals(ritmos4)){
            //Correct
            for(int i = 0; i<16; i++){
                if(botonesSeleccionados4[i]!=null){
                    botonesSeleccionados4[i].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
                }

            }
            aciertos++;
        }
        else{
            //Incorrect
            for(int i = 0; i<16; i++){
                if(botonesSeleccionados4[i]!=null){
                    botonesSeleccionados4[i].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_red_500)));
                }
                if(botonesResultado4[i]!=null)
                    botonesResultado4[i].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
            }

        }
        if(aciertos == 4)
            ControladorExamen.getInstance().setResultadoEjercicioActual(true);

        else
            ControladorExamen.getInstance().setResultadoEjercicioActual(false);

        findViewById(R.id.continuar_hr).setEnabled(true);
        findViewById(R.id.continuar_hr).setAlpha(1);
        ((Button)findViewById(R.id.continuar_hr)).setText("Continuar");
        findViewById(R.id.continuar_hr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
}
