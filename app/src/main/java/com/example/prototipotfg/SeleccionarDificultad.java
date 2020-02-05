package com.example.prototipotfg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SeleccionarDificultad extends Activity {

    public String modo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccion_dificultad);
        modo = getIntent().getExtras().getString("modo");


    }

    public void modo(View view){
        Intent i = new Intent(this, NivelesAdivinar.class);
        Button boton = (Button)view;
        i.putExtra("dificultad", ((Button)view).getText());
        startActivity(i);
    }
}
