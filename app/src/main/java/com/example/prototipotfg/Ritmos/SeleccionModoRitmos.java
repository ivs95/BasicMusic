package com.example.prototipotfg.Ritmos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Enumerados.RangosPuntuaciones;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Ritmos.Crear.NivelesCrearRitmo;
import com.example.prototipotfg.Ritmos.Hallar.NivelesHallarRitmos;
import com.example.prototipotfg.Singletons.GestorBBDD;

public class SeleccionModoRitmos extends Activity {

    private Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccion_ritmos);
        this.savedInstanceState = savedInstanceState;


        ImageView viewRangoHalla = findViewById(R.id.imageViewRitmos1);
        viewRangoHalla.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        viewRangoHalla.setImageResource(RangosPuntuaciones.getRangoPorNombre(GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Halla_Ritmo.toString()).getRango()).getImage());

        ImageView viewRangoRealiza = findViewById(R.id.imageViewRitmos2);
        viewRangoRealiza.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        viewRangoRealiza.setImageResource(RangosPuntuaciones.getRangoPorNombre(GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Realiza_Ritmo.toString()).getRango()).getImage());
    }

    public void modo(View view){
        Intent i;
        switch (view.getId()){
            case R.id.buttonHalla:
                i = new Intent(this, NivelesHallarRitmos.class);
                i.putExtra("modoRitmo", "hallar");
                i.putExtra("visitado", GestorBBDD.getInstance().esPrimeraVezModo(ModoJuego.Halla_Ritmo));
                startActivity(i);
                break;

            case R.id.buttonRealizar:
                i = new Intent(this, NivelesCrearRitmo.class);
                i.putExtra("modoRitmo", "realizar");
                i.putExtra("visitado", GestorBBDD.getInstance().esPrimeraVezModo(ModoJuego.Realiza_Ritmo));
                startActivity(i);
                break;

            default: break;
        }
    }
    public void onResume(){
        super.onResume();
        this.onCreate(this.savedInstanceState);
    }
}
