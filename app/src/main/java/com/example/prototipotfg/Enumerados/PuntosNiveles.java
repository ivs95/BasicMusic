package com.example.prototipotfg.Enumerados;

public enum PuntosNiveles {
    Uno(1, 0, 30),
    Dos(2,30,63),
    Tres(3,63,99),
    Cuatro(4,99,138),
    Cinco(5,138,180),
    Seis(6,180,225),
    Siete(7,225,273),
    Ocho(8,273,324),
    Nueve(9,324,378),
    Diez(10,378,999);

    private int nivel;
    private int minPuntos;
    private int maxPuntos;

    PuntosNiveles(int nivel, int minPuntos, int maxPuntos){
        this.nivel = nivel;
        this.minPuntos = minPuntos;
        this.maxPuntos=maxPuntos;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getMinPuntos() {
        return minPuntos;
    }

    public void setMinPuntos(int minPuntos) {
        this.minPuntos = minPuntos;
    }

    public int getMaxPuntos() {
        return maxPuntos;
    }

    public void setMaxPuntos(int maxPuntos) {
        this.maxPuntos = maxPuntos;
    }

    public static int devuelveNivel(int puntuacion){
        for (PuntosNiveles p : PuntosNiveles.values()){
            if (puntuacion >= p.getMinPuntos() && puntuacion < p.getMaxPuntos())
                return p.getNivel();
        }
        return 10;
    }
}
