package com.example.prototipotfg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class SeleccionOctavas extends Activity {

    String modo;
    String dificultad;
    ArrayList<String> octavas = new ArrayList<String>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccion_octavas);
        modo = getIntent().getExtras().getString("modo");
        dificultad = getIntent().getExtras().getString("dificultad");
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

        Intent i = new Intent(this, NivelesAdivinarNotas.class);
        i.putExtra("modo", modo);
        i.putExtra("dificultad", dificultad);
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
