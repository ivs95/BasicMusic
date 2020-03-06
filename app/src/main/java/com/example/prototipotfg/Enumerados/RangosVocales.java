package com.example.prototipotfg.Enumerados;

import java.util.ArrayList;
import java.util.Arrays;

public enum RangosVocales {

    Soprano("Soprano",4, 6, 1, 1 ),
    Mezzo("Mezzosoprano",3, 5, 10, 10),
    Contralto("Contralto",3, 5, 8,6),
    Tenor("Tenor",3, 4, 3,10),
    Baritono("Baritono",2, 4,8,5),
    Bajo("Bajo",2, 4, 5,5);


    private String nombre;
    private int octavaIni;
    private int octavaFin;
    private int tonoIni;
    private int tonoFin;

    private RangosVocales(String nombre, int octavaIni ,int octavaFin, int tonoIni, int tonoFin){
        this.nombre=nombre;
        this.octavaFin = octavaFin;
        this.octavaIni = octavaIni;
        this.tonoFin = tonoFin;
        this.tonoIni = tonoIni;
    }

    public static RangosVocales devuelveRVPorNombre(String nombre) {

        for (RangosVocales n: RangosVocales.values()
        ) {
            if (n.getNombre().equals(nombre)) return n;
        }
        return null;
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

    public int getOctavaIni() {
        return octavaIni;
    }

    public void setOctavaIni(int octavaIni) {
        this.octavaIni = octavaIni;
    }

    public int getOctavaFin() {
        return octavaFin;
    }

    public void setOctavaFin(int octavaFin) {
        this.octavaFin = octavaFin;
    }

    public int getTonoIni() {
        return tonoIni;
    }

    public void setTonoIni(int tonoIni) {
        this.tonoIni = tonoIni;
    }

    public int getTonoFin() {
        return tonoFin;
    }

    public void setTonoFin(int tonoFin) {
        this.tonoFin = tonoFin;
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
