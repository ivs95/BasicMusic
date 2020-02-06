package com.example.prototipotfg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SeleccionModoAdivinar extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccion_adivinar);
    }

    public void modo(View view){
        Intent i = new Intent(this, SeleccionarDificultadAdivinar.class);
        switch (view.getId()){
            case R.id.buttonNotas: i.putExtra("modo", "notas");break;

            case R.id.buttonAcordes: i.putExtra("modo", "acordes");break;

            case R.id.buttonIntervalos: i.putExtra("modo", "intervalos");break;

            default: break;
        }
        startActivity(i);
    }
}
