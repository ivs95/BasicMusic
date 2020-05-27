package com.example.prototipotfg.Acordes.Adivinar;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.prototipotfg.Enumerados.Acordes;
import com.example.prototipotfg.Enumerados.Instrumentos;
import com.example.prototipotfg.Enumerados.Notas;
import com.example.prototipotfg.Enumerados.Octavas;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.FactoriaNotas;
import com.example.prototipotfg.Singletons.Reproductor;

import java.io.IOException;
import java.util.ArrayList;


public class TutorialNivelAdivinarAcorde extends Activity{

    private Notas notaInicio = Notas.values()[0];
    private Octavas octavaInicio = Octavas.values()[0];
    private ArrayList<ArrayList<Pair<Notas, Octavas>>> acordesReproducir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String[] octavas = new String[Octavas.values().length];
        String[] notas = new String[Notas.values().length];

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial_adivinar_acordes);
        for (int i = 0; i < octavas.length; i++){
            octavas[i] = Octavas.values()[i].getNombre();
        }
        for (int i = 0; i < notas.length; i++){
            notas[i] = Notas.values()[i].getNombre();
        }

        Spinner s = findViewById(R.id.spinnerOctavas);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, octavas);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        s.setAdapter(adapter);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                octavaInicio = Octavas.devuelveOctavaPorNombre((String)parent.getItemAtPosition(position));
                actualizaVista();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        s = (Spinner) findViewById(R.id.spinnerNotas);
        adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, notas);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        s.setAdapter(adapter);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                notaInicio = Notas.devuelveNotaPorNombre((String)parent.getItemAtPosition(position));

                actualizaVista();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void actualizaVista() {
        LinearLayout tabla = findViewById(R.id.acordesTutorialAcordes);
        tabla.removeAllViews();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        acordesReproducir = FactoriaNotas.getInstance().devuelveAcordes(this.octavaInicio, this.notaInicio);
        for (int i = 0; i < acordesReproducir.size(); i++) {
            String texto = Acordes.values()[i].getNombre();
            Button b = new Button(this);
            b.setLayoutParams(lp);
            b.setText(texto);
            b.setId(i);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reproducirAcorde(v);
                }
            });
            tabla.addView(b);
        }
    }

    public void reproducirAcorde(View view){
        ArrayList<AssetFileDescriptor> assetFileDescriptors = preparaAssets(acordesReproducir.get(view.getId()));
        Reproductor.getInstance().reproducirAcorde(assetFileDescriptors);
        cierraAssets(assetFileDescriptors);


    }

    private ArrayList<AssetFileDescriptor> preparaAssets(ArrayList<Pair<Notas, Octavas>> acorde){
        ArrayList<AssetFileDescriptor> retorno = new ArrayList<>();
        for (Pair<Notas, Octavas> nota : acorde){
            try {
                retorno.add(getAssets().openFd(Instrumentos.Piano.getPath() + nota.second.getPath() + nota.first.getPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return retorno;
    }


    private void cierraAssets(ArrayList<AssetFileDescriptor> assetFileDescriptors) {
        for (AssetFileDescriptor afd : assetFileDescriptors) {
            try {
                afd.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
