package com.example.prototipotfg.Intervalos;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.prototipotfg.Enumerados.Intervalos;
import com.example.prototipotfg.Enumerados.Notas;
import com.example.prototipotfg.Enumerados.Octavas;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.FactoriaNotas;
import com.example.prototipotfg.Singletons.Reproductor;

import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class TutorialIntervalos extends Activity{
    ArrayList<Button> opciones = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.niveles);
        ((TextView)findViewById(R.id.tituloNiveles)).setText("Intervalos sobre LA4");
        LinearLayout llBotonera = (LinearLayout) findViewById(R.id.Botonera);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < Intervalos.values().length; i++){
            Button button = new Button(this);
            button.setLayoutParams(lp);
            button.setText(Intervalos.values()[i].getNombre() + " \n (" + Intervalos.values()[i].getDiferencia() + " semitonos)");
            button.setId(Intervalos.values()[i].getDiferencia());
            opciones.add(button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reproduceIntervalo(v);
                }
            });
            llBotonera.addView(button);
        }
    }



    public void reproduceIntervalo(final View v){

        Pair<Notas, Octavas> segundaNota = FactoriaNotas.getInstance().devuelveNotaCompletaIntervalo(Notas.LA, Octavas.Cuarta, Intervalos.getIntervaloPorDiferencia(v.getId()));
        deshabilitaBotones();
        reproduceNota(FactoriaNotas.getInstance().getInstrumento().getPath()+ Octavas.Cuarta.getPath() + Notas.LA.getPath());
        try {
            sleep(400);
            reproduceNota(FactoriaNotas.getInstance().getInstrumento().getPath() + segundaNota.second.getPath()+segundaNota.first.getPath());
            sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        habilitaBotones();
    }

    public void reproduceNota(String ruta) {
        AssetFileDescriptor afd = null;
        try {
            afd = getAssets().openFd(ruta);
            Reproductor.getInstance().reproducirNota(afd);
            afd.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deshabilitaBotones(){
        for (Button b : opciones){
            b.setEnabled(false);
        }
    }

    private void habilitaBotones(){
        for (Button b : opciones){
            b.setEnabled(true);
        }
    }
}