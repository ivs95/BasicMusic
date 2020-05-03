package com.example.prototipotfg.Examen.Ejercicios;

import android.content.Context;
import android.content.Intent;
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
    private boolean resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public void comprobar(View view){

        if (compruebaArrays()){
            for (Button b : this.botonesGuia){
                b.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
            }
            resultado=true;

        }
        else{
            for (Button b : this.botonesGuia) {
                b.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_red_500)));
            }
            resultado=false;
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
        findViewById(R.id.continuar_cr).setVisibility(View.VISIBLE);
        ((Button)findViewById(R.id.continuar_cr)).setText("Continuar");
        findViewById(R.id.continuar_cr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent();
                intent.putExtra("resultado",resultado);
                setResult(2,intent);
                finish();            }
        });

    }


}
