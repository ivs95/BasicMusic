package com.example.prototipotfg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuJugar extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_jugar);
    }

    public void modo_adivinar(View view){
        Intent i = new Intent(this, SeleccionModoAdivinar.class);
        startActivity(i);
    }

    public void modo_imitar(View view){
        Intent i = new Intent(this, SeleccionOctavas.class);
        i.putExtra("modo", "imitar");
        startActivity(i);
    }

    public void modo_ritmos(View view) {
        Intent i = new Intent(this, SeleccionModoRitmos.class);
        startActivity(i);
    }
}
