package com.example.prototipotfg.Enumerados;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.GestorBBDD;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public enum RangosPuntuaciones {
    Principiante("Principiante", R.drawable.semifusa_verdu),
    Aprendiz("Aprendiz", R.drawable.fusa),
    Veterano("Veterano", R.drawable.semicorchea),
    Experto("Experto", R.drawable.corchea),
    Maestro("Maestro", R.drawable.negra),
    GranMaestro("Gran Maestro", R.drawable.blanca),
    Leyenda("Leyenda", R.drawable.redonda);

    private String nombre;
    private int image;

    RangosPuntuaciones(String nombre, int image){
        this.nombre=nombre;
        this.image = image;
    }

    public static RangosPuntuaciones actualizaRango(String modoJuego, int puntuacion){
        RangosPuntuaciones nuevo_rango = null;
        switch (modoJuego){
            case "Adivinar_Notas":
                if(puntuacion >= 0 && puntuacion < 30) nuevo_rango = RangosPuntuaciones.Principiante;
                else if(puntuacion >= 30 && puntuacion < 138) nuevo_rango = RangosPuntuaciones.Aprendiz;
                else if(puntuacion >= 138 && puntuacion < 225) nuevo_rango = RangosPuntuaciones.Veterano;
                else if(puntuacion >= 225 && puntuacion < 273) nuevo_rango = RangosPuntuaciones.Experto;
                else if(puntuacion >= 273 && puntuacion < 324) nuevo_rango = RangosPuntuaciones.Maestro;
                else if(puntuacion >= 324 && puntuacion < 398) nuevo_rango = RangosPuntuaciones.GranMaestro;
                else return RangosPuntuaciones.Leyenda;
                break;
            case "Adivinar_Intervalo":

            case "Adivinar_Acordes":

            case "Crear_Acordes":
                if(puntuacion >= 0 && puntuacion < 15) nuevo_rango = RangosPuntuaciones.Principiante;
                else if(puntuacion >= 15 && puntuacion < 30) nuevo_rango = RangosPuntuaciones.Aprendiz;
                else if(puntuacion >= 30 && puntuacion < 63) nuevo_rango = RangosPuntuaciones.Veterano;
                else if(puntuacion >= 63 && puntuacion < 99) nuevo_rango = RangosPuntuaciones.Experto;
                else if(puntuacion >= 99 && puntuacion < 138) nuevo_rango = RangosPuntuaciones.Maestro;
                else if(puntuacion >= 138 && puntuacion < 200) nuevo_rango = RangosPuntuaciones.GranMaestro;
                else nuevo_rango = RangosPuntuaciones.Leyenda;
                break;
            case "Crear_Intervalo":
            case "Halla_Ritmo":

            case "Realiza_Ritmo":
                if(puntuacion >= 0 && puntuacion < 30) nuevo_rango = RangosPuntuaciones.Principiante;
                else if(puntuacion >= 30 && puntuacion < 99) nuevo_rango = RangosPuntuaciones.Aprendiz;
                else if(puntuacion >= 99 && puntuacion < 138) nuevo_rango = RangosPuntuaciones.Veterano;
                else if(puntuacion >= 138 && puntuacion < 180) nuevo_rango = RangosPuntuaciones.Experto;
                else if(puntuacion >= 180 && puntuacion < 225) nuevo_rango = RangosPuntuaciones.Maestro;
                else if(puntuacion >= 225 && puntuacion < 293) nuevo_rango = RangosPuntuaciones.GranMaestro;
                else nuevo_rango = RangosPuntuaciones.Leyenda;
                break;
            default:break;
        }

        return nuevo_rango;
    }

    public int getImage(){return this.image;}

    public static RangosPuntuaciones getRangoPorNombre(String name){
        RangosPuntuaciones rango = null;
        for(int i = 0; i < RangosPuntuaciones.values().length; i++){
            if(RangosPuntuaciones.values()[i].toString().equals(name)) rango = RangosPuntuaciones.values()[i];

        }
        return rango;
    }

    public static void mostrar_popUp_rango(View view, int rangoActual, int rangoNuevo, LayoutInflater inflater, String modoJuego){


        View popupView = null;
        ImageView viewRangoPopup = null;
        TextView rango = null;

        if(rangoActual < rangoNuevo) {
            popupView = inflater.inflate(R.layout.popup_nuevo_rango, null);
            rango = (TextView) popupView.findViewById(R.id.text_rango_popup);
            rango.setText(rango.getText() + "    " + GestorBBDD.getInstance().devuelvePuntuacion(modoJuego).getRango() + "!");

            viewRangoPopup = popupView.findViewById(R.id.imagen_rango_popup);   viewRangoPopup.setImageResource(RangosPuntuaciones.getRangoPorNombre(GestorBBDD.getInstance().devuelvePuntuacion(modoJuego).getRango()).getImage());

        }
        else {
            popupView = inflater.inflate(R.layout.popup_nuevo_rango_inf, null);
            rango = (TextView) popupView.findViewById(R.id.text_rango_popup_inf);
            rango.setText(rango.getText() + "    " + GestorBBDD.getInstance().devuelvePuntuacion(modoJuego).getRango() + "!");

            viewRangoPopup = popupView.findViewById(R.id.imagen_rango_popup_inf);   viewRangoPopup.setImageResource(RangosPuntuaciones.getRangoPorNombre(GestorBBDD.getInstance().devuelvePuntuacion(modoJuego).getRango()).getImage());

        }

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        //final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
        final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,true);


        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });


    }

    }
