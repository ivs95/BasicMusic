package com.example.prototipotfg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prototipotfg.Singletons.GestorBBDD;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GestorBBDD.getInstance().setContexto(getApplicationContext());
    }


    public void ejecutar_jugar(View view) {

        Intent i = new Intent(this, MenuJugar.class);
        startActivity(i);
    }

    public void salir(View view){
        finish();
    }
}
