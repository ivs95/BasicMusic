package com.example.prototipotfg.Examen.Ejercicios;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.core.content.ContextCompat;

import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Examen.ControladorExamen;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Ritmos.Crear.ImitarRitmo;
import com.example.prototipotfg.Singletons.Controlador;
import com.example.prototipotfg.Singletons.GestorBBDD;


public class ImitarRitmoExamen extends ImitarRitmo {
    private boolean resultado;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        if (GestorBBDD.getInstance().esPrimerNivelAdivinar(ModoJuego.Modo_Mix, ControladorExamen.getInstance().getNivel().getNivel()) && !Controlador.getInstance().getMixIniciado() && ControladorExamen.getInstance().getNivel().getNivel() != 1) {

            LayoutInflater inflater = (LayoutInflater)
                    getSystemService(LAYOUT_INFLATER_SERVICE);

            ModoJuego.mostrarPopUpNuevoNivel(inflater, ModoJuego.Modo_Mix, findViewById(android.R.id.content).getRootView());
        }
    }
    @Override
    public void comprobar(View view){
        para(view);
        int indice = 0;
        if (compruebaArrays()){
            for (Button b : this.botonesGuia){
                if(ritmos1.get(indice) == 1) {
                    if(resultado1.get(indice)==1)
                        b.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
                    else
                        b.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_red_500)));
                }
                indice++;
                if(indice == 16)
                    indice = 0;
            }
            resultado=true;
            TextView resultadoText = findViewById(R.id.textRitmosResultado2);
            resultadoText.setText("¡Bien hecho!\n");
            resultadoText.setTextSize(22);
            resultadoText.setVisibility(View.VISIBLE);
            resultadoText.setTextColor(getResources().getColor(R.color.md_green_500));

        }
        else{
            for (Button b : this.botonesGuia) {
                if(ritmos1.get(indice) == 1) {
                    if(resultado1.get(indice)==1)
                        b.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
                    else
                        b.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_red_500)));
                }
                indice++;
                if(indice == 16)
                    indice = 0;
            }
            resultado=false;
            TextView resultadoText = findViewById(R.id.textRitmosResultado2);
            resultadoText.setText("Prueba otra vez\n");
            resultadoText.setTextSize(22);
            resultadoText.setVisibility(View.VISIBLE);
            resultadoText.setTextColor(getResources().getColor(R.color.md_red_500));
        }

        Controlador.getInstance().setMixIniciado(true);
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
        Controlador.getInstance().setMixIniciado(true);
        ((Button)findViewById(R.id.continuar_cr)).setText("Continuar");
        findViewById(R.id.continuar_cr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent();
                intent.putExtra("resultado",resultado);
                setResult(RESULT_OK,intent);
                finish();            }
        });

    }


}
