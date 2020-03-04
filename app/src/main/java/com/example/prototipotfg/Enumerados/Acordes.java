package com.example.prototipotfg.Enumerados;

import android.util.Pair;

import java.util.ArrayList;

public enum Acordes {
    Acorde_mayor(new int[]{0,4,7}, 3, "Acorde mayor"),
    Acorde_menor(new int[]{0, 3, 7}, 3, "Acorde menor"),
    Acorde_disminuido(new int[]{0,3,6},3, "Acorde disminuido"),
    Acorde_aumentado(new int[]{0,4,8},3, "Acorde aumentado"),
    Acorde_2º_suspendida(new int[]{0,2,7},3,"Acorde de 2º suspendida"),
    Acorde_4º_suspendida(new int[]{0,5,7},3,"Acorde de 4º suspendida"),
    Acorde_mayor_7_menor(new int[]{0,4,7,10}, 4, "Acorde mayor con 7º menor"),
    Acorde_mayor_7_mayor(new int[]{0,4,7,11},4,"Acorde mayor con 7º mayor"),
    Acorde_menor_7_menor(new int[]{0,3,7,10}, 4, "Acorde menor con 7º menor"),
    Acorde_menor_7_mayor(new int[]{0,3,7,11}, 4, "Acorde menor con 7º mayor"),
    Acorde_disminuido_7_menor(new int[]{0,3,6,10}, 4, "Acorde disminuido y 7º menor"),
    Acorde_7_disminuida(new int[]{0,3,6,9}, 4, "Acorde de 7º disminuida");

    private int[] notas;
    private int numNotas;
    private String nombre;

    Acordes (int[] notas, int numNotas, String nombre){
        this.notas = notas;
        this.numNotas=numNotas;
        this.nombre=nombre;
    }

    public static void reproducirAcorde(Acordes acordeCorrecto, Notas notaInicio) {
        /*
        * Funcion que reproduce un acorde dado a partir de una nota
        * */
    }

    public int[] getNotas() {
        return notas;
    }

    public void setNotas(int[] notas) {
        this.notas = notas;
    }

    public int getNumNotas() {
        return numNotas;
    }

    public void setNumNotas(int numNotas) {
        this.numNotas = numNotas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



    public static ArrayList<Acordes> devuelveAcordesConNumnotas(int numNotas){
        ArrayList<Acordes> retorno = new ArrayList<>();
        for (Acordes a : Acordes.values()){
            if (a.getNumNotas()==numNotas)
                retorno.add(a);
        }
        return retorno;
    }

    public static ArrayList<Notas> devuelveNotasAcorde(Acordes a, Notas n, Octavas o){
        ArrayList<Notas> retorno = new ArrayList<>();
        retorno.add(n);
        int tonoActual = n.getTono();
        int tonos[] = a.getNotas();

        for (int i = 1; i < tonos.length; i++){
            tonoActual += tonos[i];
            if (tonoActual > 12){
                tonoActual = tonoActual - 12;
                o = Octavas.values()[o.devuelveIndiceOctava(o)+1];
            }

        }

        return retorno;
    }

    public static ArrayList<Pair<Notas, Octavas>> devuelveNotasAcorde(Acordes acorde, Octavas octavaInicio, Notas notaInicio){
        ArrayList<Pair<Notas,Octavas>> retorno = new ArrayList<>();
        retorno.add(Pair.create(notaInicio, octavaInicio));
        Notas notaActual = notaInicio;
        Octavas octavaActual = octavaInicio;
        for (int i = 1; i < acorde.numNotas; i++){
            if (notaActual.getTono() + acorde.getNotas()[i] > 12){
                octavaActual = Octavas.devuelveSiguienteOctava(octavaActual);
                notaActual = Notas.devuelveNotaPorTono(notaActual.getTono()+acorde.getNotas()[i]-11);
            }
            else{
                notaActual = Notas.devuelveNotaPorTono(notaActual.getTono()+acorde.getNotas()[i]);
            }
            retorno.add(Pair.create(notaActual, octavaActual));
        }
        return retorno;
    }
}
