package com.example.prototipotfg;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Estadisticas extends Activity {


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
    }
}
