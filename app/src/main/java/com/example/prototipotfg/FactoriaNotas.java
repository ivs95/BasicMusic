package com.example.prototipotfg;

import java.lang.reflect.Field;

class FactoriaNotas {
    private static final FactoriaNotas ourInstance = new FactoriaNotas();

    static FactoriaNotas getInstance() {
        return ourInstance;
    }

    private FactoriaNotas() {}

    public Notas[] getNumNotasAleatorias(int num){
        /*
        * Funcion que devuelve num notas aleatorias en un array
        * La primera posicion del array se utilizara como la nota a adivinar
        * */


        Field[] notas = R.raw.class.getFields();

        return null;
    }

    public Notas getNotaAleatoria(){
        /*
        * Funcion que devuelve una nota aleatoria
        * */



        return null;
    }
}
