package com.example.prototipotfg.Ritmos;

import android.annotation.TargetApi;
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

import com.example.prototipotfg.BBDD.NivelAdivinar;
import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Ritmos.Metronomo;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.Controlador;
import com.example.prototipotfg.Singletons.GestorBBDD;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class HallaRitmos extends Activity {

    private ArrayList<Integer> ritmos1;
    private ArrayList<Integer> ritmos2;
    private ArrayList<Integer> ritmos3;
    private ArrayList<Integer> ritmos4;
    private View botonesSeleccionados1[]= new View[16];
    private View botonesSeleccionados2[]= new View[16];
    private View botonesSeleccionados3[]= new View[16];
    private View botonesSeleccionados4[]= new View[16];
    private View botonesResultado1[]= new View[16];
    private View botonesResultado2[]= new View[16];
    private View botonesResultado3[]= new View[16];
    private View botonesResultado4[]= new View[16];
    private boolean running;
    private boolean go1 = false;
    private boolean go2 = false;
    private boolean go3 = false;
    private boolean go4 = false;
    private boolean goMetronomo = false;
    private boolean tick = false;
    private boolean end = false;
    private boolean play = false;
    private boolean pause = false;
    private int nivel;
    private int indice = 0;
    private int bpm = 60;
    private int compases = 64;


    private ArrayList<Integer> resultado1 = new ArrayList<>();
    private ArrayList<Integer> resultado2 = new ArrayList<>();
    private ArrayList<Integer> resultado3 = new ArrayList<>();
    private ArrayList<Integer> resultado4 = new ArrayList<>();
    private ArrayList<Integer> metronomo = new ArrayList<>();
    private MediaPlayerRitmos mediaPlayer1 =  new MediaPlayerRitmos();
    private MediaPlayerRitmos mediaPlayer2 =  new MediaPlayerRitmos();
    private MediaPlayerRitmos mediaPlayer3 =  new MediaPlayerRitmos();
    private MediaPlayerRitmos mediaPlayer4 =  new MediaPlayerRitmos();
    private MediaPlayerRitmos mediaPlayerMetronomo =  new MediaPlayerRitmos();

    private  Thread hilo_ritmos = new Thread(new Runnable(){
        @Override
        public void run() {
            int i = 0;
            while(!end) {
                while (indice < compases && running) {
                    play = false;
                    if (indice == 0)
                        i = 0;
                    if (metronomo.get(i) == 1) {
                        if (i == 0)
                            tick = true;
                        goMetronomo = true;
                    }
                    int notaActual1 = ritmos1.get(i);
                    if (notaActual1 == 1) {
                        go1 = true;
                    }
                    if (nivel > 2) {
                        int notaActual2 = ritmos2.get(i);
                        if (notaActual2 == 1) {
                            go2 = true;
                        }
                        if (nivel > 4) {
                            int notaActual3 = ritmos3.get(i);
                            if (notaActual3 == 1) {
                                go3 = true;
                            }
                            if (nivel > 6) {
                                int notaActual4 = ritmos4.get(i);
                                if (notaActual4 == 1) {
                                    go4 = true;
                                }
                            }
                        }
                    }
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (!play) {
                        i++;
                        indice++;
                    }
                    if (i >= 16)
                        i = i - 16;
                    if(indice >= compases)
                        running = false;
                }
            }
        }

    });

    private Thread hiloPlayer1 = new Thread(new Runnable() {

        @Override
        public void run() {
            try {
                while(!end) {
                    while (running) {
                        if (go1) {
                            mediaPlayer1.play();
                            go1 = false;
                        }
                        if (go2) {
                            mediaPlayer2.play();
                            go2 = false;
                        }
                        if (go3) {
                            mediaPlayer3.play();
                            go3 = false;
                        }
                        if (go4) {
                            mediaPlayer4.play();
                            go4 = false;
                        }
                        if (goMetronomo) {
                            mediaPlayerMetronomo.playMetronomo(tick);
                            goMetronomo = false;
                            tick = false;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });
    /*
    private Thread hiloPlayer2 = new Thread(new Runnable() {

        @Override
        public void run() {
            try {
                while(running) {
                    if (go2) {
                        mediaPlayer2.play();
                        go2 = false;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });
    private Thread hiloPlayer3 = new Thread(new Runnable() {

        @Override
        public void run() {
            try {
                while(running) {
                    if (go3) {
                        mediaPlayer3.play();
                        go3 = false;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });
    private Thread hiloPlayer4 = new Thread(new Runnable() {

        @Override
        public void run() {
            try {
                while(running) {
                    if (go4) {
                        mediaPlayer4.play();
                        go4 = false;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });
    */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hallaritmos);


        nivel = getIntent().getExtras().getInt("nivel");
        ritmos1 = getIntent().getExtras().getIntegerArrayList("ritmos1");
        ritmos2 = getIntent().getExtras().getIntegerArrayList("ritmos2");
        ritmos3 = getIntent().getExtras().getIntegerArrayList("ritmos3");
        ritmos4 = getIntent().getExtras().getIntegerArrayList("ritmos4");

        TextView titulo = (TextView)findViewById(R.id.tituloHallaRitmo);
        titulo.setText(titulo.getText() + String.valueOf(nivel));

        running = false;
        hilo_ritmos.start();
        hiloPlayer1.start();

        mediaPlayerMetronomo.initMetronomo(this);
        mediaPlayer1.init1(this);
        findViewById(R.id.textRitmos1).setVisibility(View.VISIBLE);
        if(nivel>2) {
            //hiloPlayer2.start();
            mediaPlayer2.init2(this);
            findViewById(R.id.textRitmos2).setVisibility(View.VISIBLE);
            if(nivel>4) {
                //hiloPlayer3.start();
                mediaPlayer3.init3(this);
                findViewById(R.id.textRitmos3).setVisibility(View.VISIBLE);
                if(nivel > 6) {
                 //   hiloPlayer4.start();
                    mediaPlayer4.init4(this);
                    findViewById(R.id.textRitmos4).setVisibility(View.VISIBLE);
                }
            }
        }

        final Context contexto = this;


        //Obtenemos el linear layout donde colocar los botones
        LinearLayout llBotonera1 = (LinearLayout) findViewById(R.id.botoneraRitmos1);
        LinearLayout llBotonera2 = (LinearLayout) findViewById(R.id.botoneraRitmos2);
        LinearLayout llBotonera3 = (LinearLayout) findViewById(R.id.botoneraRitmos3);
        LinearLayout llBotonera4 = (LinearLayout) findViewById(R.id.botoneraRitmos4);
        //Creamos las propiedades de layout que tendrán los botones.
        //Son LinearLayout.LayoutParams porque los botones van a estar en un LinearLayout.
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(80, LinearLayout.LayoutParams.WRAP_CONTENT);
        for(int j=0; j<(nivel+1)/2; j++) {
            //Creamos los botones en bucle
            final int finalJ = j;
            for (int i = 0; i < 16; i++) {
                final Button button = new Button(this);
                button.setId(i + 1);
                //Asignamos propiedades de layout al boton
                button.setLayoutParams(lp);
                //Asignamos el Listener
                final int finalI = i;
                //Añadimos el botón a la botonera
                if (i < 4) {


                    button.setOnClickListener(new View.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void onClick(View v) {
                            if (finalJ == 0) {
                                if (resultado1.get((int) button.getId() - 1) == 1) {

                                    button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_blue_300)));

                                    resultado1.set(((int) button.getId() - 1), 0);
                                    botonesSeleccionados1[(int) button.getId() - 1] = null;
                                } else {

                                    button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_blue_700)));

                                    resultado1.set(((int) button.getId() - 1), 1);
                                    botonesSeleccionados1[(int) button.getId() - 1] = button;
                                }
                            }
                            if (finalJ == 1) {
                                if (resultado2.get((int) button.getId() - 1) == 1) {

                                    button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_blue_300)));

                                    resultado2.set(((int) button.getId() - 1), 0);
                                    botonesSeleccionados2[(int) button.getId() - 1] = null;
                                } else {

                                    button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_blue_700)));

                                    resultado2.set(((int) button.getId() - 1), 1);
                                    botonesSeleccionados2[(int) button.getId() - 1] = button;
                                }
                            }
                            if (finalJ == 2) {
                                if (resultado3.get((int) button.getId() - 1) == 1) {

                                    button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_blue_300)));

                                    resultado3.set(((int) button.getId() - 1), 0);
                                    botonesSeleccionados3[(int) button.getId() - 1] = null;
                                } else {

                                    button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_blue_700)));

                                    resultado3.set(((int) button.getId() - 1), 1);
                                    botonesSeleccionados3[(int) button.getId() - 1] = button;
                                }
                            }
                            if (finalJ == 3) {
                                if (resultado4.get((int) button.getId() - 1) == 1) {

                                    button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_blue_300)));

                                    resultado4.set(((int) button.getId() - 1), 0);
                                    botonesSeleccionados4[(int) button.getId() - 1] = null;
                                } else {

                                    button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_blue_700)));

                                    resultado4.set(((int) button.getId() - 1), 1);
                                    botonesSeleccionados4[(int) button.getId() - 1] = button;
                                }
                            }
                        }
                    });
                    if(j==0) {
                        llBotonera1.addView(button);
                        if(ritmos1.get(i)==1){
                            botonesResultado1[(int) button.getId() - 1]=button;
                        }
                    }
                    else if(j==1) {
                        llBotonera4.addView(button);
                        if(ritmos2.get(i)==1){
                            botonesResultado2[(int) button.getId() - 1]=button;
                        }
                    }
                    else if(j==2) {
                        llBotonera3.addView(button);
                        if(ritmos3.get(i)==1){
                            botonesResultado3[(int) button.getId() - 1]=button;
                        }
                    }
                    else if(j==3) {
                        llBotonera2.addView(button);
                        if(ritmos4.get(i)==1){
                            botonesResultado4[(int) button.getId() - 1]=button;
                        }
                    }
                } else if (i >= 4 && i < 8) {

                    button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_cyan_300)));

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (finalJ == 0) {
                                if (resultado1.get((int) button.getId() - 1) == 1) {

                                    button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_cyan_300)));

                                    resultado1.set(((int) button.getId() - 1), 0);
                                    botonesSeleccionados1[(int) button.getId() - 1] = null;
                                } else {

                                    button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_cyan_800)));

                                    resultado1.set(((int) button.getId() - 1), 1);
                                    botonesSeleccionados1[(int) button.getId() - 1] = button;
                                }
                            }
                            if (finalJ == 1) {
                                if (resultado2.get((int) button.getId() - 1) == 1) {

                                    button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_cyan_300)));

                                    resultado2.set(((int) button.getId() - 1), 0);
                                    botonesSeleccionados2[(int) button.getId() - 1] = null;
                                } else {

                                    button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_cyan_800)));

                                    resultado2.set(((int) button.getId() - 1), 1);
                                    botonesSeleccionados2[(int) button.getId() - 1] = button;
                                }
                            }
                            if (finalJ == 2) {
                                if (resultado3.get((int) button.getId() - 1) == 1) {

                                    button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_cyan_300)));

                                    resultado3.set(((int) button.getId() - 1), 0);
                                    botonesSeleccionados3[(int) button.getId() - 1] = null;
                                } else {

                                    button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_cyan_800)));

                                    resultado3.set(((int) button.getId() - 1), 1);
                                    botonesSeleccionados3[(int) button.getId() - 1] = button;
                                }
                            }
                            if (finalJ == 3) {
                                if (resultado4.get((int) button.getId() - 1) == 1) {

                                    button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_cyan_300)));

                                    resultado4.set(((int) button.getId() - 1), 0);
                                    botonesSeleccionados4[(int) button.getId() - 1] = null;
                                } else {

                                    button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_cyan_800)));

                                    resultado4.set(((int) button.getId() - 1), 1);
                                    botonesSeleccionados4[(int) button.getId() - 1] = button;
                                }
                            }

                        }
                    });
                    if(j==0) {
                        llBotonera1.addView(button);
                        if(ritmos1.get(i)==1){
                            botonesResultado1[(int) button.getId() - 1]=button;
                        }
                    }
                    else if(j==1) {
                        llBotonera4.addView(button);
                        if(ritmos2.get(i)==1){
                            botonesResultado2[(int) button.getId() - 1]=button;
                        }
                    }
                    else if(j==2) {
                        llBotonera3.addView(button);
                        if(ritmos3.get(i)==1){
                            botonesResultado3[(int) button.getId() - 1]=button;
                        }
                    }
                    else if(j==3) {
                        llBotonera2.addView(button);
                        if(ritmos4.get(i)==1){
                            botonesResultado4[(int) button.getId() - 1]=button;
                        }
                    }
                } else if (i >= 8 && i < 12) {
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (finalJ == 0) {
                                if (resultado1.get((int) button.getId() - 1) == 1) {

                                    button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_blue_300)));

                                    resultado1.set(((int) button.getId() - 1), 0);
                                    botonesSeleccionados1[(int) button.getId() - 1] = null;
                                } else {

                                    button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_blue_700)));

                                    resultado1.set(((int) button.getId() - 1), 1);
                                    botonesSeleccionados1[(int) button.getId() - 1] = button;
                                }
                            }
                            if (finalJ == 1) {
                                if (resultado2.get((int) button.getId() - 1) == 1) {

                                    button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_blue_300)));

                                    resultado2.set(((int) button.getId() - 1), 0);
                                    botonesSeleccionados2[(int) button.getId() - 1] = null;
                                } else {

                                    button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_blue_700)));

                                    resultado2.set(((int) button.getId() - 1), 1);
                                    botonesSeleccionados2[(int) button.getId() - 1] = button;
                                }
                            }
                            if (finalJ == 2) {
                                if (resultado3.get((int) button.getId() - 1) == 1) {

                                    button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_blue_300)));

                                    resultado3.set(((int) button.getId() - 1), 0);
                                    botonesSeleccionados3[(int) button.getId() - 1] = null;
                                } else {

                                    button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_blue_700)));

                                    resultado3.set(((int) button.getId() - 1), 1);
                                    botonesSeleccionados3[(int) button.getId() - 1] = button;
                                }
                            }
                            if (finalJ == 3) {
                                if (resultado4.get((int) button.getId() - 1) == 1) {

                                    button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_blue_300)));

                                    resultado4.set(((int) button.getId() - 1), 0);
                                    botonesSeleccionados4[(int) button.getId() - 1] = null;
                                } else {

                                    button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_blue_700)));

                                    resultado4.set(((int) button.getId() - 1), 1);
                                    botonesSeleccionados4[(int) button.getId() - 1] = button;
                                }
                            }

                        }
                    });
                    if(j==0) {
                        llBotonera1.addView(button);
                        if(ritmos1.get(i)==1){
                            botonesResultado1[(int) button.getId() - 1]=button;
                        }
                    }
                    else if(j==1) {
                        llBotonera4.addView(button);
                        if(ritmos2.get(i)==1){
                            botonesResultado2[(int) button.getId() - 1]=button;
                        }
                    }
                    else if(j==2) {
                        llBotonera3.addView(button);
                        if(ritmos3.get(i)==1){
                            botonesResultado3[(int) button.getId() - 1]=button;
                        }
                    }
                    else if(j==3) {
                        llBotonera2.addView(button);
                        if(ritmos4.get(i)==1){
                            botonesResultado4[(int) button.getId() - 1]=button;
                        }
                    }
                } else if (i >= 12 && i < 16) {
                    button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_cyan_300)));
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (finalJ == 0) {
                                if (resultado1.get((int)button.getId() - 1) == 1) {

                                    button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_cyan_300)));

                                    resultado1.set(((int) button.getId() - 1), 0);
                                    botonesSeleccionados1[(int)button.getId() - 1] = null;
                                } else {

                                    button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_cyan_800)));

                                    resultado1.set(((int) button.getId() - 1), 1);
                                    botonesSeleccionados1[(int) button.getId() - 1] = button;
                                }
                            }
                            if (finalJ == 1) {
                                if (resultado2.get((int) button.getId() - 1) == 1) {

                                    button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_cyan_300)));

                                    resultado2.set(((int) button.getId() - 1), 0);
                                    botonesSeleccionados2[(int) button.getId() - 1] = null;
                                } else {

                                    button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_cyan_800)));

                                    resultado2.set(((int) button.getId() - 1), 1);
                                    botonesSeleccionados2[(int) button.getId() - 1] = button;
                                }
                            }
                            if (finalJ == 2) {
                                if (resultado3.get((int) button.getId() - 1) == 1) {

                                    button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_cyan_300)));

                                    resultado3.set(((int) button.getId() - 1), 0);
                                    botonesSeleccionados3[(int) button.getId() - 1] = null;
                                } else {

                                    button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_cyan_800)));

                                    resultado3.set(((int) button.getId() - 1), 1);
                                    botonesSeleccionados3[(int) button.getId() - 1] = button;
                                }
                            }
                            if (finalJ == 3) {
                                if (resultado4.get((int) button.getId() - 1) == 1) {

                                    button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_cyan_300)));

                                    resultado4.set(((int) button.getId() - 1), 0);
                                    botonesSeleccionados4[(int) button.getId() - 1] = null;
                                } else {

                                    button.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(contexto, R.color.md_cyan_800)));

                                    resultado4.set(((int) button.getId() - 1), 1);
                                    botonesSeleccionados4[(int) button.getId() - 1] = button;
                                }
                            }

                        }
                    });
                    if(j==0) {
                        llBotonera1.addView(button);
                        if(ritmos1.get(i)==1){
                            botonesResultado1[(int) button.getId() - 1]=button;
                        }
                    }
                    else if(j==1) {
                        llBotonera4.addView(button);
                        if(ritmos2.get(i)==1){
                            botonesResultado2[(int) button.getId() - 1]=button;
                        }
                    }
                    else if(j==2) {
                        llBotonera3.addView(button);
                        if(ritmos3.get(i)==1){
                            botonesResultado3[(int) button.getId() - 1]=button;
                        }
                    }
                    else if(j==3) {
                        llBotonera2.addView(button);
                        if(ritmos4.get(i)==1){
                            botonesResultado4[(int) button.getId() - 1]=button;
                        }
                    }
                }

                //Aprovecho el bucle para rellenar el array
                resultado1.add(0);
                resultado2.add(0);
                resultado3.add(0);
                resultado4.add(0);
                if(i%4==0){
                    metronomo.add(i,1);
                }
                else{
                    metronomo.add(i,0);
                }
            }
        }

    }


    public void play(@NotNull final View view){
        if(running == true){
            indice = 0;
            play = true;
        }
        else {
            running = true;
            if(pause == false)
                indice = 0;
            pause = false;
        }
    }

    public void pause(@NotNull final View view){
        running = false;
        pause = true;
    }

    public void para(@NotNull final View view){
        running = false;
    }

    public void stop(View view){
        NivelAdivinar nivel;
        int aciertos=0;
        if(resultado1.equals(ritmos1)){
            //Correct
            for(int i = 0; i<16; i++){
                if(botonesSeleccionados1[i]!=null){
                    botonesSeleccionados1[i].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
                }
            }
            aciertos++;
        }
        else{
            //Incorrect
            for(int i = 0; i<16; i++){
                if(botonesSeleccionados1[i]!=null){
                    botonesSeleccionados1[i].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_red_500)));
                }
                if(botonesResultado1[i]!=null)
                    botonesResultado1[i].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
            }

        }
        if(resultado2.equals(ritmos2)){
            //Correct
            for(int i = 0; i<16; i++){
                if(botonesSeleccionados2[i]!=null){
                    botonesSeleccionados2[i].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
                }
            }
            aciertos++;
        }
        else{
            //Incorrect
            for(int i = 0; i<16; i++){
                if(botonesSeleccionados2[i]!=null){
                    botonesSeleccionados2[i].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_red_500)));
                }
                if(botonesResultado2[i]!=null)
                    botonesResultado2[i].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
            }

        }
        if(resultado3.equals(ritmos3)){
            //Correct
            for(int i = 0; i<16; i++){
                if(botonesSeleccionados3[i]!=null){
                    botonesSeleccionados3[i].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
                }
            }
            aciertos++;
        }
        else{
            //Incorrect
            for(int i = 0; i<16; i++){
                if(botonesSeleccionados3[i]!=null){
                    botonesSeleccionados3[i].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_red_500)));
                }
                if(botonesResultado3[i]!=null)
                    botonesResultado3[i].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
            }
        }
        if(resultado4.equals(ritmos4)){
            //Correct
            for(int i = 0; i<16; i++){
                if(botonesSeleccionados4[i]!=null){
                    botonesSeleccionados4[i].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
                }

            }
            aciertos++;
        }
        else{
            //Incorrect
            for(int i = 0; i<16; i++){
                if(botonesSeleccionados4[i]!=null){
                    botonesSeleccionados4[i].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_red_500)));
                }
                if(botonesResultado4[i]!=null)
                    botonesResultado4[i].setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
            }

        }
        if(aciertos == 4){
            if(Controlador.getInstance().getNivel() == GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Halla_Ritmo.getNombre()).getNivel())
                GestorBBDD.getInstance().actualizarPuntuacion(Controlador.getInstance().getNivel(), ModoJuego.Halla_Ritmo.toString(), true);
            nivel  = new NivelAdivinar(ModoJuego.Halla_Ritmo.toString(), this.nivel,true, GestorBBDD.getInstance().getUsuarioLoggeado().getCorreo(), 1, 0 );
        }
        else{
            if(Controlador.getInstance().getNivel() == GestorBBDD.getInstance().devuelvePuntuacion(ModoJuego.Halla_Ritmo.getNombre()).getNivel())
                GestorBBDD.getInstance().actualizarPuntuacion(Controlador.getInstance().getNivel(), ModoJuego.Halla_Ritmo.toString(), false);
            nivel  = new NivelAdivinar(ModoJuego.Halla_Ritmo.toString(), this.nivel,false, GestorBBDD.getInstance().getUsuarioLoggeado().getCorreo(), 0, 1 );
        }
        GestorBBDD.getInstance().insertaNivelAdivinar(nivel);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        running = false;
        end = true;
        hiloPlayer1.interrupt();
        mediaPlayer1.stop();
        mediaPlayer2.stop();
        mediaPlayer3.stop();
        mediaPlayer4.stop();
        mediaPlayerMetronomo.stopMetronomo();
    }


}
