package com.example.prototipotfg.Mix;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.prototipotfg.BBDD.Entidades.NivelAdivinar;
import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Enumerados.RangosPuntuaciones;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.Controlador;
import com.example.prototipotfg.Singletons.ControladorMix;
import com.example.prototipotfg.Singletons.GestorBBDD;

import java.util.HashMap;

public class ResultadoMix extends Activity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultado_examen);

        NivelAdivinar nivel;
        int nivelActual = GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Modo_Mix.toString()).getNivel();
        int rangoActual = RangosPuntuaciones.getRangoPorNombre(GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Modo_Mix.toString()).getRango()).ordinal();
        if (ControladorMix.getInstance().isAprobado()) {
            if (ControladorMix.getInstance().getNivel().getNivel() == GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Modo_Mix.toString()).getNivel())
                GestorBBDD.getInstance().actualizarPuntuacion(ControladorMix.getInstance().getNivel().getNivel(), ModoJuego.Modo_Mix.toString(), true);
            nivel = new NivelAdivinar(ModoJuego.Modo_Mix.getNombre(), ControladorMix.getInstance().getNivel().getNivel(), true, GestorBBDD.getInstance().getUsuarioLoggeado().getCorreo(), 1, 0);

        } else {
            if (ControladorMix.getInstance().getNivel().getNivel() == GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Modo_Mix.toString()).getNivel())
                GestorBBDD.getInstance().actualizarPuntuacion(ControladorMix.getInstance().getNivel().getNivel(), ModoJuego.Modo_Mix.toString(), false);
            nivel = new NivelAdivinar(ModoJuego.Modo_Mix.getNombre(), ControladorMix.getInstance().getNivel().getNivel(), false, GestorBBDD.getInstance().getUsuarioLoggeado().getCorreo(), 0, 1);

        }
        GestorBBDD.getInstance().insertaNivelAdivinar(nivel);


        int nivelNuevo = GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Modo_Mix.toString()).getNivel();

        int rangoNuevo = RangosPuntuaciones.getRangoPorNombre(GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Modo_Mix.toString()).getRango()).ordinal();

        if (rangoActual != rangoNuevo) {
            LayoutInflater inflater = (LayoutInflater)
                    getSystemService(LAYOUT_INFLATER_SERVICE);
            RangosPuntuaciones.mostrar_popUp_rango(findViewById(android.R.id.content).getRootView(), rangoActual, rangoNuevo, inflater, ModoJuego.Modo_Mix.toString());

        }

        if (nivelActual != nivelNuevo) {
            Controlador.getInstance().setNivel(nivelNuevo);
            ControladorMix.getInstance().setNivel(nivelNuevo);
            Controlador.getInstance().estableceDificultad();
        }


        HashMap<ModoJuego, Integer> resultado = ControladorMix.getInstance().getResultadoEjercicios();
        for (ModoJuego m : resultado.keySet()) {
            setTextoResultado(m, resultado.get(m));
        }
        setTextoAprobado(ControladorMix.getInstance().isAprobado(), ControladorMix.getInstance().getNumAciertos(), ControladorMix.getInstance().getNivel().getAciertosAprobar(), (TextView) findViewById(R.id.resultadoAprobado));

    }

    private void setTextoAprobado(boolean aprobado, int numAciertos, int aciertosAprobar, TextView lbl) {
        if (aprobado) {
            lbl.setText("¡Felicidades, has aprobado!\n\nTu total de aciertos: " +
                    numAciertos + "\nAciertos requeridos: " + aciertosAprobar);
            lbl.setTextColor(ContextCompat.getColor(this, R.color.md_green_500));
        } else {
            lbl.setText("Inténtalo de nuevo\n\nTu total de aciertos: " +
                    numAciertos + "\nAciertos requeridos: " + aciertosAprobar);
            lbl.setTextColor(ContextCompat.getColor(this, R.color.md_red_500));
        }
    }

    private void setTextoResultado(ModoJuego m, Integer aciertos) {
        TextView lbl;
        int fallos = 2 - aciertos;
        switch (m) {
            case Adivinar_Notas:
                lbl = findViewById(R.id.resultadoAdivinaNota);
                lbl.setText(lbl.getText() + "Aciertos " + aciertos.toString() + " Fallos " + fallos);
                break;
            case Adivinar_Acordes:
                lbl = findViewById(R.id.resultadoAdivinarAcorde);
                lbl.setText(lbl.getText() + "Aciertos " + aciertos.toString() + " Fallos " + fallos);
                break;
            case Adivinar_Intervalo:
                lbl = findViewById(R.id.resultadoAdivinaIntervalo);
                lbl.setText(lbl.getText() + "Aciertos " + aciertos.toString() + " Fallos " + fallos);
                break;
            case Halla_Ritmo:
                lbl = findViewById(R.id.resultadoHallarRitmo);
                lbl.setText(lbl.getText() + "Aciertos " + aciertos.toString() + " Fallos " + fallos);
                break;
            case Realiza_Ritmo:
                lbl = findViewById(R.id.resultadoCrearRitmo);
                lbl.setText(lbl.getText() + "Aciertos " + aciertos.toString() + " Fallos " + fallos);
                break;
            case Crear_Acordes:
                lbl = findViewById(R.id.resultadoCrearAcorde);
                lbl.setText(lbl.getText() + "Aciertos " + aciertos.toString() + " Fallos " + fallos);
                break;
            case Crear_Intervalo:
                lbl = findViewById(R.id.resultadoCrearIntervalo);
                lbl.setText(lbl.getText() + "Aciertos " + aciertos.toString() + " Fallos " + fallos);
                break;
        }
    }

    public void continuarResultado(View view) {
        finish();
    }
}
