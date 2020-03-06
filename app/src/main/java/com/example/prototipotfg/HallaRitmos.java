package com.example.prototipotfg;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class HallaRitmos extends Activity {

    private ArrayList<Integer> ritmos;
    private Thread hilo_ritmos;
    private View botonesSeleccionados[]= new View[16];
    private boolean running;
    private boolean go = false;
    private int bpm = 60;
    private Metronomo m = new Metronomo(bpm, 4);

    private ImageView cross;
    private ImageView tick;

    private ArrayList<Integer> resultado = new ArrayList<>();
    private MediaPlayerRitmos mediaPlayer =  new MediaPlayerRitmos();
    private Thread hiloPlayer = new Thread(new Runnable() {

        @Override
        public void run() {
            try {
                while(running) {
                    if (go) {
                        mediaPlayer.play();
                        go = false;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hallaritmos);


        int nivel = getIntent().getExtras().getInt("nivel");
        ritmos = getIntent().getExtras().getIntegerArrayList("ritmos");
        TextView titulo = (TextView)findViewById(R.id.tituloHallaRitmo);
        titulo.setText(titulo.getText() + String.valueOf(nivel));
        running = true;
        hiloPlayer.start();
        mediaPlayer.init(this);

        final Context contexto = this;
        cross = (ImageView)findViewById(R.id.cross);
        tick = (ImageView)findViewById(R.id.tick);
        cross.setVisibility(View.INVISIBLE);
        tick.setVisibility(View.INVISIBLE);


        //Obtenemos el linear layout donde colocar los botones
        LinearLayout llBotonera1 = (LinearLayout) findViewById(R.id.botoneraRitmos1);
        LinearLayout llBotonera2 = (LinearLayout) findViewById(R.id.botoneraRitmos2);
        LinearLayout llBotonera3 = (LinearLayout) findViewById(R.id.botoneraRitmos3);
        LinearLayout llBotonera4 = (LinearLayout) findViewById(R.id.botoneraRitmos4);
        //Creamos las propiedades de layout que tendrán los botones.
        //Son LinearLayout.LayoutParams porque los botones van a estar en un LinearLayout.
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(200, 200);

        //Creamos los botones en bucle
        for (int i=0; i<16; i++){
            final Button button = new Button(this);
            button.setId(i+1);
            //Asignamos propiedades de layout al boton
            button.setLayoutParams(lp);
            //Asignamos el Listener
            final int finalI = i;
            //Añadimos el botón a la botonera
            if(i < 4) {

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (resultado.get((int) button.getId() - 1) == 1) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_blue_300)));
                            }
                            resultado.set(((int) button.getId() - 1), 0);
                            botonesSeleccionados[(int) button.getId() - 1] = null;
                        } else {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_blue_700)));
                            }
                            resultado.set(((int) button.getId() - 1), 1);
                            botonesSeleccionados[(int) button.getId() - 1] = button;
                        }

                    }
                });
                llBotonera1.addView(button);
            }
            else if(i>=4 && i<8) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_purple_300)));
                }
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (resultado.get((int) button.getId() - 1) == 1) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_purple_300)));
                            }
                            resultado.set(((int) button.getId() - 1), 0);
                            botonesSeleccionados[(int) button.getId() - 1] = null;
                        } else {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_purple_700)));
                            }
                            resultado.set(((int) button.getId() - 1), 1);
                            botonesSeleccionados[(int) button.getId() - 1] = button;
                        }

                    }
                });
                llBotonera2.addView(button);
            }
            else if(i>=8 && i<12) {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (resultado.get((int) button.getId() - 1) == 1) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_blue_300)));
                            }
                            resultado.set(((int) button.getId() - 1), 0);
                            botonesSeleccionados[(int) button.getId() - 1] = null;
                        } else {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_blue_700)));
                            }
                            resultado.set(((int) button.getId() - 1), 1);
                            botonesSeleccionados[(int) button.getId() - 1] = button;
                        }

                    }
                });
                llBotonera3.addView(button);
            }
            else if(i>=12 && i<16) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_purple_300)));
                }
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (resultado.get((int) button.getId() - 1) == 1) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_purple_300)));
                            }
                            resultado.set(((int) button.getId() - 1), 0);
                            botonesSeleccionados[(int) button.getId() - 1] = null;
                        } else {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_purple_700)));
                            }
                            resultado.set(((int) button.getId() - 1), 1);
                            botonesSeleccionados[(int) button.getId() - 1] = button;
                        }

                    }
                });
                llBotonera4.addView(button);
            }
            //Aprovecho el bucle para rellenar el array
            resultado.add(0);
        }


    }


    public void play(@NotNull final View view){

        //view.setEnabled(false);
        try {
            m.init(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        hilo_ritmos = new Thread(new Runnable(){
            @Override
            public void run(){
                Metronomo m = new Metronomo(bpm, 4);
                m.start();
                try {
                    sleep((long) (1000 * (60.0 / bpm)));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int i = 0;
                int indice = 0;
                ;
                while(i < 32 && running){
                    //Lanzar todo lo del media player en un nuevo hilo y a dormir
                    int notaActual = ritmos.get(indice);

                    if(notaActual==1) {
                        go=true;
                    }
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    i++;
                    indice++;
                    if (indice >= 16)
                        indice = indice-16;
                    if(!running) {
                        m.stop();
                    }

                }
                m.stop();
            }

        });
        hilo_ritmos.start();


    }

    public void stop(View view){

        if(resultado.equals(ritmos)){
            //Correct
            for(int i = 0; i<16; i++){
                if(botonesSeleccionados[i]!=null){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        botonesSeleccionados[i].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
                    }
                }
            }

        }
        else{
            //Incorrect
            for(int i = 0; i<16; i++){
                if(botonesSeleccionados[i]!=null){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        botonesSeleccionados[i].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_red_500)));
                    }
                }
            }

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        running = false;
    }


}
