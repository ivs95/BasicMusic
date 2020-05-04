package com.example.prototipotfg.Examen;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.prototipotfg.BBDD.NivelAdivinar;
import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Enumerados.RangosPuntuaciones;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.Controlador;
import com.example.prototipotfg.Singletons.GestorBBDD;

import java.text.DecimalFormat;
import java.util.HashMap;

public class ResultadoExamen extends Activity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultado_examen);

        NivelAdivinar nivel = null;
        int nivelActual = GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Modo_Mix.toString()).getNivel();
        int rangoActual = RangosPuntuaciones.getRangoPorNombre(GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Modo_Mix.toString()).getRango()).ordinal();
        if(ControladorExamen.getInstance().isAprobado()) {
            if (ControladorExamen.getInstance().getNivel().getNivel() == GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Modo_Mix.toString()).getNivel())
                GestorBBDD.getInstance().actualizarPuntuacion(ControladorExamen.getInstance().getNivel().getNivel(), ModoJuego.Modo_Mix.toString(), true);
            nivel = new NivelAdivinar(ModoJuego.Modo_Mix.getNombre(), ControladorExamen.getInstance().getNivel().getNivel(), true, GestorBBDD.getInstance().getUsuarioLoggeado().getCorreo(), 1 , 0);

        }

        else{
            if (ControladorExamen.getInstance().getNivel().getNivel() == GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Modo_Mix.toString()).getNivel())
                GestorBBDD.getInstance().actualizarPuntuacion(ControladorExamen.getInstance().getNivel().getNivel(), ModoJuego.Modo_Mix.toString(), false);
            nivel = new NivelAdivinar(ModoJuego.Modo_Mix.getNombre(), ControladorExamen.getInstance().getNivel().getNivel(), false, GestorBBDD.getInstance().getUsuarioLoggeado().getCorreo(), 0 , 1);

        }
        GestorBBDD.getInstance().insertaNivelAdivinar(nivel);


        int nivelNuevo = GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Modo_Mix.toString()).getNivel();

        int rangoNuevo = RangosPuntuaciones.getRangoPorNombre(GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Modo_Mix.toString()).getRango()).ordinal();
        if(rangoActual != rangoNuevo) {
            LayoutInflater inflater = (LayoutInflater)
                    getSystemService(LAYOUT_INFLATER_SERVICE);
            RangosPuntuaciones.mostrar_popUp_rango(findViewById(android.R.id.content).getRootView(), rangoActual, rangoNuevo, inflater, ModoJuego.Modo_Mix.toString());

        }

        if(nivelActual != nivelNuevo){
            Controlador.getInstance().setNivel(nivelNuevo);
            ControladorExamen.getInstance().setNivel(nivelNuevo);
            Controlador.getInstance().estableceDificultad();
        }


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
