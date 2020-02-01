package com.example.prototipotfg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ReproducirImitar extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nivel_reproducir_imitar);

        int nivel = getIntent().getExtras().getInt("nivel");
        TextView titulo = (TextView)findViewById(R.id.tituloImitar);
        titulo.setText(titulo.getText() + String.valueOf(nivel));
    }

    public void reproducir(View view){
        ProgressBar progresoReproducir = (ProgressBar)findViewById(R.id.progressReproducir);
        ProgressBar progresoGrabar = (ProgressBar)findViewById(R.id.progressGrabar);
        progresoReproducir.setVisibility(View.VISIBLE);
        progresoGrabar.setVisibility(View.INVISIBLE);


    }

    public void grabar(View view){
        ProgressBar progresoReproducir = (ProgressBar)findViewById(R.id.progressReproducir);
        ProgressBar progresoGrabar = (ProgressBar)findViewById(R.id.progressGrabar);
        progresoReproducir.setVisibility(View.INVISIBLE);
        progresoGrabar.setVisibility(View.VISIBLE);

    }

    public void comparar(View view){
        ProgressBar progresoReproducir = (ProgressBar)findViewById(R.id.progressReproducir);
        ProgressBar progresoGrabar = (ProgressBar)findViewById(R.id.progressGrabar);
        progresoReproducir.setVisibility(View.INVISIBLE);
        progresoGrabar.setVisibility(View.INVISIBLE);
    }
}