package com.example.prototipotfg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SeleccionarDificultadAdivinar extends Activity {

    String modo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccion_dificultad);
        modo = getIntent().getExtras().getString("modo");

    }


    public void mostrarNiveles(View view){
        Intent i = null;
        switch(view.getId()){
            case R.id.buttonFacil: i = new Intent(this, SeleccionOctavas.class); i.putExtra("dificultad", "facil"); break;
            case R.id.buttonMedio: i = new Intent(this, SeleccionOctavas.class); i.putExtra("dificultad", "normal"); break;
            case R.id.buttonDificil: i = new Intent(this, SeleccionOctavas.class); i.putExtra("dificultad", "dificil");break;
            default: break;
        }
        i.putExtra("modo",modo);
        startActivity(i);
    }
}
