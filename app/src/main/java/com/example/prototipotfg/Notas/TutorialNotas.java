package com.example.prototipotfg.Notas;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.prototipotfg.Enumerados.Notas;
import com.example.prototipotfg.Enumerados.Octavas;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.FactoriaNotas;
import com.example.prototipotfg.Singletons.Reproductor;

import java.io.IOException;

public class TutorialNotas extends Activity implements AdapterView.OnItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String[] octavas = new String[Octavas.values().length];
        for (int i = 0; i < octavas.length; i++){
            octavas[i] = Octavas.values()[i].getNombre();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial_notas);
        Spinner s = (Spinner) findViewById(R.id.spinnerTutorialNotas);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, octavas);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        s.setAdapter(adapter);
        s.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String octava = (String)parent.getItemAtPosition(position);
        LinearLayout tabla = findViewById(R.id.notasTutorialNotas);
        tabla.removeAllViews();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT );
        for (Notas n : Notas.values()) {
            final String ruta = FactoriaNotas.getInstance().getInstrumento().getPath() + Octavas.devuelveOctavaPorNombre(octava).getPath() + n.getPath();
            Button b = new Button(this);
            b.setLayoutParams(lp);
            b.setText(n.getNombre());
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reproduceNota(ruta);
                }
            });
            tabla.addView(b);
        }
    }


    public void reproduceNota(String ruta){
        AssetFileDescriptor afd = null;
        try {
            afd = getAssets().openFd(ruta);
            Reproductor.getInstance().reproducirNota(afd);
            afd.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
