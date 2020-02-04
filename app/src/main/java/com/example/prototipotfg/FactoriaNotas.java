package com.example.prototipotfg;

import android.content.res.AssetManager;
import android.content.res.AssetFileDescriptor;
import android.util.Pair;


import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

class FactoriaNotas {
    private static final FactoriaNotas ourInstance = new FactoriaNotas();
    private Random random = new Random();


    static FactoriaNotas getInstance() {
        return ourInstance;
    }

    private FactoriaNotas() {}

    public HashMap<String,String> getNumNotasAleatorias(int numeroNotas, Instrumentos instrumento, Octavas[] octavas) throws IOException {

        /*
        * Funcion que devuelve num notas aleatorias en un array
        * La primera posicion del array se utilizara como la nota a adivinar
        * */

        String rutaInstrumento = instrumento.getPath();

        //HashMap con clave nombre de nota y su octava y valor el path a su fichero
        HashMap<String,String> rutasFicherosAudio = new HashMap<String,String>();
        Octavas octava = devuelveOctavaAleatoria(octavas);
        Notas nota = devuelveNotaAleatoria(Notas.values());
        rutasFicherosAudio.put(nota.getNombre()+octava.getOctava(), rutaInstrumento+octava.getPath()+nota.getPath());
        for (int i = 1 ; i < numeroNotas; i++){
            while (rutasFicherosAudio.containsKey(nota.getNombre()+octava.getOctava())){
                octava = devuelveOctavaAleatoria(octavas);
                nota = devuelveNotaAleatoria(Notas.values());
            }
            rutasFicherosAudio.put(nota.getNombre()+octava.getOctava(), rutaInstrumento+octava.getPath()+nota.getPath());
        }
        return rutasFicherosAudio;
    }

    private Notas devuelveNotaAleatoria(Notas[] notas) {
        return notas[random.nextInt(notas.length)];
    }

    private Octavas devuelveOctavaAleatoria(Octavas[] octavas) {
        return octavas[random.nextInt(octavas.length)];
    }
}
