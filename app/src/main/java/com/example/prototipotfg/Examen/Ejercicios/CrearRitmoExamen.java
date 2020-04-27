package com.example.prototipotfg.Examen.Ejercicios;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.prototipotfg.BBDD.NivelAdivinar;
import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Enumerados.RangosPuntuaciones;
import com.example.prototipotfg.Examen.ControladorExamen;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Ritmos.Crear.CrearRitmo;
import com.example.prototipotfg.Singletons.Controlador;
import com.example.prototipotfg.Singletons.GestorBBDD;

import java.util.ArrayList;
import java.util.Random;

import static com.example.prototipotfg.Enumerados.DuracionSonido.getSonidoPorSimbolo;

public class CrearRitmoExamen extends CrearRitmo {

    @Override
    protected void onCreate(Bundle savedInstanceState){


        super.onCreate(savedInstanceState);
        setContentView(R.layout.crearritmos);

        nivel = Controlador.getInstance().getNivel();

        findViewById(R.id.continuar_cr).setEnabled(false);           findViewById(R.id.continuar_cr).setAlpha(.5f);

        Random random = new Random();
        ritmos1 = new ArrayList<>();
        ritmos2 = new ArrayList<>();
        ritmos3 = new ArrayList<>();
        ritmos4 = new ArrayList<>();
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

        if (nivel % 2 == 1){
            pausa = 700;
        }
        else
            pausa = 475;

        if(nivel == 8) pausa = 400;

        TextView titulo = findViewById(R.id.tituloCrearRitmo);
        titulo.setText(titulo.getText() + String.valueOf(nivel));
        LinearLayout guia = findViewById(R.id.linearRitmo);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,1 );
        running = false;
        hilo_ritmos.start();
        hiloPlayer1.start();
        hilo_ritmos_propio.start();
        mediaPlayerMetronomo.initMetronomo(this);
        mediaPlayer1.init1(this);
        if(nivel>2) {
            mediaPlayer2.init2(this);
            findViewById(R.id.botonCaja).setVisibility(View.VISIBLE);
            if(nivel>4) {
                mediaPlayer3.init3(this);
                findViewById(R.id.botonTambor).setVisibility(View.VISIBLE);
                if(nivel > 6) {
                    mediaPlayer4.init4(this);
                    findViewById(R.id.botonPlatillo).setVisibility(View.VISIBLE);
                }
            }
        }
        for (int i = 0; i < COMPASES; i++){
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

            Button button = new Button(this);
            button.setEnabled(false);
            button.setText("");
            button.setLayoutParams(lp);
            guia.addView(button);
            botonesGuia.add(button);
        }
        findViewById(R.id.botonStopRitmo).setEnabled(false);   findViewById(R.id.botonStopRitmo).setAlpha(.5f);
        findViewById(R.id.botonPalmada).setEnabled(false);   findViewById(R.id.botonPalmada).setAlpha(.5f);
        findViewById(R.id.botonCaja).setEnabled(false);   findViewById(R.id.botonCaja).setAlpha(.5f);
        findViewById(R.id.botonTambor).setEnabled(false);   findViewById(R.id.botonTambor).setAlpha(.5f);
        findViewById(R.id.botonPlatillo).setEnabled(false);   findViewById(R.id.botonPlatillo).setAlpha(.5f);
    }
    @Override
    public void comprobar(View view){

        if (compruebaArrays()){
            for (Button b : this.botonesGuia){
                b.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
            }
            ControladorExamen.getInstance().setResultadoEjercicioActual(true);

        }
        else{
            for (Button b : this.botonesGuia) {
                b.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_red_500)));
            }
            ControladorExamen.getInstance().setResultadoEjercicioActual(false);
        }


        view.setEnabled(false);
        view.setAlpha(0.5f);
        findViewById(R.id.botonPlayRitmo).setEnabled(false);
        findViewById(R.id.botonPlayRitmo).setAlpha(.5f);
        findViewById(R.id.botonStopRitmo).setEnabled(false);
        findViewById(R.id.botonStopRitmo).setAlpha(.5f);
        findViewById(R.id.botonPlayRitmoPropio).setEnabled(false);
        findViewById(R.id.botonPlayRitmoPropio).setAlpha(.5f);
        findViewById(R.id.botonResetRitmo).setEnabled(false);
        findViewById(R.id.botonResetRitmo).setAlpha(.5f);
        findViewById(R.id.botonPalmada).setEnabled(false);
        findViewById(R.id.botonPalmada).setAlpha(.5f);
        findViewById(R.id.botonCaja).setEnabled(false);
        findViewById(R.id.botonCaja).setAlpha(.5f);
        findViewById(R.id.botonTambor).setEnabled(false);
        findViewById(R.id.botonTambor).setAlpha(.5f);
        findViewById(R.id.botonPlatillo).setEnabled(false);
        findViewById(R.id.botonPlatillo).setAlpha(.5f);
        findViewById(R.id.continuar_cr).setEnabled(true);
        findViewById(R.id.continuar_cr).setAlpha(1);
        ((Button)findViewById(R.id.continuar_cr)).setText("Continuar");
        findViewById(R.id.continuar_cr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


}
