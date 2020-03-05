package com.example.prototipotfg.Enumerados;

import java.util.ArrayList;
import java.util.Arrays;

public enum Octavas {

    Primera("Primera","uno/", 1),
    Segunda("Segunda","dos/", 2),
    Tercera("Tercera","tres/", 3),
    Cuarta("Cuarta","cuatro/", 4),
    Quinta("Quinta","cinco/", 5),
    Sexta("Sexta","seis/", 6),
    Septima("Septima","siete/", 7);


    private String nombre;
    private String path;
    private int octava;

    private Octavas(String nombre, String path,int octava){
        this.nombre=nombre;
        this.path=path;
        this.octava=octava;
    }

    public static Octavas devuelveSiguienteOctava(Octavas octava) {
        int indice = Arrays.asList(Octavas.values()).indexOf(octava)+1;
        return Octavas.values()[indice];
    }

    public int devuelveIndiceOctava(Octavas o){
        return Arrays.asList(Octavas.values()).indexOf(o);
    }

    public static ArrayList<Octavas> devuelveOctavas(ArrayList<String> octavas) {
        ArrayList<Octavas> retorno = new ArrayList<Octavas>();
        for (String nombre: octavas) {
            retorno.add(Octavas.devuelveOctavaPorNombre(nombre));
        }
        return retorno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getOctava() {
        return octava;
    }

    public void setOctava(int octava) {
        this.octava = octava;
    }

    public static Octavas devuelveOctavaPorNombre(String nombre){
        for (Octavas o: Octavas.values()
        ) {
            if (o.getNombre().equals(nombre)) return o;
        }
        return null;
    }

    public static Octavas devuelveOctavaPorNumero(int numero){
        for (Octavas o: Octavas.values()
        ) {
            if (o.getOctava()== numero) return o;
        }
        return null;
    }

}
