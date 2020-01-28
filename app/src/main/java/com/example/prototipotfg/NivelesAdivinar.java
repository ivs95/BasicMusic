package com.example.prototipotfg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class NivelesAdivinar extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.niveles_adivinar);
    }

    public void nivel_seleccionado(View view){
        Intent i = new Intent(this, ReproducirAdivinar.class);
        startActivity(i);
    }
}
