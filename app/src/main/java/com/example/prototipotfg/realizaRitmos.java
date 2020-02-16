package com.example.prototipotfg;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.audiofx.AcousticEchoCanceler;
import android.media.audiofx.AutomaticGainControl;
import android.media.audiofx.NoiseSuppressor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.ArrayList;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;


public class realizaRitmos extends Activity {


    private AudioDispatcher dispatcher;
    private ArrayList<NotasImitar> lista = new ArrayList<NotasImitar>();
    private NotasImitar resNota;
    private ArrayList<String> nombres;
    private ArrayList<String> rutas;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nivel_reproducir_imitar);

        nombres = getIntent().getExtras().getStringArrayList("nombres");
        rutas = getIntent().getExtras().getStringArrayList("rutas");

        int nivel = getIntent().getExtras().getInt("nivel");
        TextView titulo = (TextView)findViewById(R.id.tituloImitar);
        titulo.setText(titulo.getText() + String.valueOf(nivel));

        TextView nota = findViewById(R.id.notaImitar);
        nota.setText(nombres.get(0));

        Button repetirNivel = (Button)findViewById(R.id.botonRepite);
        repetirNivel.setEnabled(false);
        repetirNivel.setVisibility(View.INVISIBLE);

        Button comparar = (Button)findViewById(R.id.button4);
        comparar.setEnabled(false);
        comparar.setVisibility(View.INVISIBLE);



        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1000);
        }

        AudioDispatcherFactory1 factory = new AudioDispatcherFactory1();
        dispatcher = factory.fromDefaultMicrophone(22050,1024,0);

        int id = factory.getAudioRecord().getAudioSessionId();

        if(AcousticEchoCanceler.isAvailable()) {
            AcousticEchoCanceler echo = AcousticEchoCanceler.create(id);
            echo.setEnabled(true);
            Log.d("Echo", "Off");
        }

        if(NoiseSuppressor.isAvailable()) {
            NoiseSuppressor noise = NoiseSuppressor.create(id);
            noise.setEnabled(true);
            Log.d("Noise", "Off");
        }
        if(AutomaticGainControl.isAvailable()) {
            AutomaticGainControl gain = AutomaticGainControl.create(id);
            gain.setEnabled(false);
            Log.d("Gain", "Off");
        }

        PitchDetectionHandler pdh = new PitchDetectionHandler() {
            @Override
            public void handlePitch(PitchDetectionResult result, AudioEvent e) {
                final float pitchInHz = result.getPitch();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        hallaMax(pitchInHz);
                    }
                }).start();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView text = (TextView) findViewById(R.id.textoFrecuencia);
                        text.setText("Escuchando...");

                    }
                });
            }
        };
        AudioProcessor p = new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, 22050, 1024, pdh);
        dispatcher.addAudioProcessor(p);


    }

    public void reproducir(View view) throws IOException{
        MediaPlayer mediaPlayer =  new MediaPlayer();
        AssetFileDescriptor afd = getAssets().openFd(rutas.get(0));
        mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
        mediaPlayer.prepare();
        mediaPlayer.start();

    }

    public void comparar(View view){
        TextView respuesta = (TextView)findViewById(R.id.respuesta);
        System.out.println(resNota.nota.getNombre()+(resNota.octava-1));
        System.out.println(nombres.get(0));
        if((resNota.nota.getNombre() + (resNota.octava)).equals(nombres.get(0))){
            respuesta.setText("Respuesta Correcta");
        }
        else{
            respuesta.setText("Respuesta Incorrecta");
        }
    }





    //Va añadiendo notas junto con su octava a "lista" para asi hallar la nota que mas se repite en la secuencia que el dispatcher esta ON
    void hallaMax(float hz){
        if (hz != -1) {
            Integer octava = 3;
            //Situa a la nota en la octava que le corresponde
            if (hz < Notas.DO.getMinimaFrecuencia()) {
                while (hz < Notas.DO.getMinimaFrecuencia()) {
                    hz = hz * 2;
                    octava = octava - 1;
                }
            } else if (hz > Notas.SI.getMaximaFrecuencia()) {
                while (hz > Notas.SI.getMaximaFrecuencia()) {
                    hz = hz / 2;
                    octava = octava + 1;
                }
            }
            boolean esNota = false;
            for (Notas n : Notas.values()) {
                if (!esNota) {
                    esNota = compruebaSiEsNota(hz, n, lista, octava);
                }
            }
        }


    }

    private boolean compruebaSiEsNota(float hz, Notas n, ArrayList<NotasImitar> lista, int octava) {
        if (hz >= n.getMinimaFrecuencia() && hz <= n.getMaximaFrecuencia()) {

            if (lista.contains(new NotasImitar(n, octava))) {
                    lista.set(lista.indexOf(new NotasImitar(n, octava)), new NotasImitar(n, octava, lista.indexOf(new NotasImitar(n, octava).contador + 1)));
            } else {
                    lista.add(new NotasImitar(n, octava, 1));
            }
            return true;
        }
        return false;
    }

    public void Grabar(View view) {
        Button botonGrabar = findViewById(R.id.botonGrabar);
        botonGrabar.setVisibility(View.INVISIBLE);
        botonGrabar.setEnabled(false);


        class MiContador extends CountDownTimer {

            public MiContador(long millisInFuture, long countDownInterval) {
                super(millisInFuture, countDownInterval);
            }

            @Override
            public void onFinish() {

                final Thread dispatch_Thread = new Thread(dispatcher,"Audio Dispatcher");
                Toast.makeText(getApplicationContext(), "La grabación comenzó", Toast.LENGTH_LONG).show();
                dispatch_Thread.start();

                new Thread(new Runnable() {
                    @Override
                    public void run() {


                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        dispatcher.stop();
                        dispatch_Thread.interrupt();

                        //Recorre el ArrayList "lista" para guardar en resNota la nota que mas se repite
                        resNota = lista.get(0);
                        for (int i = 1; i<lista.size(); i++) {
                            if(lista.get(i).contador > resNota.contador){
                                resNota = lista.get(i);
                            }
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                TextView text2 = findViewById(R.id.textoFrecuencia);
                                text2.setText("Resultado: " + resNota.nota.getNombre() + (resNota.octava));

                                TextView text1 = findViewById(R.id.timer_id);
                                text1.setText("Fin");

                                Button repetirNivel = (Button)findViewById(R.id.botonRepite);
                                repetirNivel.setVisibility(View.VISIBLE);
                                repetirNivel.setEnabled(true);

                                Button comparar = (Button)findViewById(R.id.button4);
                                comparar.setEnabled(true);
                                comparar.setVisibility(View.VISIBLE);

                            }

                        });


                        return;

                    }
                }).start();




            }

            @Override
            public void onTick(long millisUntilFinished) {
                //texto a mostrar en cuenta regresiva en un textview
                TextView countdownText = findViewById(R.id.textoFrecuencia);
                countdownText.setText((((millisUntilFinished+1000)/1000)+""));
            }

        }
        final MiContador timer = new MiContador(3000,1000);
        timer.start();


    }

    public void repetirNivel(View view){
        startActivity(getIntent());
        this.finish();
    }

}