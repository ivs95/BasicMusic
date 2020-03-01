package com.example.prototipotfg.Singletons;


import com.example.prototipotfg.Enumerados.Instrumentos;
import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Enumerados.Notas;
import com.example.prototipotfg.Enumerados.Octavas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class FactoriaNotas {

    private static final FactoriaNotas ourInstance = new FactoriaNotas();
    private Random random = new Random();
    private Instrumentos instrumento;
    private String rutaReferencia;


    public Instrumentos getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(Instrumentos instrumento) {
        this.instrumento = instrumento;
    }

    public static FactoriaNotas getInstance() {
        return ourInstance;
    }

    public String getReferencia(){
        return rutaReferencia;
    }

    private void setReferencia(String ruta){
        this.rutaReferencia=ruta;
    }

    private FactoriaNotas() {}

    public HashMap<String,String> getNumNotasAleatorias(int numeroNotas, Instrumentos instrumento, ArrayList<Octavas> octavas) throws IOException {
        /*
        * Funcion que devuelve num notas aleatorias en un array
        * La primera posicion del array se utilizara como la nota a adivinar
        * */
        String rutaInstrumento = instrumento.getPath();
        setReferencia(instrumento.getPath()+"cuatro/A.wav");
        setInstrumento(instrumento);

        //HashMap con clave nombre de nota y su octava y valor el path a su fichero
        HashMap<String,String> rutasFicherosAudio = new HashMap<String,String>();
        Octavas octava = devuelveOctavaAleatoria(octavas);
        Notas nota = devuelveNotaAleatoria(Notas.values() ,0, 0, false);

        int tonoNotaAnt = getTonoNota(nota.getNombre());

        String ruta = rutaInstrumento+octava.getPath()+nota.getPath();
        rutasFicherosAudio.put(nota.getNombre()+octava.getOctava(), ruta);
        System.out.println(ruta);
        for (int i = 1 ; i < numeroNotas; i++){
            while (rutasFicherosAudio.containsKey(nota.getNombre()+octava.getOctava())){
                octava = devuelveOctavaAleatoria(octavas);
                if(nota == Notas.SI) nota = devuelveNotaAleatoria(Notas.values(), i, tonoNotaAnt, true);
                else nota = devuelveNotaAleatoria(Notas.values(), i, tonoNotaAnt, false);
                tonoNotaAnt = getTonoNota(nota.getNombre());


            }
            rutasFicherosAudio.put(nota.getNombre()+octava.getOctava(), rutaInstrumento+octava.getPath()+nota.getPath());
        }
        return rutasFicherosAudio;
    }

    public HashMap<String,String> getNumNotasAleatorias(int numeroNotas, Instrumentos instrumento, Octavas octava) throws IOException {
        /*
         * Funcion que devuelve num notas aleatorias en un array
         * La primera posicion del array se utilizara como la nota a adivinar
         */
        String rutaInstrumento = instrumento.getPath();
        setReferencia(instrumento.getPath()+"cuatro/A.wav");
        setInstrumento(instrumento);

        //HashMap con clave nombre de nota y su octava y valor el path a su fichero
        HashMap<String,String> rutasFicherosAudio = new HashMap<String,String>();
        Notas nota = devuelveNotaAleatoria(Notas.values(), 0, 0, false);

        int tonoNotaAnt = getTonoNota(nota.getNombre());

        String ruta = rutaInstrumento+octava.getPath()+nota.getPath();
        rutasFicherosAudio.put(nota.getNombre()+octava.getOctava(), ruta);
        System.out.println(ruta);
        for (int i = 1 ; i < numeroNotas; i++){
            while (rutasFicherosAudio.containsKey(nota.getNombre()+octava.getOctava())){
                nota = devuelveNotaAleatoria(Notas.values(), i, tonoNotaAnt, false);
            }
            rutasFicherosAudio.put(nota.getNombre()+octava.getOctava(), rutaInstrumento+octava.getPath()+nota.getPath());
        }
        return rutasFicherosAudio;
    }
    //0, 1, , 2 --> 8, 9, 10

    private Notas devuelveNotaAleatoria(Notas[] notas, int i, int tonoNotaAnt, boolean ultNota) {
        if(Controlador.getInstance().getModo_juego() == ModoJuego.Adivinar_Intervalo && i == 1 && !ultNota){
            return notas[random.nextInt((min((notas.length-tonoNotaAnt), (Controlador.getInstance().getRango()))))+ tonoNotaAnt];
        }

         else return notas[random.nextInt(notas.length)];
    }

    private Octavas devuelveOctavaAleatoria(ArrayList<Octavas> octavas) {
        return octavas.get(random.nextInt(octavas.size()));
    }


    private int getTonoNota(String name){
        boolean OK = false;
        int i = 0;
        Notas[] lista_notas = new Notas[11];
        lista_notas = Notas.values();
        while(i < 11 && !OK){
            if(lista_notas[i].getNombre().equals(name)) OK = true;
            i++;

        }
        return lista_notas[i-1].getTono();
    }

    public Notas getNotaInicioIntervalo(Instrumentos piano, Octavas octavaInicio) {
        return Notas.values()[new Random().nextInt(Notas.values().length)];
    }
}
