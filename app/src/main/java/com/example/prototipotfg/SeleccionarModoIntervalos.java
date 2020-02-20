package com.example.prototipotfg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.prototipotfg.Intervalos.SeleccionOctavasIntervalos;

public class SeleccionarModoIntervalos extends Activity {

    private String modo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccion_modo_intervalo);
        modo = getIntent().getExtras().getString("modo");
    }

    public void modo_intervalo(View view){
        //Intent i = new Intent(this, SeleccionarDificultadAdivinar.class);
        switch (view.getId()){
            case R.id.buttonIntervalosModo2:
                Intent i = new Intent(this, SeleccionOctavasIntervalos.class);
                i.putExtra("modo_intervalo", "adivina_intervalo");
                i.putExtra("modo", modo);
                startActivity(i);
                break;

            case R.id.buttonIntervalosModo1:
                i = new Intent(this, SeleccionOctavasIntervalos.class);
                i.putExtra("modo_intervalo", "crea_intervalo");
                i.putExtra("modo", modo);
                startActivity(i);
                break;

            default: break;
        }
    }
}
