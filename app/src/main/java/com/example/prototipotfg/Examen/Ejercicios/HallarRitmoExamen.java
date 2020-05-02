package com.example.prototipotfg.Examen.Ejercicios;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
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

    private boolean resultado;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
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
            resultado=true;

        else
            resultado=false;

        findViewById(R.id.continuar_hr).setVisibility(View.VISIBLE);
        ((Button)findViewById(R.id.continuar_hr)).setText("Continuar");
        findViewById(R.id.continuar_hr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("resultado",resultado);
                setResult(2,intent);
                finish();            }
        });



    }
}
