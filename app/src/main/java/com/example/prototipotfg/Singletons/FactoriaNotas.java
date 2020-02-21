package com.example.prototipotfg.Singletons;


import com.example.prototipotfg.Enumerados.Instrumentos;
import com.example.prototipotfg.Enumerados.Notas;
import com.example.prototipotfg.Enumerados.Octavas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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
        Notas nota = devuelveNotaAleatoria(Notas.values());

        String ruta = rutaInstrumento+octava.getPath()+nota.getPath();
        rutasFicherosAudio.put(nota.getNombre()+octava.getOctava(), ruta);
        System.out.println(ruta);
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

    private Octavas devuelveOctavaAleatoria(ArrayList<Octavas> octavas) {
        return octavas.get(random.nextInt(octavas.size()));
    }


}