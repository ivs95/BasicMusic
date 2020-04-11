package com.example.prototipotfg.Acordes.Crear;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.prototipotfg.Enumerados.Acordes;
import com.example.prototipotfg.Enumerados.Notas;
import com.example.prototipotfg.Enumerados.Octavas;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.FactoriaNotas;
import com.example.prototipotfg.Singletons.Reproductor;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import static com.example.prototipotfg.R.*;

public class TutorialNivelCrearAcordes extends Activity implements AdapterView.OnItemSelectedListener{

    private ArrayList<ArrayList<Pair<Notas, Octavas>>> acordesReproducir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String[] notas = new String[Notas.values().length];
        for (int i = 0; i < notas.length; i++){
            notas[i] = Notas.values()[i].getNombre();
        }
        super.onCreate(savedInstanceState);
        setContentView(layout.tutorial_crear_acordes);
        Spinner s = (Spinner) findViewById(id.spinnerTutorialCrearAcordes);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, layout.spinner_item, notas);
        adapter.setDropDownViewResource(layout.spinner_dropdown_item);
        s.setAdapter(adapter);
        s.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Notas nota = Notas.devuelveNotaPorNombre((String)parent.getItemAtPosition(position));
        LinearLayout tabla = findViewById(R.id.notasTutorialCrearAcordes);
        tabla.removeAllViews();
        tabla.removeAllViews();
        LinearLayout.LayoutParams lpl = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT );
        LinearLayout.LayoutParams lpb = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1);
        acordesReproducir = FactoriaNotas.getInstance().devuelveAcordes(Octavas.Cuarta, nota);
        for (int i = 0; i < acordesReproducir.size(); i++) {
            LinearLayout fila = new LinearLayout(this);
            fila.setOrientation(LinearLayout.HORIZONTAL);
            fila.setLayoutParams(lpl);
            TextView txtAcorde = new TextView(this);
            txtAcorde.setText(Acordes.values()[i].getNombre());
            txtAcorde.setLayoutParams(lpb);
            txtAcorde.setTextColor(getResources().getColor(color.md_blue_900));
            txtAcorde.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tabla.addView(txtAcorde);
            TextView notas = new TextView(this);
            notas.setTextColor(getResources().getColor(color.md_blue_800));
            notas.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            notas.setText("Notas: ");
            notas.setLayoutParams(lpb);
            fila.addView(notas);
            for (int j = 1; j < acordesReproducir.get(i).size(); j++){
                Notas nBoton = acordesReproducir.get(i).get(j).first;
                TextView txtNota = new TextView(this);
                txtNota.setTextColor(getResources().getColor(color.md_blue_500));
                txtNota.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                txtNota.setText(nBoton.getNombre());
                txtNota.setLayoutParams(lpb);
                fila.addView(txtNota);
            }
            fila.setPadding(0,0,0,10);
            tabla.addView(fila);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
