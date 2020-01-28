package com.example.prototipotfg;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class SeleccionarAdivinar extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nivel_seleccionar_adivinar);
    }

    public void volverAtras(View view){
        finish();
    }
}
