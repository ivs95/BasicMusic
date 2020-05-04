package com.example.prototipotfg.Examen;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.R;

import java.text.DecimalFormat;
import java.util.HashMap;

public class ResultadoExamen extends Activity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultado_examen);
        HashMap<ModoJuego, Integer> resultado = ControladorExamen.getInstance().getResultadoEjercicios();
        for (ModoJuego m : resultado.keySet()){
            setTextoResultado(m, resultado.get(m));
        }
        TextView lbl = findViewById(R.id.resultadoExamen);
        int numAciertos = ControladorExamen.getInstance().getNumAciertos();
        lbl.setText(lbl.getText() + "Total de aciertos: " + numAciertos + "\n Total de fallos: " + (ControladorExamen.getInstance().getNumEjercicios()-numAciertos));
        setTextoAprobado(ControladorExamen.getInstance().isAprobado(), ControladorExamen.getInstance().getPorcentajeAcierto(), ControladorExamen.getInstance().getNivel().getPorcentajeAprobar(), (TextView) findViewById(R.id.resultadoAprobado));

    }

    private void setTextoAprobado(boolean aprobado, double porcentajeAcierto, double porcentajeAprobar, TextView lbl) {
        porcentajeAcierto = porcentajeAcierto*100;
        if (aprobado) {
            lbl.setText(lbl.getText() + "¡Felicidades, has aprobado!\nTu porcentaje de acierto: " +
                    ((int)(porcentajeAcierto*100)) + "%\nPorcentaje requerido: " + ((int)(porcentajeAprobar*100)) + "%");
            lbl.setTextColor(ContextCompat.getColor(this, R.color.md_green_500));
        }
        else {
            lbl.setText(lbl.getText() + "Inténtalo de nuevo\nTu porcentaje de acierto: " +
                    ((int)(porcentajeAcierto*100)) + "%\nPorcentaje requerido: " + ((int)(porcentajeAprobar*100)) + "%");
            lbl.setTextColor(ContextCompat.getColor(this, R.color.md_red_500));
        }
    }

    private void setTextoResultado(ModoJuego m, Integer aciertos) {
        TextView lbl;
        int fallos = 2 - aciertos;
        switch (m){
            case Adivinar_Notas:
                lbl = findViewById(R.id.resultadoAdivinaNota);
                lbl.setText(lbl.getText() + "Aciertos " +aciertos.toString() + " Fallos " + fallos);
                break;
            case Adivinar_Acordes:
                lbl = findViewById(R.id.resultadoAdivinarAcorde);
                lbl.setText(lbl.getText() + "Aciertos " +aciertos.toString() + " Fallos " + fallos);
                break;
            case Adivinar_Intervalo:
                lbl = findViewById(R.id.resultadoAdivinaIntervalo);
                lbl.setText(lbl.getText() + "Aciertos " +aciertos.toString() + " Fallos " + fallos);
                break;
            case Halla_Ritmo:
                lbl = findViewById(R.id.resultadoHallarRitmo);
                lbl.setText(lbl.getText() + "Aciertos " +aciertos.toString() + " Fallos " + fallos);
                break;
            case Realiza_Ritmo:
                lbl = findViewById(R.id.resultadoCrearRitmo);
                lbl.setText(lbl.getText() + "Aciertos " +aciertos.toString() + " Fallos " + fallos);
                break;
            case Crear_Acordes:
                lbl = findViewById(R.id.resultadoCrearAcorde);
                lbl.setText(lbl.getText() + "Aciertos " +aciertos.toString() + " Fallos " + fallos);
                break;
            case Crear_Intervalo:
                lbl = findViewById(R.id.resultadoCrearIntervalo);
                lbl.setText(lbl.getText() + "Aciertos " +aciertos.toString() + " Fallos " + fallos);
                break;
        }
    }
}
