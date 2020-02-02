package com.example.prototipotfg;

public enum Notas {
    DO("Do", 31.7, 33.7, "do.wav"),
    DOS("Do#",33.71, 35.65, "dos.wav"),
    RE("Re", 35.71, 37.71, "re.wav"),
    RES("Re#", 37.89, 39.89, "res.wav"),
    MI("Mi", 40.2, 42.2, "mi.wav"),
    FA("Fa", 42.65, 44.65, "fa.wav"),
    FAS("Fa#", 45.25, 47.25, "fas.wav"),
    SOL("Sol", 48, 50, "sol.wav"),
    SOLS("Sol#", 50.91, 52.91, "sols.wav"),
    LA("La", 54, 56, "la.wav"),
    LAS("La#", 57.27, 59.27, "las.wav"),
    SI("Si", 60.74, 62.74, "si.wav");


    private String nombre;
    private double minimaFrecuencia;
    private double maximaFrecuencia;
    private String path;

    private Notas(String nombre, double minimaFrecuencia, double maximaFrecuencia, String path){
        this.nombre=nombre;
        this.minimaFrecuencia=minimaFrecuencia;
        this.maximaFrecuencia=maximaFrecuencia;
        this.path=path;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getMinimaFrecuencia() {
        return minimaFrecuencia;
    }

    public void setMinimaFrecuencia(double minimaFrecuencia) {
        this.minimaFrecuencia = minimaFrecuencia;
    }

    public double getMaximaFrecuencia() {
        return maximaFrecuencia;
    }

    public void setMaximaFrecuencia(double maximaFrecuencia) {
        this.maximaFrecuencia = maximaFrecuencia;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean esNota(Notas n, double frecuencia){
        return (n.minimaFrecuencia <= frecuencia && n.maximaFrecuencia >= frecuencia);
    }
}
