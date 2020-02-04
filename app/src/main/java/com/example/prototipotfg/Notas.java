package com.example.prototipotfg;

public enum Notas {
    DO1("Do", 31.7, 33.7, "C.wav"),
    DOS1("Do#",33.71, 35.65, "C#.wav"),
    RE1("Re", 35.71, 37.71, "D.wav"),
    RES1("Re#", 37.89, 39.89, "D#.wav"),
    MI1("Mi", 40.2, 42.2, "E.wav"),
    FA1("Fa", 42.65, 44.65, "F.wav" ),
    FAS1("Fa#", 45.25, 47.25, "F#.wav"),
    SOL1("Sol", 48, 50, "G.wav" ),
    SOLS1("Sol#", 50.91, 52.91, "G#.wav" ),
    LA1("La", 54, 56, "A.wav"),
    LAS1("La#", 57.27, 59.27, "A#.wav"),
    SI1("Si", 60.74, 62.74, "B.wav");


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
