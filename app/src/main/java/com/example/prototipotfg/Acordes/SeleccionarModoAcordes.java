package com.example.prototipotfg.Acordes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.prototipotfg.Acordes.Adivinar.SeleccionarNivelAdivinarAcordes;
import com.example.prototipotfg.Acordes.Crear.SeleccionarNivelCrearAcordes;
import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Enumerados.RangosPuntuaciones;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.Controlador;
import com.example.prototipotfg.Singletons.GestorBBDD;

public class SeleccionarModoAcordes extends Activity {

    private Bundle savedInstanceState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccion_modo_acordes);
        this.savedInstanceState = savedInstanceState;

        ImageView viewRangoAdivinar = findViewById(R.id.imageViewAcordes1);
        viewRangoAdivinar.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        viewRangoAdivinar.setImageResource(RangosPuntuaciones.getRangoPorNombre(GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Adivinar_Acordes.toString()).getRango()).getImage());

        ImageView viewRangoCrear = findViewById(R.id.imageViewAcordes2);
        viewRangoCrear.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        viewRangoCrear.setImageResource(RangosPuntuaciones.getRangoPorNombre(GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Crear_Acordes.toString()).getRango()).getImage());
    }

    public void modo_acordes(View view){
        switch (view.getId()){
            case R.id.buttonAcordesModo1:
                Intent i = new Intent(this, SeleccionarNivelAdivinarAcordes.class);
                Controlador.getInstance().setModo_juego(ModoJuego.Adivinar_Acordes);
                i.putExtra("visitado", GestorBBDD.getInstance().esPrimeraVezModo(ModoJuego.Adivinar_Acordes));

                startActivity(i);
                break;

            case R.id.buttonAcordesModo2:
                i = new Intent(this, SeleccionarNivelCrearAcordes.class);
                Controlador.getInstance().setModo_juego(ModoJuego.Crear_Acordes);
                i.putExtra("visitado", GestorBBDD.getInstance().esPrimeraVezModo(ModoJuego.Crear_Acordes));
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
