package com.example.prototipotfg.Enumerados;

public enum RangosVocales {

    Mujer("Mujer", 3, 6, 10, 1),
    Hombre("Hombre", 2, 4, 8, 10),
    Niño("Niño", 3, 6, 3, 6),
    MujerM("Mujer", 3, 6, 8, 1),
    HombreM("Hombre", 2, 5, 8, 6),
    NiñoM("Niño", 3, 6, 3, 10),
    MujerD("Mujer", 3, 6, 3, 1),
    HombreD("Hombre", 2, 4, 5, 10),
    NiñoD("Niño", 3, 6, 3, 1);


    private String nombre;
    private int octavaIni;
    private int octavaFin;
    private int tonoIni;
    private int tonoFin;

    RangosVocales(String nombre, int octavaIni, int octavaFin, int tonoIni, int tonoFin) {
        this.nombre = nombre;
        this.octavaFin = octavaFin;
        this.octavaIni = octavaIni;
        this.tonoFin = tonoFin;
        this.tonoIni = tonoIni;
    }

    public static RangosVocales devuelveRVPorNombre(String nombre) {

        for (RangosVocales n : RangosVocales.values()
        ) {
            if (n.getNombre().equals(nombre)) return n;
        }
        return null;
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

    public int getOctavaFin() {
        return octavaFin;
    }

    public int getTonoIni() {
        return tonoIni;
    }

    public int getTonoFin() {
        return tonoFin;
    }


}
