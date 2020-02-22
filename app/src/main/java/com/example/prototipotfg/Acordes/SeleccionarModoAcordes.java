package com.example.prototipotfg.Acordes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.prototipotfg.R;
import com.example.prototipotfg.SeleccionarDificultadAdivinar;

public class SeleccionarModoAcordes extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccion_modo_acordes);
    }

    public void modo_acordes(View view){
        //Intent i = new Intent(this, SeleccionarDificultadAdivinar.class);
        switch (view.getId()){
            case R.id.buttonAcordesModo1:
                Intent i = new Intent(this, SeleccionOctavasAcordes.class);
                i.putExtra("modo_acordes", "adivina_acorde");
                startActivity(i);
                break;

            case R.id.buttonAcordesModo2:
                i = new Intent(this, SeleccionarDificultadAdivinar.class);
                i.putExtra("modo_acordes", "adivina_intervalo");
                startActivity(i);
                break;

            default: break;
        }
    }
}
