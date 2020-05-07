package com.example.prototipotfg.Enumerados;

public enum NivelExamen {
    Uno(1,new ModoJuego[]{ModoJuego.Adivinar_Notas,ModoJuego.Adivinar_Intervalo, ModoJuego.Crear_Intervalo, ModoJuego.Adivinar_Acordes,
            ModoJuego.Crear_Acordes,ModoJuego.Halla_Ritmo, ModoJuego.Realiza_Ritmo}, new int[]{1,1,1,1,1,1,1}, 7),
    Dos(2, new ModoJuego[]{ModoJuego.Adivinar_Notas,ModoJuego.Adivinar_Intervalo, ModoJuego.Crear_Intervalo, ModoJuego.Adivinar_Acordes,
            ModoJuego.Crear_Acordes,ModoJuego.Halla_Ritmo, ModoJuego.Realiza_Ritmo}, new int[]{2,2,2,2,2,2,2}, 7),
    Tres(3, new ModoJuego[]{ModoJuego.Adivinar_Notas,ModoJuego.Adivinar_Intervalo, ModoJuego.Crear_Intervalo, ModoJuego.Adivinar_Acordes,
            ModoJuego.Crear_Acordes,ModoJuego.Halla_Ritmo, ModoJuego.Realiza_Ritmo}, new int[]{3,3,3,3,2,2,2}, 8),
    Cuatro(4,new ModoJuego[]{ModoJuego.Adivinar_Notas,ModoJuego.Adivinar_Intervalo, ModoJuego.Crear_Intervalo, ModoJuego.Adivinar_Acordes,
            ModoJuego.Crear_Acordes,ModoJuego.Halla_Ritmo, ModoJuego.Realiza_Ritmo}, new int[]{4,3,4,3,3,3,3}, 8),
    Cinco(5,new ModoJuego[]{ModoJuego.Adivinar_Notas,ModoJuego.Adivinar_Intervalo, ModoJuego.Crear_Intervalo, ModoJuego.Adivinar_Acordes,
            ModoJuego.Crear_Acordes,ModoJuego.Halla_Ritmo, ModoJuego.Realiza_Ritmo}, new int[]{5,3,4,4,3,4,4}, 9),
    Seis(6,new ModoJuego[]{ModoJuego.Adivinar_Notas,ModoJuego.Adivinar_Intervalo, ModoJuego.Crear_Intervalo, ModoJuego.Adivinar_Acordes,
            ModoJuego.Crear_Acordes,ModoJuego.Halla_Ritmo, ModoJuego.Realiza_Ritmo}, new int[]{6,4,5,4,4,5,4}, 10),
    Siete(7,new ModoJuego[]{ModoJuego.Adivinar_Notas,ModoJuego.Adivinar_Intervalo, ModoJuego.Crear_Intervalo, ModoJuego.Adivinar_Acordes,
            ModoJuego.Crear_Acordes,ModoJuego.Halla_Ritmo, ModoJuego.Realiza_Ritmo}, new int[]{7,4,6,5,4,6,5}, 11),
    Ocho(8,new ModoJuego[]{ModoJuego.Adivinar_Notas,ModoJuego.Adivinar_Intervalo, ModoJuego.Crear_Intervalo, ModoJuego.Adivinar_Acordes,
            ModoJuego.Crear_Acordes,ModoJuego.Halla_Ritmo, ModoJuego.Realiza_Ritmo}, new int[]{8,5,6,5,5,6,6}, 12),
    Nueve(9,new ModoJuego[]{ModoJuego.Adivinar_Notas,ModoJuego.Adivinar_Intervalo, ModoJuego.Crear_Intervalo, ModoJuego.Adivinar_Acordes,
            ModoJuego.Crear_Acordes,ModoJuego.Halla_Ritmo, ModoJuego.Realiza_Ritmo}, new int[]{9,5,7,5,5,7,7}, 13),
    Diez(10,new ModoJuego[]{ModoJuego.Adivinar_Notas,ModoJuego.Adivinar_Intervalo, ModoJuego.Crear_Intervalo, ModoJuego.Adivinar_Acordes,
            ModoJuego.Crear_Acordes,ModoJuego.Halla_Ritmo, ModoJuego.Realiza_Ritmo}, new int[]{10,6,8,6,6,8,8}, 14);

    /*
    * 2 preguntas cada tipo
    * Puntuaciones desbloquear y rango como notas
    * Porcentaje pruebas acertadas
    * Puntos como notas
    * Mezclar las pruebas
    * onResume para lanzar siguiente prueba
    * */

    private int nivel;
    private ModoJuego[] modos;
    private int[] niveles_modos;
    private int aciertosAprobar;

    NivelExamen(int nivel , ModoJuego [] modos, int[] niveles_modos, int aciertosAprobar){
        this.nivel = nivel;
        this.modos = modos;
        this.niveles_modos = niveles_modos;
        this.aciertosAprobar=aciertosAprobar;
    }

    public static NivelExamen getNivelExamen(int nivel) {
        for (NivelExamen n : values()){
            if (n.getNivel()==nivel)
                return n;
        }
        return null;
    }

    public int getNivelModo(ModoJuego modo){
        for (int i = 0; i < modos.length; i++){
            if (modo == modos[i]){
                return niveles_modos[i];
            }
        }
        return 0;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getAciertosAprobar() {
        return aciertosAprobar;
    }

    public void setAciertosAprobar(int aciertosAprobar) {
        this.aciertosAprobar = aciertosAprobar;
    }

    public ModoJuego[] getModos() {
        return modos;
    }

    public void setModos(ModoJuego[] modos) {
        this.modos = modos;
    }

    public int[] getNiveles_modos() {
        return niveles_modos;
    }

    public void setNiveles_modos(int[] niveles_modos) {
        this.niveles_modos = niveles_modos;
    }
}
