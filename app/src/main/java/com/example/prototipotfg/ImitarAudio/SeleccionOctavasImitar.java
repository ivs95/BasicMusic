package com.example.prototipotfg.ImitarAudio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.core.content.ContextCompat;

import com.example.prototipotfg.Enumerados.Octavas;
import com.example.prototipotfg.Enumerados.RangosVocales;
import com.example.prototipotfg.R;

import java.util.ArrayList;

public class SeleccionOctavasImitar extends Activity {

    private ArrayList<String> octavas = new ArrayList<String>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccion_octavas);
    }

    public void seleccionaOctava(View view){
        Button boton = (Button) view;
        switch (view.getId()){
            case R.id.buttonPrimeraOctava: octavaPulsada(RangosVocales.Soprano);break;
            case R.id.buttonSegundaOctava: octavaPulsada(RangosVocales.Mezzo);break;
            case R.id.buttonTerceraOctava: octavaPulsada(RangosVocales.Contralto);break;
            case R.id.buttonCuartaOctava: octavaPulsada(RangosVocales.Tenor);break;
            case R.id.buttonQuintaOctava: octavaPulsada(RangosVocales.Baritono);break;
            case R.id.buttonSextaOctava: octavaPulsada(RangosVocales.Bajo);break;

            default:break;
        }
    }

    private void octavaPulsada(RangosVocales rv) {
        Intent i = new Intent(this, NivelesImitar.class);
        for(int e = rv.getOctavaIni(); e<=rv.getOctavaFin(); e++)
            octavas.add(Octavas.devuelveOctavaPorNumero(e).getNombre());
        i.putStringArrayListExtra("octavas", octavas);
        i.putExtra("rangoVocal",rv.getNombre());
        startActivity(i);
    }
}
