package com.example.prototipotfg;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prototipotfg.Singletons.GestorBBDD;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //if(GestorBBDD.getInstance().esPrimeraVezApp())
            mostrarPopupRangos(findViewById(android.R.id.content).getRootView());

    }


    public void ejecutar_jugar(View view) {

        Intent i = new Intent(this, MenuJugar.class);
        startActivity(i);
    }

    public void ejecutar_Perfil(View view) {

        if(!GestorBBDD.getInstance().getUsuarioLoggeado().getCorreo().equals("usuario@prueba.com")) {
            Intent i = new Intent(this, Perfil.class);
            startActivity(i);
        }
        else
            Toast.makeText(this, "No puedes editar el usuario de prueba", Toast.LENGTH_SHORT).show();
    }

    public void mostrarEstadisticas(View view){

        Intent i = new Intent(this, Estadisticas.class);
        startActivity(i);
    }

    public void tutoriales(View view){

        Intent i = new Intent(this, Tutoriales.class);
        startActivity(i);
    }

    protected void onStop(){
        super.onStop();

    }
    public void salir(View view){
        GestorBBDD.getInstance().cerrarSesion();
        Intent i = new Intent(this, Login.class);
        startActivity(i);
    }

    public void mostrarPopupRangos(final View view){
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);

        final View popupView = inflater.inflate(R.layout.popup_tutorial_rangos, null);

        // create the popup window
        //final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
        final PopupWindow popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken


        view.post(new Runnable() {
            public void run() {
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
            }
        });

        Button cerrar = (Button)popupView.findViewById(R.id.boton_cerrar_tutorialrangos);
        cerrar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        // popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched

    }
}
