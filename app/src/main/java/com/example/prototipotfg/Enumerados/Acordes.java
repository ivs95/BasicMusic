package com.example.prototipotfg.Enumerados;

import android.util.Pair;

import java.util.ArrayList;

public enum Acordes {
    Acorde_mayor(new int[]{0, 4, 7}, 3, "Acorde mayor"),
    Acorde_menor(new int[]{0, 3, 7}, 3, "Acorde menor"),
    Acorde_disminuido(new int[]{0, 3, 6}, 3, "Acorde disminuido"),
    Acorde_aumentado(new int[]{0, 4, 8}, 3, "Acorde aumentado"),
    Acorde_2º_suspendida(new int[]{0, 2, 7}, 3, "Acorde de 2º suspendida"),
    Acorde_4º_suspendida(new int[]{0, 5, 7}, 3, "Acorde de 4º suspendida"),
    Acorde_mayor_7_menor(new int[]{0, 4, 7, 10}, 4, "Acorde mayor con 7º menor"),
    Acorde_mayor_7_mayor(new int[]{0, 4, 7, 11}, 4, "Acorde mayor con 7º mayor"),
    Acorde_menor_7_menor(new int[]{0, 3, 7, 10}, 4, "Acorde menor con 7º menor"),
    Acorde_menor_7_mayor(new int[]{0, 3, 7, 11}, 4, "Acorde menor con 7º mayor"),
    Acorde_disminuido_7_menor(new int[]{0, 3, 6, 10}, 4, "Acorde disminuido y 7º menor"),
    Acorde_7_disminuida(new int[]{0, 3, 6, 9}, 4, "Acorde de 7º disminuida");

    private int[] notas;
    private int numNotas;
    private String nombre;

    Acordes(int[] notas, int numNotas, String nombre) {
        this.notas = notas;
        this.numNotas = numNotas;
        this.nombre = nombre;
    }


    public int[] getNotas() {
        return notas;
    }

    public void setNotas(int[] notas) {
        this.notas = notas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public static ArrayList<Pair<Notas, Octavas>> devuelveNotasAcorde(Acordes acorde, Octavas octavaInicio, Notas notaInicio) {
        ArrayList<Pair<Notas, Octavas>> retorno = new ArrayList<>();
        retorno.add(Pair.create(notaInicio, octavaInicio));
        Notas notaActual = notaInicio;
        Octavas octavaActual = octavaInicio;
        for (int i = 1; i < acorde.numNotas; i++) {
            if (notaActual.getTono() + (acorde.getNotas()[i] - acorde.getNotas()[i - 1]) > 12) {
                octavaActual = Octavas.devuelveSiguienteOctava(octavaActual);
                notaActual = Notas.devuelveNotaPorTono(notaInicio.getTono() + acorde.getNotas()[i] - 12);
            } else {
                notaActual = Notas.devuelveNotaPorTono(notaActual.getTono() + acorde.getNotas()[i] - acorde.getNotas()[i - 1]);
            }
            retorno.add(Pair.create(notaActual, octavaActual));
        }
        return retorno;
    }
}