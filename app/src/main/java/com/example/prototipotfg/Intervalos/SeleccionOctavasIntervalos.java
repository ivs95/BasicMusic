package com.example.prototipotfg.Intervalos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.core.content.ContextCompat;

import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Enumerados.Octavas;
import com.example.prototipotfg.Notas.NivelesAdivinarNotas;
import com.example.prototipotfg.ImitarAudio.NivelesImitar;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.Controlador;

import java.util.ArrayList;

public class SeleccionOctavasIntervalos extends Activity {

    private ArrayList<String> octavas = new ArrayList<String>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccion_octavas);
        octavaPulsada(Octavas.Segunda, (Button)findViewById(R.id.buttonSegundaOctava));
    }

    public void seleccionaOctava(View view){
        Button boton = (Button) view;
        switch (view.getId()){
            case R.id.buttonPrimeraOctava: octavaPulsada(Octavas.Primera, boton);break;
            case R.id.buttonSegundaOctava: octavaPulsada(Octavas.Segunda, boton);break;
            case R.id.buttonTerceraOctava: octavaPulsada(Octavas.Tercera, boton);break;
            case R.id.buttonCuartaOctava: octavaPulsada(Octavas.Cuarta, boton);break;
            case R.id.buttonQuintaOctava: octavaPulsada(Octavas.Quinta, boton);break;
            case R.id.buttonSextaOctava: octavaPulsada(Octavas.Sexta, boton);break;
            case R.id.buttonSeptimaOctava: octavaPulsada(Octavas.Septima, boton);break;

            default:break;
        }
    }

    public void confirmarOctavas(View view){
        Intent i = new Intent(this, NivelesAdivinarIntervalos.class);
        i.putExtra("modo_intervalo", getIntent().getExtras().getString("modo_intervalo"));
        i.putStringArrayListExtra("octavas", octavas);
        startActivity(i);
    }

    private void octavaPulsada(Octavas octava, Button boton) {
        if (octavas.contains(octava.getNombre())){
            boton.setBackgroundColor(ContextCompat.getColor(this, R.color.md_orange_400));
            octavas.remove(octava.getNombre());
        }
        else {
            octavas.add(octava.getNombre());
            boton.setBackgroundColor(ContextCompat.getColor(this, R.color.md_deep_orange_900));
        }
    }
}
