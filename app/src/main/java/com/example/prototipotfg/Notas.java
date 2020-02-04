package com.example.prototipotfg;

public enum Notas {
    DO1("Do1", 31.7, 33.7, "C.wav", 1),
    DOS1("Do#1",33.71, 35.65, "C#.wav", 1),
    RE1("Re1", 35.71, 37.71, "D.wav", 1),
    RES1("Re#1", 37.89, 39.89, "D#.wav", 1),
    MI1("Mi1", 40.2, 42.2, "E.wav",1),
    FA1("Fa1", 42.65, 44.65, "piano/F#1.wav", 1),
    FAS1("Fa#1", 45.25, 47.25, "piano/F#1.wav",1),
    SOL1("Sol1", 48, 50, "piano/G1.wav", 1),
    SOLS1("Sol#1", 50.91, 52.91, "piano/G#1.wav", 1),
    LA1("La1", 54, 56, "piano/A1.wav",1),
    LAS1("La#1", 57.27, 59.27, "piano/A#1.wav",1),
    SI1("Si1", 60.74, 62.74, "piano/B1.wav",1),
    DO2("Do2", "piano/C1.wav", 2),
    DOS2("Do#2","piano/C#1.wav", 2),
    RE2("Re2","piano/D1.wav", 2),
    RES2("Re#2","piano/D#1.wav", 2),
    MI2("Mi2","piano/E1.wav.wav",2),
    FA2("Fa2", "piano/F#1.wav", 2),
    FAS2("Fa#2", "piano/F1.wav",2),
    SOL2("Sol2", "piano/G1.wav", 2),
    SOLS2("Sol#2","piano/G#1.wav", 2),
    LA2("La2","piano/A1.wav",2),
    LAS2("La#2","piano/A#1.wav",2),
    SI2("Si2","piano/B1.wav",2);



    private String nombre;
    private double minimaFrecuencia;
    private double maximaFrecuencia;
    private String path;
    private int octava;
    private Notas(String nombre, double minimaFrecuencia, double maximaFrecuencia, String path,int octava){
        this.nombre=nombre;
        this.minimaFrecuencia=minimaFrecuencia;
        this.maximaFrecuencia=maximaFrecuencia;
        this.path=path;
        this.octava=octava;
    }
    private Notas(String nombre, String path,int octava){
        this.nombre=nombre;
        this.path=path;
        this.octava=octava;
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
