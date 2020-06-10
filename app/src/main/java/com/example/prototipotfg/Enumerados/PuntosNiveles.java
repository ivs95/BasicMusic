package com.example.prototipotfg.Enumerados;

public enum PuntosNiveles {
    Uno(1, 0, 30, false),
    Dos(2,30,63, false),
    Tres(3,63,99, false),
    Cuatro(4,99,138, false),
    Cinco(5,138,180, false),
    Seis(6,180,225, false),
    Siete(7,225,273, false),
    Ocho(8,273,324, false),
    Nueve(9,324,378, false),
    Diez(10,378,999, false),

    UnoImitar(1, 0, 20, true),
    DosImitar(2,20,43, true),
    TresImitar(3,43,66, true),
    CuatroImitar(4,66,89, true),
    CincoImitar(5,89,115, true),
    SeisImitar(6,115,141, true),
    SieteImitar(7,141,170, true),
    OchoImitar(8,170,999, true);

    private int nivel;
    private int minPuntos;
    private int maxPuntos;
    private boolean imitar;

    PuntosNiveles(int nivel, int minPuntos, int maxPuntos, boolean imitar){
        this.nivel = nivel;
        this.minPuntos = minPuntos;
        this.maxPuntos=maxPuntos;
        this.imitar = imitar;
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


    public int getMaxPuntos() {
        return maxPuntos;
    }


    public static int devuelveNivel(int puntuacion, ModoJuego modoJuego){
        int nivel = 0;

        if(modoJuego != ModoJuego.Imitar_Audio) {
            for (PuntosNiveles p : PuntosNiveles.values()) {
                if(!p.imitar) {
                    if (puntuacion >= p.getMinPuntos() && puntuacion < p.getMaxPuntos())
                        nivel = p.getNivel();
                }
            }
        }
        else {
            for (PuntosNiveles p : PuntosNiveles.values()) {
                if(p.imitar) {
                    if (puntuacion >= p.getMinPuntos() && puntuacion < p.getMaxPuntos())
                        nivel = p.getNivel();
                }
            }
        }
        if(nivel > modoJuego.getMax_level()) return modoJuego.getMax_level();
        else return nivel;
    }


}
