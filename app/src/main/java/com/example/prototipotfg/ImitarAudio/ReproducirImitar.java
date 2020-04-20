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
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.example.prototipotfg.AudioDispatcherFactory1;
import com.example.prototipotfg.BBDD.NivelImitar;
import com.example.prototipotfg.Enumerados.Instrumentos;
import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Enumerados.Notas;
import com.example.prototipotfg.Enumerados.RangosVocales;
import com.example.prototipotfg.R;
import com.example.prototipotfg.Singletons.FactoriaNotas;
import com.example.prototipotfg.Singletons.GestorBBDD;
import com.example.prototipotfg.Singletons.Reproductor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;


public class ReproducirImitar extends Activity {
    private AudioDispatcherFactory1 factory = new AudioDispatcherFactory1();
    private AudioDispatcher dispatcher = factory.fromDefaultMicrophone(22050,1024,0);
    private ArrayList<NotasImitar> lista = new ArrayList<>();
    private ArrayList<Float> porcentajes = new ArrayList<>();
    private Float resPorcentaje;
    private NotasImitar resNota;
    private ArrayList<String> nombres;
    private ArrayList<String> rutas;
    private int nivel;
    private int reproducciones=0;
    private int reproduccionesTotales;
    private int intentos=0;
    private int intentosTotales;
    private boolean octavas = true;
    private int octava;
    private AcousticEchoCanceler echo;
    private NoiseSuppressor noise;
    private AutomaticGainControl gain;
    private AudioProcessor p;
    private Thread dispatch_Thread = new Thread(dispatcher,"Audio Dispatcher");



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nivel_reproducir_imitar);
        GestorBBDD.getInstance().modoRealizado(ModoJuego.Imitar_Audio);

        nivel = getIntent().getExtras().getInt("nivel");
        TextView titulo = (TextView)findViewById(R.id.tituloImitar);
        titulo.setText(titulo.getText() + String.valueOf(nivel));

        inicializaPorDificultad();

        TextView restantes = findViewById(R.id.textViewRestantes);
        if(this.nivel == 1) {
            restantes.setText("Reproducciones restantes: ∞\n Intentos restantes: "+(intentosTotales-intentos));
        }
        else{
            restantes.setText("Reproducciones restantes: "+ (reproduccionesTotales-reproducciones)+"\n Intentos restantes: "+(intentosTotales-intentos));
        }

        int id = factory.getAudioRecord().getAudioSessionId();
        if(AcousticEchoCanceler.isAvailable()) {
            echo = AcousticEchoCanceler.create(id);
            if(echo != null) {
                echo.setEnabled(true);
                Log.d("Echo", "Off");
            }
        }
        if(NoiseSuppressor.isAvailable()) {
            noise = NoiseSuppressor.create(id);
            if(noise != null) {
                noise.setEnabled(true);
                Log.d("Noise", "Off");
            }
        }
        if(AutomaticGainControl.isAvailable()) {
            gain = AutomaticGainControl.create(id);
            if(gain != null) {
                gain.setEnabled(false);
                Log.d("Gain", "Off");
            }
        }
    }

    public void rangoPorNivel(){
        RangosVocales RangoVocal = RangosVocales.devuelveRVPorNombre(getIntent().getExtras().getString("rangoVocal"));
        if(nivel>4 && nivel <6){
            if(RangoVocal.getNombre().equalsIgnoreCase("Hombre"))
                RangoVocal = RangosVocales.HombreM;
            else if(RangoVocal.getNombre().equalsIgnoreCase("Mujer"))
                RangoVocal = RangosVocales.MujerM;
            else if(RangoVocal.getNombre().equalsIgnoreCase("Niño"))
                RangoVocal = RangosVocales.NiñoM;
        }
        else if(nivel > 6){
            if(RangoVocal.getNombre().equalsIgnoreCase("Hombre"))
                RangoVocal = RangosVocales.HombreD;
            else if(RangoVocal.getNombre().equalsIgnoreCase("Mujer"))
                RangoVocal = RangosVocales.MujerD;
            else if(RangoVocal.getNombre().equalsIgnoreCase("Niño"))
                RangoVocal = RangosVocales.NiñoD;
        }
        HashMap<String, String> notas = FactoriaNotas.getInstance().getNotasRV(RangoVocal,1, Instrumentos.Piano);
        nombres = new ArrayList<>(notas.keySet());
        rutas = new ArrayList<>(notas.values());
    }

    public void inicializaPorDificultad(){
        if(this.nivel==1){
            rangoPorNivel();
            intentosTotales = 3;
            reproduccionesTotales=10000;
        }
        else if(this.nivel==2){
            rangoPorNivel();
            intentosTotales = 2;
            reproduccionesTotales=3;
        }
        else if(this.nivel==3){
            rangoPorNivel();
            intentosTotales = 2;
            reproduccionesTotales=3;
            octavas=false;
        }
        else if(this.nivel==4){
            rangoPorNivel();
            intentosTotales = 2;
            reproduccionesTotales=3;
            octavas=false;
        }
        else if(this.nivel==5){
            rangoPorNivel();
            intentosTotales = 2;
            reproduccionesTotales=2;
            octavas=false;
        }
        else if(this.nivel==6){
            rangoPorNivel();
            intentosTotales = 2;
            reproduccionesTotales=2;
            octavas=false;
        }
        else if(this.nivel==7){
            rangoPorNivel();
            intentosTotales = 1;
            reproduccionesTotales=2;
            octavas=false;
        }
        else if(this.nivel==8){
            rangoPorNivel();
            intentosTotales = 1;
            reproduccionesTotales=1;
            octavas=false;
        }
    }

    public void reproducir(View view) throws IOException {
        AssetFileDescriptor afd = getAssets().openFd(rutas.get(0));
        Reproductor.getInstance().reproducirNota(afd);
        reproducciones++;
        if(reproducciones == reproduccionesTotales) {
            findViewById(R.id.button2).setEnabled(false);
            findViewById(R.id.button2).setAlpha(.5f);
        }
        TextView restantes = findViewById(R.id.textViewRestantes);
        if(this.nivel == 1) {
            restantes.setText("Reproducciones restantes: ∞\n Intentos restantes: "+(intentosTotales-intentos));
        }
        else{
            restantes.setText("Reproducciones restantes: "+ (reproduccionesTotales-reproducciones)+"\n Intentos restantes: "+(intentosTotales-intentos));
        }
    }

    public void comparar(View view){
        NivelImitar nivel;
        TextView text2 = findViewById(R.id.textoFrecuencia);
        boolean correct = false;
        intentos++;

        if(octavas ==false) {
            if ((resNota.getNota().getNombre() + (resNota.getOctava())).equals(nombres.get(0))) {
                view = this.getWindow().getDecorView();
                text2.setTextColor(getResources().getColor(R.color.md_green_500));
                //PONER RANGO VOCAL
                nivel = new NivelImitar(GestorBBDD.getInstance().getUsuarioLoggeado().getCorreo(), true, resPorcentaje, 1, getIntent().getExtras().getString("rangoVocal"), this.nivel);
                correct = true;
            } else {
                view = this.getWindow().getDecorView();
                text2.setTextColor(getResources().getColor(R.color.md_red_500));

                nivel = new NivelImitar(GestorBBDD.getInstance().getUsuarioLoggeado().getCorreo(), false, resPorcentaje, 1, getIntent().getExtras().getString("rangoVocal"), this.nivel);
            }
        }
        else{
            if ((resNota.getNota().getNombre()).equals(nombres.get(0).substring(0,nombres.get(0).length()-1))) {
                view = this.getWindow().getDecorView();
                text2.setTextColor(getResources().getColor(R.color.md_green_500));
                //PONER RANGO VOCAL
                nivel = new NivelImitar(GestorBBDD.getInstance().getUsuarioLoggeado().getCorreo(), true, resPorcentaje, 1, getIntent().getExtras().getString("rangoVocal"), this.nivel);
                correct = true;
            } else {
                view = this.getWindow().getDecorView();
                text2.setTextColor(getResources().getColor(R.color.md_red_500));

                nivel = new NivelImitar(GestorBBDD.getInstance().getUsuarioLoggeado().getCorreo(), false, resPorcentaje, 1, getIntent().getExtras().getString("rangoVocal"), this.nivel);
            }
        }
        if(correct || intentos >= intentosTotales) {
            GestorBBDD.getInstance().insertaNivelImitar(nivel);
            findViewById(R.id.button2).setEnabled(false);        findViewById(R.id.button2).setAlpha(.5f);
            findViewById(R.id.botonGrabar).setEnabled(false);        findViewById(R.id.botonGrabar).setAlpha(.5f);
            findViewById(R.id.button4).setEnabled(false);        findViewById(R.id.button4).setAlpha(.5f);
        }

        TextView restantes = findViewById(R.id.textViewRestantes);
        if(this.nivel == 1) {
            restantes.setText("Reproducciones restantes: ∞\n Intentos restantes: "+(intentosTotales-intentos));
        }
        else{
            restantes.setText("Reproducciones restantes: "+ (reproduccionesTotales-reproducciones)+"\n Intentos restantes: "+(intentosTotales-intentos));
        }
    }

    public void contador(View view){
        findViewById(R.id.button2).setEnabled(false);        findViewById(R.id.button2).setAlpha(.5f);
        TextView text2 = findViewById(R.id.textoFrecuencia); text2.setTextColor(getResources().getColor(R.color.md_blue_900));
        inicializaArrays();
        view.setVisibility(View.GONE);
        class MiContador extends CountDownTimer {

            public MiContador(long millisInFuture, long countDownInterval) {
                super(millisInFuture, countDownInterval);
            }

            @Override
            public void onTick(long millisUntilFinished) {
                //texto a mostrar en cuenta regresiva en un textview
                TextView countdownText = findViewById(R.id.textoFrecuencia);
                countdownText.setText(String.valueOf((millisUntilFinished/1000)+1));
            }

            @Override
            public void onFinish() {
                TextView countdownText = findViewById(R.id.textoFrecuencia);
                countdownText.setText("Grabando");
                inicializaPitch();
            }

        }
        MiContador timer = new MiContador(2999,100);
        timer.start();




    }

    private void inicializaArrays() {
        lista = new ArrayList<>();
        porcentajes = new ArrayList<>();
    }

    private void inicializaPitch() {
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
        p = new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, 22050, 1024, pdh);
        dispatcher.addAudioProcessor(p);
        this.dispatch_Thread = new Thread(dispatcher,"Audio Dispatcher");
        final Thread dispatch_Thread = this.dispatch_Thread;
        dispatch_Thread.start();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                dispatcher.removeAudioProcessor(p);
                dispatch_Thread.interrupt();
                poneNota();
                ((Button)findViewById(R.id.botonGrabar)).setVisibility(View.VISIBLE);
                if(reproducciones < reproduccionesTotales) {
                    findViewById(R.id.button2).setEnabled(true);
                    findViewById(R.id.button2).setAlpha(1f);
                }

            }
        }, 5000);
    }

    private void hallaMax(float hz){
        if (hz != -1) {
            octava = 5;
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


    @Override
    public void onDestroy(){
        super.onDestroy();
        dispatch_Thread.interrupt();
        dispatcher.stop();
        if(echo != null)
            echo.release();
        if(noise != null)
            noise.release();
        if(gain != null)
            gain.release();
    }

    public void poneNota(){
        if(lista.size()>0) {
            resNota = lista.get(0);
            resPorcentaje = porcentajes.get(0)/resNota.getContador();
            int octavaOrigen = Integer.parseInt(nombres.get(0).substring(nombres.get(0).length()-1,nombres.get(0).length()));
            if(octavaOrigen>=5) {
                resPorcentaje = resPorcentaje * 100 / (float) (Notas.devuelveNotaPorNombre(nombres.get(0).substring(0, nombres.get(0).length() - 1)).getFrecuencia()/(2*(octavaOrigen-5)));
            }
            else{
                resPorcentaje = resPorcentaje * 100 / (float) (Notas.devuelveNotaPorNombre(nombres.get(0).substring(0, nombres.get(0).length() - 1)).getFrecuencia()*(2*(5-octavaOrigen)));
            }
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
                    text2.setText("No se ha detectado ningún audio. \n Por favor repita el nivel");


                }

            });
        }
    }
}
