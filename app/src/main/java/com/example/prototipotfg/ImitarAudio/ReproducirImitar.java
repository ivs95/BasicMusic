package com.example.prototipotfg.ImitarAudio;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.content.res.ColorStateList;
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

import com.example.prototipotfg.AudioDispatcherFactory1;
import com.example.prototipotfg.Enumerados.Notas;
import com.example.prototipotfg.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;


public class ReproducirImitar extends Activity {


    private AudioDispatcher dispatcher;
    private ArrayList<NotasImitar> lista = new ArrayList<NotasImitar>();
    private ArrayList<Float> porcentajes = new ArrayList<>();
    private NotasImitar resNota;
    private Float resPorcentaje;
    private ArrayList<String> nombres;
    private ArrayList<String> rutas;
    private int nivel;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nivel_reproducir_imitar);
        nombres = getIntent().getExtras().getStringArrayList("nombres");
        rutas = getIntent().getExtras().getStringArrayList("rutas");

        nivel = getIntent().getExtras().getInt("nivel");
        TextView titulo = (TextView)findViewById(R.id.tituloImitar);
        titulo.setText(titulo.getText() + String.valueOf(nivel));

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
        if((resNota.getNota().getNombre() + (resNota.getOctava())).equals(nombres.get(0))){
            view = this.getWindow().getDecorView();
            view.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_green_500)));
        }
        else{
            view = this.getWindow().getDecorView();
            view.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_red_500)));
        }
    }





    //Va añadiendo notas junto con su octava a "lista" para asi hallar la nota que mas se repite en la secuencia que el dispatcher esta ON
    void hallaMax(float hz){
        if (hz != -1) {
            Integer octava = 5;
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
            NotasImitar nota = new NotasImitar(n, octava);
            Boolean contiene = false;
            Comparator<NotasImitar> comparador = new Comparator<NotasImitar>() {
                @Override
                public int compare(NotasImitar o1, NotasImitar o2) {
                    int resul = 0;
                    if (o1.getNota() == o2.getNota() && o1.getOctava() == o2.getOctava()) {
                        resul = 1;
                    }
                    return resul;
                }
            };
            for (int i = 0; i < lista.size(); i++) {
                if (comparador.compare(nota, lista.get(i)) == 1) {
                    nota=lista.get(i);
                    lista.set(i, new NotasImitar(n, octava, nota.getContador() + 1));
                    porcentajes.set(i, porcentajes.get(i) + hz);
                    contiene = true;
                }
            }
            if (!contiene) {
                lista.add(new NotasImitar(n, octava, 1));
                porcentajes.add(hz);
            }
            return true;
        }
        else
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

                        if(lista.size()>0) {
                            resNota = lista.get(0);
                            resPorcentaje = porcentajes.get(0)/resNota.getContador();
                            resPorcentaje = resPorcentaje*100/(float)resNota.getNota().getFrecuencia();
                            if(resPorcentaje > 100) {
                                resPorcentaje = 100 - (resPorcentaje - 100);
                            }
                            for (int i = 1; i < lista.size(); i++) {
                                if (lista.get(i).getContador() > resNota.getContador()) {
                                    resNota = lista.get(i);
                                    resPorcentaje = porcentajes.get(i)/resNota.getContador();
                                    resPorcentaje = resPorcentaje*100/(float)resNota.getNota().getFrecuencia();
                                    if(resPorcentaje > 100) {
                                        resPorcentaje = 100 - (resPorcentaje - 100);
                                    }
                                }
                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    TextView text2 = findViewById(R.id.textoFrecuencia);
                                    text2.setText("Resultado: " + resNota.getNota().getNombre() + (resNota.getOctava()) + "   " + resPorcentaje+"%");

                                    Button repetirNivel = (Button) findViewById(R.id.botonRepite);
                                    repetirNivel.setVisibility(View.VISIBLE);
                                    repetirNivel.setEnabled(true);

                                    Button comparar = (Button) findViewById(R.id.button4);
                                    comparar.setEnabled(true);
                                    comparar.setVisibility(View.VISIBLE);

                                }

                            });
                        }
                        else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    TextView text2 = findViewById(R.id.textoFrecuencia);
                                    text2.setText("No se ha detectado ningún audio.   Por favor repita el nivel");

                                    Button repetirNivel = (Button) findViewById(R.id.botonRepite);
                                    repetirNivel.setVisibility(View.VISIBLE);
                                    repetirNivel.setEnabled(true);


                                }

                            });
                        }


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