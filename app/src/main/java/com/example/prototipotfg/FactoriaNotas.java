package com.example.prototipotfg;

import android.content.res.AssetManager;
import android.content.res.AssetFileDescriptor;
import android.util.ArrayMap;
import android.util.Pair;


import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

class FactoriaNotas {
    private static final FactoriaNotas ourInstance = new FactoriaNotas();
    private Random random = new Random();
    private  ArrayList<String> intervalos = new ArrayList<String>();


    static FactoriaNotas getInstance() {
        return ourInstance;
    }

    private FactoriaNotas() {}

    public HashMap<String,String> getNumNotasAleatorias(int numeroNotas, Instrumentos instrumento, ArrayList<Octavas> octavas) throws IOException {
        /*
        * Funcion que devuelve num notas aleatorias en un array
        * La primera posicion del array se utilizara como la nota a adivinar
        * */
        String rutaInstrumento = instrumento.getPath();

        //HashMap con clave nombre de nota y su octava y valor el path a su fichero
        HashMap<String,String> rutasFicherosAudio = new HashMap<String,String>();
        Octavas octava = devuelveOctavaAleatoria(octavas);
        Notas nota = devuelveNotaAleatoria(Notas.values());


        Notas nota_anterior = nota;
        String ruta = rutaInstrumento+octava.getPath()+nota.getPath();
        rutasFicherosAudio.put(nota.getNombre()+octava.getOctava(), ruta);
        System.out.println(ruta);
        for (int i = 1 ; i < numeroNotas; i++){
            while (rutasFicherosAudio.containsKey(nota.getNombre()+octava.getOctava())){
                octava = devuelveOctavaAleatoria(octavas);
                nota = devuelveNotaAleatoria(Notas.values());


            }
            String intervalo = this.getNombreConDif(Math.abs(nota.getTono() - nota_anterior.getTono()));
            intervalos.add(intervalo);
            nota_anterior = nota;
            rutasFicherosAudio.put(nota.getNombre()+octava.getOctava(), rutaInstrumento+octava.getPath()+nota.getPath());
        }
        intervalos.add(devuelveIntervaloAleatorio(Intervalos.values()).getNombre());
        return rutasFicherosAudio;
    }

    private Notas devuelveNotaAleatoria(Notas[] notas) {
         return notas[random.nextInt(notas.length)];
    }

    private Octavas devuelveOctavaAleatoria(ArrayList<Octavas> octavas) {
        return octavas.get(random.nextInt(octavas.size()));
    }

    private Intervalos devuelveIntervaloAleatorio(Intervalos[] intervalos){
        return intervalos[random.nextInt(intervalos.length)];
    }

    public ArrayList getIntevalos(){
        return this.intervalos;

    }

    public String getNombreConDif(double dif){
        boolean OK = false;
        int i = 0;
        Intervalos[] intervalos_lista = new Intervalos[12];
        intervalos_lista = Intervalos.values();
        while(i < 12 && !OK){
            if(intervalos_lista[i].getDiferencia() == dif) OK = true;
            i++;

        }
        return intervalos_lista[i-1].getNombre();
    }

}
