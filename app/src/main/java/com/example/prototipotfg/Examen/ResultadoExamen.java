package com.example.prototipotfg.Examen;

import android.app.Activity;
import android.os.Bundle;

import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.Controlador;

public class ResultadoExamen extends Activity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultado_examen);
        ControladorExamen.getInstance().setResultadoExamen();


    }
}
