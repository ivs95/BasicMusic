package com.example.prototipotfg;

public enum Notas {
    DO("Do", 254.281, 269.404, "C.wav", 1),
    DOS("Do#",269.405, 285.41, "C#.wav", 2),
    RE("Re", 285.42, 302.394, "D.wav", 3),
    RES("Re#", 302.395, 320.37, "D#.wav",  4),
    MI("Mi", 320.38, 339.42, "E.wav",  5),
    FA("Fa", 339.43, 359.60, "F.wav", 6),
    FAS("Fa#", 359.61, 380.994, "F#.wav",  7),
    SOL("Sol", 380.995, 403.64, "G.wav", 8),
    SOLS("Sol#", 403.65, 427.64, "G#.wav",  9),
    LA("La", 427.65, 452.99, "A.wav", 10),
    LAS("La#", 453, 479.93, "A#.wav",  11),
    SI("Si", 479.94, 508.564, "B.wav",  12);


    private String nombre;
    private double minimaFrecuencia;
    private double maximaFrecuencia;
    private String path;
    private  int tono;

    private Notas(String nombre, double minimaFrecuencia, double maximaFrecuencia, String path, int tono){
        this.nombre=nombre;
        this.minimaFrecuencia=minimaFrecuencia;
        this.maximaFrecuencia=maximaFrecuencia;
        this.path=path;
        this.tono = tono;

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


    public int getTono(){ return this.tono;}
}
