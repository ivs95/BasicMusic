package com.example.prototipotfg;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.prototipotfg.Singletons.GestorBBDD;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

public class Estadisticas extends Activity implements AdapterView.OnItemSelectedListener{


    private final String[] MODOS_JUEGO = new String[]{"Adivinar nota", "Adivinar intervalo", "Crear intervalo", "Adivinar acorde", "Crear acorde",
            "Imitar audio - Soprano","Imitar audio - Mezzosoprano","Imitar audio - Contralto","Imitar audio - Tenor","Imitar audio - Barítono","Imitar audio - Bajo",
            "Hallar ritmo", "Realizar ritmo"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estadisticas);
        Spinner s = (Spinner) findViewById(R.id.spinnerModo);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, MODOS_JUEGO);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        s.setAdapter(adapter);
        s.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String opcion = (String)parent.getItemAtPosition(position);
        LinkedHashMap<String,String> datos = GestorBBDD.getInstance().devuelveEstadistica(opcion);
        TableLayout tabla = findViewById(R.id.tablaEstadisticas);
        tabla.removeAllViews();
        for (String nivel : datos.keySet()) {
            String texto = nivel + ": \t\t";
            TableRow row = new TableRow(this);
            row.setLayoutParams(tabla.getLayoutParams());
            TextView prueba = new TextView(this);
            prueba.setTextSize(15);
            prueba.setTextColor(Color.BLUE);
            prueba.setText(texto);
            row.addView(prueba);
            for (String s : datos.get(nivel).split(";")){
                prueba = new TextView(this);
                prueba.setTextSize(15);
                prueba.setTextColor(Color.BLUE);
                prueba.setText(s + "\t\t");
                row.addView(prueba);
            }
            row.setPadding(0,30,0,0);
            tabla.addView(row);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
