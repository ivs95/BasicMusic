package com.example.prototipotfg.Mix.Ejercicios;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.core.content.ContextCompat;

import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Singletons.ControladorMix;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Ritmos.Crear.ImitarRitmo;
import com.example.prototipotfg.Singletons.Controlador;
import com.example.prototipotfg.Singletons.GestorBBDD;


public class ImitarRitmoMix extends ImitarRitmo {

    private static final int MAX_BOTONES = 16;
    private boolean resultadoPrueba;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        if (GestorBBDD.getInstance().esPrimerNivelAdivinar(ModoJuego.Modo_Mix, ControladorMix.getInstance().getNivel().getNivel()) && !Controlador.getInstance().getMixIniciado() && ControladorMix.getInstance().getNivel().getNivel() != 1) {

            LayoutInflater inflater = (LayoutInflater)
                    getSystemService(LAYOUT_INFLATER_SERVICE);

            ModoJuego.mostrarPopUpNuevoNivel(inflater, ModoJuego.Modo_Mix, findViewById(android.R.id.content).getRootView(), false, 0, 0);
        }
        ((TextView)findViewById(R.id.lblIndice)).setText(ControladorMix.getInstance().getIndiceActual()+1 + "/" + ControladorMix.getInstance().getNumEjercicios());
        findViewById(R.id.lblIndice).setVisibility(View.VISIBLE);

    }
    @Override
    public void comprobar(View view) {
        findViewById(R.id.botonPalmada).setVisibility(View.GONE);
        findViewById(R.id.botonCaja).setVisibility(View.GONE);
        findViewById(R.id.botonTambor).setVisibility(View.GONE);
        findViewById(R.id.botonPlatillo).setVisibility(View.GONE);
        findViewById(R.id.botonPlayRitmoPropio).setVisibility(View.GONE);
        findViewById(R.id.botonResetRitmo).setVisibility(View.GONE);
        findViewById(R.id.botonCompararRitmos).setVisibility(View.GONE);
        mostrarSolucion();
        para(view);
        //comprobado = true;
        int indice = 0;
        if (compruebaArrays()) {
            resultadoPrueba = true;
            for (Button b : this.botonesGuia) {
                if (ritmos1.get(indice) == 1)
                    b.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
                else if (this.nivel > 2) {
                    if (ritmos2.get(indice) == 1) {
                        b.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
                    } else if (this.nivel > 4) {
                        if (ritmos3.get(indice) == 1)
                            b.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
                        else if (this.nivel > 6 && ritmos4.get(indice) == 1)
                            b.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
                    }
                }
                indice++;
            }

        } else {
            resultadoPrueba = false;
            for (Button b : this.botonesGuia) {
                boolean fallo = false;
                if (ritmos1.get(indice) != resultado1.get(indice)) {
                    b.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_red_500)));
                    fallo = true;
                }
                if (!fallo && this.nivel > 2 && ritmos2.get(indice) != resultado2.get(indice)) {
                    b.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_red_500)));
                    fallo = true;
                }
                if (!fallo && this.nivel > 4 && ritmos3.get(indice) != resultado3.get(indice)) {
                    b.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_red_500)));
                    fallo = true;
                }
                if (!fallo && this.nivel > 6 && ritmos4.get(indice) != resultado4.get(indice)) {
                    b.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_red_500)));
                    fallo = true;
                }
                if (!fallo && (resultado1.get(indice) == 1 || resultado2.get(indice) == 1 || resultado3.get(indice) == 1 || resultado4.get(indice) == 1)) {
                    b.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
                }

                indice++;
            }

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
        ((Button)findViewById(R.id.continuar_cr)).setText("Continuar");

        findViewById(R.id.continuar_cr).setVisibility(View.VISIBLE);

        Controlador.getInstance().setMixIniciado(true);
        findViewById(R.id.continuar_cr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("resultado",resultadoPrueba);
                setResult(RESULT_OK,intent);
                finish();                 }
        });
    }

    private void mostrarSolucion() {
        int numeroBotones = Controlador.getInstance().getLongitud();

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100, 1);
        lp.setMargins(5, 0, 5, 10);
        LinearLayout linear = findViewById(R.id.linearSoluciones);
        for (int i = 0; i < numeroBotones; i++)
            addBotonesSolucion((LinearLayout) linear.getChildAt(i), lp, i);
        for (int i = numeroBotones; i < MAX_BOTONES; i++)
            linear.getChildAt(i).setVisibility(View.GONE);
    }

    private void addBotonesSolucion(LinearLayout linear, LinearLayout.LayoutParams lp, int i) {
        int contador = 0;
        ImageView imagen = setImagen(R.drawable.palmas, lp);
        contador = pintaFigura(ritmos1.get(i), resultado1.get(i), imagen, linear, lp, contador);
        imagen = setImagen(R.drawable.caja, lp);
        if (nivel > 2)
            contador = pintaFigura(ritmos2.get(i), resultado2.get(i), imagen, linear, lp, contador);
        else
            pintaFiguraInvisible(imagen, linear, contador);
        imagen = setImagen(R.drawable.tambor, lp);
        if (nivel > 4)
            contador = pintaFigura(ritmos3.get(i), resultado3.get(i), imagen, linear, lp, contador);
        else
            pintaFiguraInvisible(imagen, linear, contador);
        imagen = setImagen(R.drawable.platillos, lp);
        if (nivel > 6)
            pintaFigura(ritmos4.get(i), resultado4.get(i), imagen, linear, lp, contador);

        else
            pintaFiguraInvisible(imagen, linear, contador);
    }

    private ImageView setImagen(int icono, LinearLayout.LayoutParams lp) {
        ImageView imagen = new ImageView(this);
        imagen.setLayoutParams(lp);
        imagen.setImageResource(icono);
        imagen.setScaleType(ImageView.ScaleType.FIT_XY);
        return imagen;
    }

    private void pintaFiguraInvisible(ImageView imagen, LinearLayout linear, int contador) {
        imagen.setVisibility(View.INVISIBLE);
        linear.addView(imagen, contador);
    }


    @SuppressLint("NewApi")
    private int pintaFigura(Integer solucion, Integer respuesta, ImageView imagen, LinearLayout linear, LinearLayout.LayoutParams lp, int indice) {
        imagen.setBackgroundColor(Color.BLUE);
        if (solucion == 1) {
            //Hay palmada
            if (respuesta == 1) {
                //Ha acertado, pintar en verde
                linear.addView(imagen, indice);
                imagen.setBackgroundColor(this.getColor(R.color.md_green_500));
                return indice + 1;
            } else {
                //No lo ha puesto, pintar en azul
                imagen.setBackgroundColor(this.getColor(R.color.md_blue_500));
                linear.addView(imagen, indice);
                return indice + 1;
            }
        } else {
            //No hay palmada
            if (respuesta == 1) {
                //Falla, pintar en rojo
                imagen.setBackgroundColor(this.getColor(R.color.md_red_500));
                linear.addView(imagen, indice);
                return indice + 1;
            }
            imagen.setVisibility(View.INVISIBLE);
            linear.addView(imagen, indice);
            return indice;

        }
    }


}
