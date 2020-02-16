package com.example.prototipotfg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SeleccionModoRitmos extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccion_ritmos);
    }

    public void modo(View view){
        Intent i = new Intent(this, NivelesRitmos.class);
        switch (view.getId()){
            case R.id.buttonHalla:
                i.putExtra("modoRitmo", "hallar");
                startActivity(i);
                break;

            case R.id.buttonRealizar:
                i.putExtra("modoRitmo", "realizar");
                startActivity(i);
                break;

            default: break;
        }
    }
}
