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
    }


    public void ejecutar_jugar(View view) {

        Intent i = new Intent(this, MenuJugar.class);
        startActivity(i);
    }

    public void ejecutar_Perfil(View view) {

        Intent i = new Intent(this, Perfil.class);
        startActivity(i);
    }

    public void mostrarEstadisticas(View view){

        Intent i = new Intent(this, Estadisticas.class);
        startActivity(i);
    }

    protected void onStop(){
        super.onStop();
    }
    public void salir(View view){
        GestorBBDD.getInstance().cerrarSesion();
        Intent i = new Intent(this, Login.class);
        startActivity(i);
    }
}
