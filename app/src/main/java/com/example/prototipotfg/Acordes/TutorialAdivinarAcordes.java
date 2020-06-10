package com.example.prototipotfg.Acordes;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.prototipotfg.Enumerados.Acordes;
import com.example.prototipotfg.Enumerados.Notas;
import com.example.prototipotfg.Enumerados.Octavas;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.Controlador;
import com.example.prototipotfg.Singletons.Reproductor;

import java.io.IOException;
import java.util.ArrayList;

public class TutorialAdivinarAcordes extends Activity {

    private Octavas octavaAcordes;
    private Notas notaAcordes;
    private ArrayList<ArrayList<Pair<Notas, Octavas>>> acordesReproducir = new ArrayList<>();
    private ArrayList<Acordes> acordes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.niveles);
        TextView titulo = findViewById(R.id.tituloNiveles);
        octavaAcordes = Octavas.devuelveOctavaPorNombre(getIntent().getExtras().getString("octava"));
        notaAcordes = Notas.devuelveNotaPorNombre(getIntent().getExtras().getString("nota"));
        titulo.setText("Acordes sobre " + notaAcordes.getNombre());
        LinearLayout llBotonera = (LinearLayout) findViewById(R.id.Botonera);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(150, 0, 150, 0);
        acordes = Controlador.getInstance().getAcordes();

        for (Acordes a : acordes) {
            acordesReproducir.add(Acordes.devuelveNotasAcorde(a, this.octavaAcordes, notaAcordes));
        }
        for (int i = 0; i < acordes.size(); i++) {
            Button button = new Button(this);
            button.setId(i);
            //Asignamos propiedades de layout al boton
            button.setLayoutParams(lp);
            //Asignamos Texto al botón
            button.setText(acordes.get(i).getNombre());

            //Asignamose el Listener
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reproducirAcorde(v);
                }
            });
            //Añadimos el botón a la botonera
            llBotonera.addView(button);
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
                retorno.add(getAssets().openFd("piano/" + nota.second.getPath() + nota.first.getPath()));
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
