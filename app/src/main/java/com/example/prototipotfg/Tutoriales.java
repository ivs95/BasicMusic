package com.example.prototipotfg;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;


public class Tutoriales extends Activity {
    private int tutorial=1;
    private PopupWindow popupWindow;
    private View popupView;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutoriales);
    }

    public void modo_adivinar_notas(View view){
        tutorial=1;
        mostrarPopupTutorial(findViewById(android.R.id.content).getRootView(), R.layout.popup_tutorial_adivinarnotas);
    }

    public void modo_adivinar_intervalos(View view){
        tutorial=1;
        mostrarPopupTutorial(findViewById(android.R.id.content).getRootView(), R.layout.popup_tutorial_adivinaintervalo);
    }
    public void modo_crear_intervalos(View view){
        tutorial=1;
        mostrarPopupTutorial(findViewById(android.R.id.content).getRootView(), R.layout.popup_tutorial_creaintervalo);
    }
    public void modo_adivinar_acordes(View view){
        tutorial=1;
        mostrarPopupTutorial(findViewById(android.R.id.content).getRootView(), R.layout.popup_tutorial_adivinaracordes);
    }
    public void modo_crear_acordes(View view){
        tutorial=1;
        mostrarPopupTutorial(findViewById(android.R.id.content).getRootView(), R.layout.popup_tutorial_crearacordes);
    }

    public void modo_imitar(View view){
        tutorial=1;
        mostrarPopupTutorial(findViewById(android.R.id.content).getRootView(), R.layout.popup_tutorial_imitaraudio);
    }

    public void modo_dibuja_ritmos(View view) {
        tutorial=1;
        mostrarPopupTutorial(findViewById(android.R.id.content).getRootView(), R.layout.popup_tutorial_hallaritmos);
    }
    public void modo_imita_ritmos(View view) {
        tutorial=1;
        mostrarPopupTutorial(findViewById(android.R.id.content).getRootView(), R.layout.popup_tutorial_crearitmos);
    }

    public void mostrarPopupTutorial(View view,int tutorial){
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);

        popupView = inflater.inflate(tutorial, null);

        // create the popup window
        //final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
        popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken


        findViewById(R.id.id_tutoriales).post(new Runnable() {
            public void run() {
                popupWindow.showAtLocation(findViewById(R.id.id_tutoriales), Gravity.CENTER, 0, 0);
            }
        });

        // popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched

    }

    public void next(View view){
        tutorial++;
        if(popupView.getId() == R.id.popup_imitaraudio)
            actualizaPopUpImitarAudio(popupView);
        else if(popupView.getId() == R.id.popup_imitaritmos)
            actualizaPopUpCreaRitmos(popupView);
        else if(popupView.getId() == R.id.popup_hallaritmos)
            actualizaPopUpDinbujaRitmos(popupView);
        else if(popupView.getId() == R.id.popup_adivinaIntervalo)
            actualizaPopUpAdivinaIntervalo(popupView);
        else if(popupView.getId() == R.id.popup_creaIntervalo)
            actualizaPopUpCreaIntervalo(popupView);
        else if(popupView.getId() == R.id.popup_adivinaAcordes)
            actualizaPopUpAdivinarAcordes(popupView);
        else if(popupView.getId() == R.id.popup_crearacorde)
            actualizaPopUpCrearAcordes(popupView);
        else
            actualizaPopUp(popupView);
    }

    public void prev(View view){
        tutorial--;
        if(popupView.getId() == R.id.popup_imitaraudio)
            actualizaPopUpImitarAudio(popupView);
        else if(popupView.getId() == R.id.popup_imitaritmos)
            actualizaPopUpCreaRitmos(popupView);
        else if(popupView.getId() == R.id.popup_hallaritmos)
            actualizaPopUpDinbujaRitmos(popupView);
        else if(popupView.getId() == R.id.popup_adivinaIntervalo)
            actualizaPopUpAdivinaIntervalo(popupView);
        else if(popupView.getId() == R.id.popup_creaIntervalo)
            actualizaPopUpCreaIntervalo(popupView);
        else if(popupView.getId() == R.id.popup_adivinaAcordes)
            actualizaPopUpAdivinarAcordes(popupView);
        else if(popupView.getId() == R.id.popup_crearacorde)
            actualizaPopUpCrearAcordes(popupView);
        else
            actualizaPopUp(popupView);
    }



    public void actualizaPopUp(View view) {
        Button button = view.findViewById(R.id.popup_adivinarnotas_next);
        if (tutorial == 1) {
            view.findViewById(R.id.popup_adivinarnotas_mensaje2).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_adivinarnotas_scrollView2).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_adivinarnotas_prev).setVisibility(View.GONE);

            view.findViewById(R.id.popup_adivinarnotas_mensaje1).setVisibility(View.VISIBLE);
            button.setText("Siguiente");
        } else if (tutorial == 2) {

            view.findViewById(R.id.popup_adivinarnotas_mensaje1).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_adivinarnotas_mensaje3).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_adivinarnotas_linearLayout8).setVisibility(View.INVISIBLE);

            view.findViewById(R.id.popup_adivinarnotas_mensaje2).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_adivinarnotas_scrollView2).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_adivinarnotas_prev).setVisibility(View.VISIBLE);

            button.setText("Siguiente");
        } else if (tutorial == 3) {
            view.findViewById(R.id.popup_adivinarnotas_mensaje2).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_adivinarnotas_scrollView2).setVisibility(View.INVISIBLE);

            view.findViewById(R.id.popup_adivinarnotas_mensaje3).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_adivinarnotas_linearLayout8).setVisibility(View.VISIBLE);

            button.setText("Cerrar");
        } else if (tutorial == 4) {
            popupWindow.dismiss();
        }
    }

    public void actualizaPopUpAdivinaIntervalo(View view){
        Button button = view.findViewById(R.id.popup_adivinaintervalo_next);
        if(tutorial == 1){
            view.findViewById(R.id.popup_adivinaintervalo_mensaje2).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_adivinaintervalo_scrollView2).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_adivinaintervalo_prev).setVisibility(View.GONE);

            view.findViewById(R.id.popup_adivinaintervalo_mensaje1).setVisibility(View.VISIBLE);
        }
        else if(tutorial == 2){

            view.findViewById(R.id.popup_adivinaintervalo_mensaje1).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_adivinaintervalo_mensaje3).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_adivinaintervalo_linearLayout8).setVisibility(View.INVISIBLE);

            view.findViewById(R.id.popup_adivinaintervalo_mensaje2).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_adivinaintervalo_scrollView2).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_adivinaintervalo_prev).setVisibility(View.VISIBLE);

            button.setText("Siguiente");
        }
        else if(tutorial == 3){
            view.findViewById(R.id.popup_adivinaintervalo_mensaje2).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_adivinaintervalo_scrollView2).setVisibility(View.INVISIBLE);

            view.findViewById(R.id.popup_adivinaintervalo_mensaje3).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_adivinaintervalo_linearLayout8).setVisibility(View.VISIBLE);

            button.setText("Cerrar");
        }
        else if(tutorial == 4){
            popupWindow.dismiss();
        }
    }

    public void actualizaPopUpCreaIntervalo(View view){
        Button button = view.findViewById(R.id.popup_creaintervalo_next);
        if(tutorial == 1){
            view.findViewById(R.id.popup_creaintervalo_mensaje2).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_creaintervalo_scrollView2).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_creaintervalo_prev).setVisibility(View.GONE);

            view.findViewById(R.id.popup_creaintervalo_mensaje1).setVisibility(View.VISIBLE);
        }
        else if(tutorial == 2){

            view.findViewById(R.id.popup_creaintervalo_mensaje1).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_creaintervalo_mensaje3).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_creaintervalo_linearLayout8).setVisibility(View.INVISIBLE);

            view.findViewById(R.id.popup_creaintervalo_mensaje2).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_creaintervalo_scrollView2).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_creaintervalo_prev).setVisibility(View.VISIBLE);

            button.setText("Siguiente");
        }
        else if(tutorial == 3){
            view.findViewById(R.id.popup_creaintervalo_mensaje2).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_creaintervalo_scrollView2).setVisibility(View.INVISIBLE);

            view.findViewById(R.id.popup_creaintervalo_mensaje3).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_creaintervalo_linearLayout8).setVisibility(View.VISIBLE);

            button.setText("Cerrar");
        }
        else if(tutorial == 4){
            popupWindow.dismiss();
        }
    }

    public void actualizaPopUpAdivinarAcordes(View view){
        Button button = view.findViewById(R.id.popup_adivinaracorde_next);
        if(tutorial == 1){
            view.findViewById(R.id.popup_adivinaracorde_mensaje2).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_adivinaracorde_scrollView2).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_adivinaracorde_prev).setVisibility(View.GONE);

            view.findViewById(R.id.popup_adivinaracorde_mensaje1).setVisibility(View.VISIBLE);
        }
        else if(tutorial == 2){

            view.findViewById(R.id.popup_adivinaracorde_mensaje1).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_adivinaracorde_mensaje3).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_adivinaracorde_linearLayout8).setVisibility(View.INVISIBLE);

            view.findViewById(R.id.popup_adivinaracorde_mensaje2).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_adivinaracorde_scrollView2).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_adivinaracorde_prev).setVisibility(View.VISIBLE);

            button.setText("Siguiente");
        }
        else if(tutorial == 3){
            view.findViewById(R.id.popup_adivinaracorde_mensaje2).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_adivinaracorde_scrollView2).setVisibility(View.INVISIBLE);

            view.findViewById(R.id.popup_adivinaracorde_mensaje3).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_adivinaracorde_linearLayout8).setVisibility(View.VISIBLE);

            button.setText("Cerrar");
        }
        else if(tutorial == 4){
            popupWindow.dismiss();
        }
    }

    public void actualizaPopUpCrearAcordes(View view){
        Button button = view.findViewById(R.id.popup_crearacorde_next);
        if(tutorial == 1){
            view.findViewById(R.id.popup_crearacorde_mensaje2).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_crearacorde_scrollView2).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_crearacorde_prev).setVisibility(View.GONE);

            view.findViewById(R.id.popup_crearacorde_mensaje1).setVisibility(View.VISIBLE);
        }
        else if(tutorial == 2){

            view.findViewById(R.id.popup_crearacorde_mensaje1).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_crearacorde_mensaje3).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_crearacorde_linearLayout8).setVisibility(View.INVISIBLE);

            view.findViewById(R.id.popup_crearacorde_mensaje2).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_crearacorde_scrollView2).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_crearacorde_prev).setVisibility(View.VISIBLE);

            button.setText("Siguiente");
        }
        else if(tutorial == 3){
            view.findViewById(R.id.popup_crearacorde_mensaje2).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_crearacorde_scrollView2).setVisibility(View.INVISIBLE);

            view.findViewById(R.id.popup_crearacorde_mensaje3).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_crearacorde_linearLayout8).setVisibility(View.VISIBLE);

            button.setText("Cerrar");
        }
        else if(tutorial == 4){
            popupWindow.dismiss();
        }
    }

    public void actualizaPopUpImitarAudio(View view){
        Button button = view.findViewById(R.id.popup_imitaraudio_next);
        if(tutorial == 1){
            view.findViewById(R.id.popup_imitaraudio_mensaje2).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_imitaraudio_textoFrecuencia).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_imitaraudio_prev).setVisibility(View.GONE);

            view.findViewById(R.id.popup_imitaraudio_mensaje1).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_imitaraudio_mensaje12).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_imitaraudio_mensaje13).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_imitaraudio_botonGrabar).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_imitaraudio_button2).setVisibility(View.VISIBLE);
        }
        else if(tutorial == 2){

            view.findViewById(R.id.popup_imitaraudio_mensaje1).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_imitaraudio_mensaje12).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_imitaraudio_mensaje13).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_imitaraudio_mensaje3).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_imitaraudio_button4).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_imitaraudio_botonGrabar).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_imitaraudio_button2).setVisibility(View.INVISIBLE);

            view.findViewById(R.id.popup_imitaraudio_mensaje2).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_imitaraudio_textoFrecuencia).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_imitaraudio_prev).setVisibility(View.VISIBLE);

            button.setText("Siguiente");
        }
        else if(tutorial == 3){
            view.findViewById(R.id.popup_imitaraudio_mensaje2).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_imitaraudio_textoFrecuencia).setVisibility(View.INVISIBLE);

            view.findViewById(R.id.popup_imitaraudio_mensaje3).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_imitaraudio_button4).setVisibility(View.VISIBLE);

            button.setText("Cerrar");
        }
        else if(tutorial == 4){
            popupWindow.dismiss();
        }
    }

    public void actualizaPopUpCreaRitmos(View view){
        Button button = view.findViewById(R.id.popup_crearitmos_next);
        if(tutorial == 1){
            view.findViewById(R.id.popup_crearitmos_mensaje2).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_crearitmos_linearRitmo).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_crearitmos_prev).setVisibility(View.GONE);

            view.findViewById(R.id.popup_crearitmos_mensaje1).setVisibility(View.VISIBLE);
        }
        else if(tutorial == 2){

            view.findViewById(R.id.popup_crearitmos_mensaje1).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_crearitmos_mensaje3).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_crearitmos_linearLayout6).setVisibility(View.INVISIBLE);

            view.findViewById(R.id.popup_crearitmos_mensaje2).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_crearitmos_linearRitmo).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_crearitmos_prev).setVisibility(View.VISIBLE);

        }
        else if(tutorial == 3){
            view.findViewById(R.id.popup_crearitmos_mensaje4).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_crearitmos_mensaje2).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_crearitmos_linearOpcionesComprobar).setVisibility(View.INVISIBLE);

            view.findViewById(R.id.popup_crearitmos_mensaje3).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_crearitmos_linearLayout6).setVisibility(View.VISIBLE);

            button.setText("Siguiente");
        }
        else if(tutorial == 4){
            view.findViewById(R.id.popup_crearitmos_mensaje3).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_crearitmos_linearLayout6).setVisibility(View.INVISIBLE);

            view.findViewById(R.id.popup_crearitmos_mensaje4).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_crearitmos_linearOpcionesComprobar).setVisibility(View.VISIBLE);

            button.setText("Cerrar");
        }
        else if(tutorial == 5){
            popupWindow.dismiss();
        }
    }

    public void actualizaPopUpDinbujaRitmos(View view){
        Button button = view.findViewById(R.id.popup_hallaritmos_next);
        if(tutorial == 1){
            view.findViewById(R.id.popup_hallaritmos_layoutbotonesRitmo).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_hallaritmos_mensaje3).setVisibility(View.INVISIBLE);

            view.findViewById(R.id.popup_hallaritmos_layoutbotones).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_hallaritmos_prev).setVisibility(View.GONE);
            view.findViewById(R.id.popup_hallaritmos_mensaje2).setVisibility(View.VISIBLE);
        }
        else if(tutorial == 2){

            view.findViewById(R.id.popup_hallaritmos_prev).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_hallaritmos_mensaje2).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_hallaritmos_mensaje4).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_hallaritmos_mensaje5).setVisibility(View.INVISIBLE);
            view.findViewById(R.id.popup_hallaritmos_comprueba).setVisibility(View.INVISIBLE);

            view.findViewById(R.id.popup_hallaritmos_mensaje3).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_hallaritmos_layoutbotonesRitmo).setVisibility(View.VISIBLE);

            button.setText("Siguiente");
        }
        else if(tutorial == 3){

            view.findViewById(R.id.popup_hallaritmos_mensaje3).setVisibility(View.INVISIBLE);

            view.findViewById(R.id.popup_hallaritmos_mensaje4).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_hallaritmos_mensaje5).setVisibility(View.VISIBLE);
            view.findViewById(R.id.popup_hallaritmos_comprueba).setVisibility(View.VISIBLE);

            button.setText("Cerrar");
        }
        else if(tutorial == 4){
            popupWindow.dismiss();
        }
    }
}
