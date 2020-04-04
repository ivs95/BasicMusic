package com.example.prototipotfg.Enumerados;

public enum RangosPuntuaciones {
    Principiante("Principiante", 0, 30),
    Aprendiz("Aprendiz", 30, 60),
    Veterano("Veterano", 60, 90),
    Experto("Experto", 90, 120),
    Maestro("Maestro", 120, 150),
    GranMaestro("Gran Maestro", 150, 180),
    Leyenda("Leyenda", 180, 210);

    private String nombre;
    private int puntuacion_inicio;
    private int getPuntuacion_fin;

    RangosPuntuaciones(String nombre, int puntuacion_inicio ,int getPuntuacion_fin){
        this.nombre=nombre;
        this.puntuacion_inicio = puntuacion_inicio;
        this.getPuntuacion_fin = getPuntuacion_fin;
    }


    }
