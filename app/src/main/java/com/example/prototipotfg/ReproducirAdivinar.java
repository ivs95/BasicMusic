package com.example.prototipotfg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ReproducirAdivinar extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nivel_reproducir_adivinar);

        int nivel = getIntent().getExtras().getInt("nivel");
        TextView titulo = (TextView)findViewById(R.id.tituloAdivinar);
        titulo.setText(titulo.getText() + String.valueOf(nivel));
    }

    public void reproducir(View view){
        Intent i = new Intent(this, SeleccionarAdivinar.class);
        startActivity(i);
    }
}
