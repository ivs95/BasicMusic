package com.example.prototipotfg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.prototipotfg.Enumerados.Dificultad;
import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Enumerados.TipoModo;
import com.example.prototipotfg.Intervalos.SeleccionOctavasIntervalos;
import com.example.prototipotfg.Notas.SeleccionOctavasNotas;
import com.example.prototipotfg.Singletons.Controlador;

public class SeleccionarDificultadAdivinar extends Activity {

    private String modo_intervalo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccion_dificultad);

        if(Controlador.getInstance().getTipo_modo().equals(TipoModo.Intervalos)){
            Button boton = findViewById(R.id.buttonMedio);
            boton.setVisibility(View.GONE);

            modo_intervalo = getIntent().getExtras().getString("modo_intervalo");
        }

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
        Intent i = null;
        if(Controlador.getInstance().getTipo_modo().equals(TipoModo.Intervalos)){
            i = new Intent(this, SeleccionOctavasIntervalos.class);
            i.putExtra("modo_intervalo", modo_intervalo);
        }
        else{
            i = new Intent(this, SeleccionOctavasNotas.class);
        }

        startActivity(i);
    }

}
