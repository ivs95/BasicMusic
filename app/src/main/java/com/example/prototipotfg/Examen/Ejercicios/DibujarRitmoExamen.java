package com.example.prototipotfg.Examen.Ejercicios;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.prototipotfg.BBDD.Modelo.NivelAdivinar;
import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Enumerados.RangosPuntuaciones;
import com.example.prototipotfg.Examen.ControladorExamen;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Ritmos.Hallar.DibujarRitmo;
import com.example.prototipotfg.Singletons.Controlador;
import com.example.prototipotfg.Singletons.GestorBBDD;

public class DibujarRitmoExamen extends DibujarRitmo {

    private boolean resultadoPrueba;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        if (GestorBBDD.getInstance().esPrimerNivelAdivinar(ModoJuego.Modo_Mix, ControladorExamen.getInstance().getNivel().getNivel()) && !Controlador.getInstance().getMixIniciado() && ControladorExamen.getInstance().getNivel().getNivel() != 1) {

            LayoutInflater inflater = (LayoutInflater)
                    getSystemService(LAYOUT_INFLATER_SERVICE);

            ModoJuego.mostrarPopUpNuevoNivel(inflater, ModoJuego.Modo_Mix, findViewById(android.R.id.content).getRootView(), false, 0, 0);
        }
        ((TextView)findViewById(R.id.lblIndice)).setText(ControladorExamen.getInstance().getIndiceActual()+1 + "/" + ControladorExamen.getInstance().getNumEjercicios());
        findViewById(R.id.lblIndice).setVisibility(View.VISIBLE);

    }
    @Override
    public void stop(View view){
        para(view);

        TextView resultado = findViewById(R.id.textRitmosResultado);

        this.comprobado = true;
        NivelAdivinar nivel;
        view.setEnabled(false);
        view.setAlpha(0.5f);
        int aciertos=0;
        if(resultado1.equals(ritmos1)){
            //Correct
            for(int i = 0; i<Controlador.getInstance().getLongitud(); i++){
                if(botonesSeleccionados1[i]!=null){
                    botonesSeleccionados1[i].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
                }
            }
            aciertos++;
        }
        else{
            //Incorrect
            for(int i = 0; i<Controlador.getInstance().getLongitud(); i++){
                if(botonesSeleccionados1[i]!=null){
                    botonesSeleccionados1[i].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_red_500)));
                }
                if(botonesResultado1[i]!=null)
                    botonesResultado1[i].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
            }

        }
        if(resultado2.equals(ritmos2)){
            //Correct
            for(int i = 0; i<Controlador.getInstance().getLongitud(); i++){
                if(botonesSeleccionados2[i]!=null){
                    botonesSeleccionados2[i].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
                }
            }
            aciertos++;
        }
        else{
            //Incorrect
            for(int i = 0; i<Controlador.getInstance().getLongitud(); i++){
                if(botonesSeleccionados2[i]!=null){
                    botonesSeleccionados2[i].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_red_500)));
                }
                if(botonesResultado2[i]!=null)
                    botonesResultado2[i].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
            }

        }
        if(resultado3.equals(ritmos3)){
            //Correct
            for(int i = 0; i<Controlador.getInstance().getLongitud(); i++){
                if(botonesSeleccionados3[i]!=null){
                    botonesSeleccionados3[i].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
                }
            }
            aciertos++;
        }
        else{
            //Incorrect
            for(int i = 0; i<Controlador.getInstance().getLongitud(); i++){
                if(botonesSeleccionados3[i]!=null){
                    botonesSeleccionados3[i].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_red_500)));
                }
                if(botonesResultado3[i]!=null)
                    botonesResultado3[i].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
            }
        }
        if(resultado4.equals(ritmos4)){
            //Correct
            for(int i = 0; i<Controlador.getInstance().getLongitud(); i++){
                if(botonesSeleccionados4[i]!=null){
                    botonesSeleccionados4[i].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
                }

            }
            aciertos++;
        }
        else{
            //Incorrect
            for(int i = 0; i<Controlador.getInstance().getLongitud(); i++){
                if(botonesSeleccionados4[i]!=null){
                    botonesSeleccionados4[i].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_red_500)));
                }
                if(botonesResultado4[i]!=null)
                    botonesResultado4[i].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
            }

        }
        if((aciertos==1 && this.nivel<3) || (aciertos==2 && this.nivel>2 && this.nivel < 5) ||(aciertos==3 && this.nivel >= 5 && this.nivel < 7) ||(aciertos==4 && this.nivel>=7))
            resultadoPrueba = true;

        else
            resultadoPrueba = false;



        Controlador.getInstance().setMixIniciado(true);
        ((Button)findViewById(R.id.continuar_hr)).setText("Continuar");
        findViewById(R.id.continuar_hr).setVisibility(View.VISIBLE);
        findViewById(R.id.continuar_hr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("resultado",resultadoPrueba);
                setResult(RESULT_OK,intent);
                finish();                 }
        });
    }

}
