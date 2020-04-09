package com.example.prototipotfg.Intervalos;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.prototipotfg.Enumerados.Intervalos;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.GestorBBDD;

import java.util.LinkedHashMap;

public class TutorialIntervalos extends Activity{


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
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intervalos intervalo = Intervalos.getIntervaloPorDiferencia(v.getId());

                }
            });
            llBotonera.addView(button);
        }
    }

}
