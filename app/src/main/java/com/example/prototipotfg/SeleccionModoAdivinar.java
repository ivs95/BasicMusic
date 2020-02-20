package com.example.prototipotfg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.prototipotfg.Enumerados.TipoModo;
import com.example.prototipotfg.Singletons.Controlador;

public class SeleccionModoAdivinar extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccion_adivinar);
    }

    public void modo(View view){
        Intent i;
        switch (view.getId()){
            case R.id.buttonNotas:
                Controlador.getInstance().setTipo_modo(TipoModo.Notas);
                seleccionarDificultadNotas();
                break;

            /*case R.id.buttonAcordes:
                i.putExtra("modo", "acordes");
                startActivity(i);
                break;*/

            case R.id.buttonRealizar:
                Controlador.getInstance().setTipo_modo(TipoModo.Intervalos);
                seleccionarModoIntervalos();
                break;

            default: break;
        }
    }

    private void seleccionarModoIntervalos() {
        Intent i = new Intent(this, SeleccionarModoIntervalos.class);
        startActivity(i);
    }

    private void seleccionarDificultadNotas() {
        Intent i = new Intent(this, SeleccionarDificultadAdivinar.class);
        startActivity(i);
    }
}
