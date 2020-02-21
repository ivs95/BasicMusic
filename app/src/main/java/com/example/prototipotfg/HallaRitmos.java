package com.example.prototipotfg;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

public class HallaRitmos extends Activity {

    private Metronomo m = new Metronomo(60, 4);

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hallaritmos);

        int nivel = getIntent().getExtras().getInt("nivel");
        TextView titulo = (TextView)findViewById(R.id.tituloHallaRitmo);
        titulo.setText(titulo.getText() + String.valueOf(nivel));

    }


    public void play(View view) throws IOException {
        m.start(this);

    }

    public void stop(View view){
        m.stop();
    }


}
