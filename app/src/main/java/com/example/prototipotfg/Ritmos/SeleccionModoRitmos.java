package com.example.prototipotfg.Ritmos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.prototipotfg.R;
import com.example.prototipotfg.Ritmos.Crear.NivelesCrearRitmo;
import com.example.prototipotfg.Ritmos.Hallar.NivelesHallarRitmos;

public class SeleccionModoRitmos extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccion_ritmos);
    }

    public void modo(View view){
        Intent i;
        switch (view.getId()){
            case R.id.buttonHalla:
                i = new Intent(this, NivelesHallarRitmos.class);
                i.putExtra("modoRitmo", "hallar");
                startActivity(i);
                break;

            case R.id.buttonRealizar:
                i = new Intent(this, NivelesCrearRitmo.class);
                i.putExtra("modoRitmo", "realizar");
                startActivity(i);
                break;

            default: break;
        }
    }
}
