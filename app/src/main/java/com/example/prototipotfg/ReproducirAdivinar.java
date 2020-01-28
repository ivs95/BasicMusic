package com.example.prototipotfg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ReproducirAdivinar extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nivel_reproducir_adivinar);
    }

    public void reproducir(View view){
        Intent i = new Intent(this, SeleccionarAdivinar.class);
        startActivity(i);
    }
}
