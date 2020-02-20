package com.example.prototipotfg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.prototipotfg.Enumerados.Dificultad;
import com.example.prototipotfg.Notas.SeleccionOctavasNotas;
import com.example.prototipotfg.Singletons.Controlador;

public class SeleccionarDificultadAdivinar extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccion_dificultad);
    }

    public void setDificultadFacil(View view){
        Controlador.getInstance().setDificultad(Dificultad.Facil);
        mostrarNiveles();
    }

    public void setDificultadMedio(View view){
        Controlador.getInstance().setDificultad(Dificultad.Medio);
        mostrarNiveles();
    }

    public void setDificultadDificil(View view){
        Controlador.getInstance().setDificultad(Dificultad.Dificil);
        mostrarNiveles();
    }

    public void mostrarNiveles(){
        Intent i = new Intent(this, SeleccionOctavasNotas.class);
        startActivity(i);
    }

}
