package com.example.prototipotfg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.jfugue.Player;

public class AdivinarAudio extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adivinar_audio);
    }

    public void adivinarNota(View view) {
        Intent i = new Intent(this, NivelesAdivinarNotas.class);
        startActivity(i);
    }
}
