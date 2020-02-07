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

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;


public class ReproducirImitar extends Activity {

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
                        hallaMax(pitchInHz, octava, contador);
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




    private AudioDispatcher dispatcher;
    private int [] contador = {0,0,0,0,0,0,0,0,0,0,0,0};
    private int octava = 4;
    private Notas resultado;

    void hallaMax(float hz, int octava, int contador[]){
        if(hz < Notas.DO.getMinimaFrecuencia()){
            while(hz < Notas.DO.getMinimaFrecuencia()){
                hz = hz*2;
                octava=octava-1;
            }
        }
        else if(hz > Notas.SI.getMaximaFrecuencia()){
            while(hz > Notas.SI.getMaximaFrecuencia()){
                hz = hz/2;
                octava=octava+1;
            }
        }
        if(hz >= Notas.DO.getMinimaFrecuencia() && hz <= Notas.DO.getMaximaFrecuencia()){
            contador[Notas.DO.getPosicion()]=contador[Notas.DO.getPosicion()]+1;
        }
        if(hz >= Notas.DOS.getMinimaFrecuencia() && hz <= Notas.DOS.getMaximaFrecuencia()){
            contador[Notas.DOS.getPosicion()]=contador[Notas.DOS.getPosicion()]+1;
        }
        if(hz >= Notas.RE.getMinimaFrecuencia() && hz <= Notas.RE.getMaximaFrecuencia()){
            contador[Notas.RE.getPosicion()]=contador[Notas.RE.getPosicion()]+1;
        }
        if(hz >= Notas.RES.getMinimaFrecuencia() && hz <= Notas.RES.getMaximaFrecuencia()){
            contador[Notas.RES.getPosicion()]=contador[Notas.RES.getPosicion()]+1;
        }
        if(hz >= Notas.MI.getMinimaFrecuencia() && hz <= Notas.MI.getMaximaFrecuencia()){
            contador[Notas.MI.getPosicion()]=contador[Notas.MI.getPosicion()]+1;
        }
        if(hz >= Notas.FA.getMinimaFrecuencia() && hz <= Notas.FA.getMaximaFrecuencia()){
            contador[Notas.FA.getPosicion()]=contador[Notas.FA.getPosicion()]+1;
        }
        if(hz >= Notas.FAS.getMinimaFrecuencia() && hz <= Notas.FAS.getMaximaFrecuencia()){
            contador[Notas.FAS.getPosicion()]=contador[Notas.FAS.getPosicion()]+1;
        }
        if(hz >= Notas.SOL.getMinimaFrecuencia() && hz <= Notas.SOL.getMaximaFrecuencia()){
            contador[Notas.SOL.getPosicion()]=contador[Notas.SOL.getPosicion()]+1;
        }
        if(hz >= Notas.SOLS.getMinimaFrecuencia() && hz <= Notas.SOLS.getMaximaFrecuencia()){
            contador[Notas.SOLS.getPosicion()]=contador[Notas.SOLS.getPosicion()]+1;
        }
        if(hz >= Notas.LA.getMinimaFrecuencia() && hz <= Notas.LA.getMaximaFrecuencia()){
            contador[Notas.LA.getPosicion()]=contador[Notas.LA.getPosicion()]+1;
        }
        if(hz >= Notas.LAS.getMinimaFrecuencia() && hz <= Notas.LAS.getMaximaFrecuencia()){
            contador[Notas.LAS.getPosicion()]=contador[Notas.LAS.getPosicion()]+1;
        }
        if(hz >= Notas.SI.getMinimaFrecuencia() && hz <= Notas.SI.getMaximaFrecuencia()){
            contador[Notas.SI.getPosicion()]=contador[Notas.SI.getPosicion()]+1;
        }

    }

    void hallaNota(int max){

        switch (max){
            case 0: resultado = Notas.DO;
                break;
            case 1: resultado = Notas.DOS;
                break;
            case 2: resultado = Notas.RE;
                break;
            case 3: resultado = Notas.RES;
                break;
            case 4: resultado = Notas.MI;
                break;
            case 5: resultado = Notas.FA;
                break;
            case 6: resultado = Notas.FAS;
                break;
            case 7: resultado = Notas.SOL;
                break;
            case 8: resultado = Notas.SOLS;
                break;
            case 9: resultado = Notas.LA;
                break;
            case 10: resultado = Notas.LAS;
                break;
            case 11: resultado = Notas.SI;
                break;
        }
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
                        int max = 0;
                        for (int i = 0; i<12; i++){
                            if(contador[i] > contador[max])
                                max = i;
                        }
                        hallaNota(max);
                        System.out.println(resultado.getNombre());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                TextView text2 = (TextView) findViewById(R.id.textoFrecuencia);
                                text2.setText("Resultado: " + resultado.getNombre() + octava);

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