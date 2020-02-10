package com.example.prototipotfg;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.media.audiofx.AcousticEchoCanceler;
import android.media.audiofx.AutomaticGainControl;
import android.media.audiofx.NoiseSuppressor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Map;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;


public class ReproducirImitar extends Activity {


    private AudioDispatcher dispatcher;
    private ArrayList<NotasImitar> lista = new ArrayList<NotasImitar>();
    private NotasImitar resNota;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nivel_reproducir_imitar);

        int nivel = getIntent().getExtras().getInt("nivel");
        TextView titulo = (TextView)findViewById(R.id.tituloImitar);
        titulo.setText(titulo.getText() + String.valueOf(nivel));

        Button repetirNivel = (Button)findViewById(R.id.botonRepite);
        repetirNivel.setEnabled(false);
        repetirNivel.setVisibility(View.INVISIBLE);



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

    public void reproducir(View view){
        ProgressBar progresoReproducir = (ProgressBar)findViewById(R.id.progressReproducir);
        ProgressBar progresoGrabar = (ProgressBar)findViewById(R.id.progressGrabar);
        progresoReproducir.setVisibility(View.VISIBLE);
        progresoGrabar.setVisibility(View.INVISIBLE);


    }

    public void grabar(View view){
        ProgressBar progresoReproducir = (ProgressBar)findViewById(R.id.progressReproducir);
        ProgressBar progresoGrabar = (ProgressBar)findViewById(R.id.progressGrabar);
        progresoReproducir.setVisibility(View.INVISIBLE);
        progresoGrabar.setVisibility(View.VISIBLE);

    }

    public void comparar(View view){
        ProgressBar progresoReproducir = (ProgressBar)findViewById(R.id.progressReproducir);
        ProgressBar progresoGrabar = (ProgressBar)findViewById(R.id.progressGrabar);
        progresoReproducir.setVisibility(View.INVISIBLE);
        progresoGrabar.setVisibility(View.INVISIBLE);
    }





    //Va añadiendo notas junto con su octava a "lista" para asi hallar la nota que mas se repite en la secuencia que el dispatcher esta ON
    void hallaMax(float hz){
        if (hz != -1) {
            Integer octava = 4;
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
                while (!esNota) {
                    esNota = compruebaSiEsNota(hz, n, lista, octava);
                }
            }
        }
            /*
            if (hz >= Notas.DO.getMinimaFrecuencia() && hz <= Notas.DO.getMaximaFrecuencia()) {
                if(lista.size() > 0) {
                    if (lista.contains(new NotasImitar(Notas.DO, octava))) {
                        lista.set(lista.indexOf(new NotasImitar(Notas.DO, octava)), new NotasImitar(Notas.DO, octava, lista.indexOf(new NotasImitar(Notas.DO, octava).contador + 1)));
                    } else {
                        lista.add(new NotasImitar(Notas.DO, octava, 1));
                    }
                }
                else {
                    lista.add(new NotasImitar(Notas.DO, octava, 1));
                }
            }
            if (hz >= Notas.DOS.getMinimaFrecuencia() && hz <= Notas.DOS.getMaximaFrecuencia()) {
                if(lista.size() > 0) {
                    if (lista.contains(new NotasImitar(Notas.DOS, octava))) {
                        lista.set(lista.indexOf(new NotasImitar(Notas.DOS, octava)), new NotasImitar(Notas.DOS, octava, lista.indexOf(new NotasImitar(Notas.DOS, octava).contador + 1)));
                    } else {
                        lista.add(new NotasImitar(Notas.DOS, octava, 1));
                    }
                }
                else {
                    lista.add(new NotasImitar(Notas.DOS, octava, 1));
                }
            }
            if (hz >= Notas.RE.getMinimaFrecuencia() && hz <= Notas.RE.getMaximaFrecuencia()) {
                if(lista.size() > 0) {
                    if (lista.contains(new NotasImitar(Notas.RE, octava))) {
                        lista.set(lista.indexOf(new NotasImitar(Notas.RE, octava)), new NotasImitar(Notas.RE, octava, lista.indexOf(new NotasImitar(Notas.RE, octava).contador + 1)));
                    } else {
                        lista.add(new NotasImitar(Notas.RE, octava, 1));
                    }
                }
                else {
                    lista.add(new NotasImitar(Notas.RE, octava, 1));
                }
            }
            if (hz >= Notas.RES.getMinimaFrecuencia() && hz <= Notas.RES.getMaximaFrecuencia()) {
                if(lista.size() > 0) {
                    if (lista.contains(new NotasImitar(Notas.RES, octava))) {
                        lista.set(lista.indexOf(new NotasImitar(Notas.RES, octava)), new NotasImitar(Notas.RES, octava, lista.indexOf(new NotasImitar(Notas.RES, octava).contador + 1)));
                    } else {
                        lista.add(new NotasImitar(Notas.RES, octava, 1));
                    }
                }
            }
            if (hz >= Notas.MI.getMinimaFrecuencia() && hz <= Notas.MI.getMaximaFrecuencia()) {
                if(lista.size() > 0) {
                    if (lista.contains(new NotasImitar(Notas.MI, octava))) {
                        lista.set(lista.indexOf(new NotasImitar(Notas.MI, octava)), new NotasImitar(Notas.DO, octava, lista.indexOf(new NotasImitar(Notas.MI, octava).contador + 1)));
                    } else {
                        lista.add(new NotasImitar(Notas.MI, octava, 1));
                    }
                }
                else {
                    lista.add(new NotasImitar(Notas.MI, octava, 1));
                }
            }
            if (hz >= Notas.FA.getMinimaFrecuencia() && hz <= Notas.FA.getMaximaFrecuencia()) {
                if(lista.size() > 0) {
                    if (lista.contains(new NotasImitar(Notas.FA, octava))) {
                        lista.set(lista.indexOf(new NotasImitar(Notas.FA, octava)), new NotasImitar(Notas.FA, octava, lista.indexOf(new NotasImitar(Notas.FA, octava).contador + 1)));
                    } else {
                        lista.add(new NotasImitar(Notas.FA, octava, 1));
                    }
                }
                else {
                    lista.add(new NotasImitar(Notas.FA, octava, 1));
                }
            }
            if (hz >= Notas.FAS.getMinimaFrecuencia() && hz <= Notas.FAS.getMaximaFrecuencia()) {
                if(lista.size() > 0) {
                    if (lista.contains(new NotasImitar(Notas.FAS, octava))) {
                        lista.set(lista.indexOf(new NotasImitar(Notas.FAS, octava)), new NotasImitar(Notas.FAS, octava, lista.indexOf(new NotasImitar(Notas.FAS, octava).contador + 1)));
                    } else {
                        lista.add(new NotasImitar(Notas.FAS, octava, 1));
                    }
                }
                else {
                    lista.add(new NotasImitar(Notas.FAS, octava, 1));
                }
            }
            if (hz >= Notas.SOL.getMinimaFrecuencia() && hz <= Notas.SOL.getMaximaFrecuencia()) {
                if(lista.size() > 0) {
                    if (lista.contains(new NotasImitar(Notas.SOL, octava))) {
                        lista.set(lista.indexOf(new NotasImitar(Notas.SOL, octava)), new NotasImitar(Notas.SOL, octava, lista.indexOf(new NotasImitar(Notas.SOL, octava).contador + 1)));
                    } else {
                        lista.add(new NotasImitar(Notas.SOL, octava, 1));
                    }
                }
            }
            if (hz >= Notas.SOLS.getMinimaFrecuencia() && hz <= Notas.SOLS.getMaximaFrecuencia()) {
                if(lista.size() > 0) {
                    if (lista.contains(new NotasImitar(Notas.SOLS, octava))) {
                        lista.set(lista.indexOf(new NotasImitar(Notas.SOLS, octava)), new NotasImitar(Notas.SOLS, octava, lista.indexOf(new NotasImitar(Notas.SOLS, octava).contador + 1)));
                    } else {
                        lista.add(new NotasImitar(Notas.SOLS, octava, 1));
                    }
                }
                else {
                    lista.add(new NotasImitar(Notas.SOLS, octava, 1));
                }
            }
            if (hz >= Notas.LA.getMinimaFrecuencia() && hz <= Notas.LA.getMaximaFrecuencia()) {
                if(lista.size() > 0) {
                    if (lista.contains(new NotasImitar(Notas.LA, octava))) {
                        lista.set(lista.indexOf(new NotasImitar(Notas.LA, octava)), new NotasImitar(Notas.LA, octava, lista.indexOf(new NotasImitar(Notas.LA, octava).contador + 1)));
                    } else {
                        lista.add(new NotasImitar(Notas.LA, octava, 1));
                    }
                }
                else {
                    lista.add(new NotasImitar(Notas.LA, octava, 1));
                }
            }
            if (hz >= Notas.LAS.getMinimaFrecuencia() && hz <= Notas.LAS.getMaximaFrecuencia()) {
                if(lista.size() > 0) {
                    if (lista.contains(new NotasImitar(Notas.LAS, octava))) {
                        lista.set(lista.indexOf(new NotasImitar(Notas.LAS, octava)), new NotasImitar(Notas.LAS, octava, lista.indexOf(new NotasImitar(Notas.LAS, octava).contador + 1)));
                    } else {
                        lista.add(new NotasImitar(Notas.LAS, octava, 1));
                    }
                }
                else {
                    lista.add(new NotasImitar(Notas.LAS, octava, 1));
                }
            }
            if (hz >= Notas.SI.getMinimaFrecuencia() && hz <= Notas.SI.getMaximaFrecuencia()) {
                if(lista.size() > 0) {
                    if (lista.contains(new NotasImitar(Notas.SI, octava))) {
                        lista.set(lista.indexOf(new NotasImitar(Notas.SI, octava)), new NotasImitar(Notas.SI, octava, lista.indexOf(new NotasImitar(Notas.SI, octava).contador + 1)));
                    } else {
                        lista.add(new NotasImitar(Notas.SI, octava, 1));
                    }
                }
                else {
                    lista.add(new NotasImitar(Notas.SI, octava, 1));
                }
            }*/


    }

    private boolean compruebaSiEsNota(float hz, Notas n, ArrayList<NotasImitar> lista, int octava) {
        if (hz >= n.getMinimaFrecuencia() && hz <= n.getMaximaFrecuencia()) {
            if(lista.size() > 0) {

                if (lista.contains(new NotasImitar(n, octava))) {
                    lista.set(lista.indexOf(new NotasImitar(n, octava)), new NotasImitar(n, octava, lista.indexOf(new NotasImitar(n, octava).contador + 1)));
            } else {
                    lista.add(new NotasImitar(n, octava, 1));
            }
            }
            else {
                lista.add(new NotasImitar(Notas.SI, octava, 1));
            }
            return true;
        }
        return false;
    }

    public void Grabar(View view) throws InterruptedException {
        Button botonGrabar = (Button)findViewById(R.id.botonGrabar);
        botonGrabar.setVisibility(View.INVISIBLE);
        botonGrabar.setEnabled(false);
        Button repetirNivel = (Button)findViewById(R.id.botonRepite);
        repetirNivel.setVisibility(View.VISIBLE);
        repetirNivel.setEnabled(true);
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

                                TextView text2 = (TextView) findViewById(R.id.textoFrecuencia);
                                text2.setText("Resultado: " + resNota.nota + (resNota.octava-1));

                                TextView text1 = (TextView) findViewById(R.id.timer_id);
                                text1.setText("Fin");

                            }

                        });

                        return;

                    }
                }).start();

            }

            @Override
            public void onTick(long millisUntilFinished) {
                //texto a mostrar en cuenta regresiva en un textview
                TextView countdownText = (TextView) findViewById(R.id.textoFrecuencia);
                countdownText.setText((((millisUntilFinished+1000)/1000)+""));
            }
        }
        final MiContador timer = new MiContador(3000,1000);
        timer.start();

    }

    public void repetirNivel(View view){
        startActivity(getIntent());
    }

}