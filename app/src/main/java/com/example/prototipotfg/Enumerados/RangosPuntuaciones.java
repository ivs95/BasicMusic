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

    public static RangosPuntuaciones actualizaRango(String modoJuego, int puntuacion){
        RangosPuntuaciones nuevo_rango = null;
        switch (modoJuego){
            case "Adivinar_Notas":
                if(puntuacion >= 0 && puntuacion < 30) nuevo_rango = RangosPuntuaciones.Principiante;
                else if(puntuacion >= 30 && puntuacion < 138) nuevo_rango = RangosPuntuaciones.Aprendiz;
                else if(puntuacion >= 138 && puntuacion < 225) nuevo_rango = RangosPuntuaciones.Veterano;
                else if(puntuacion >= 225 && puntuacion < 273) nuevo_rango = RangosPuntuaciones.Experto;
                else if(puntuacion >= 273 && puntuacion < 324) nuevo_rango = RangosPuntuaciones.Maestro;
                else if(puntuacion >= 324 && puntuacion < 378) nuevo_rango = RangosPuntuaciones.GranMaestro;
                else return RangosPuntuaciones.Leyenda;
                break;
            case "Adivinar_Intervalo":

            case "Adivinar_Acordes":

            case "Crear_Acordes":
                if(puntuacion >= 0 && puntuacion < 30) nuevo_rango = RangosPuntuaciones.Principiante;
                else if(puntuacion >= 30 && puntuacion < 63) nuevo_rango = RangosPuntuaciones.Aprendiz;
                else if(puntuacion >= 63 && puntuacion < 99) nuevo_rango = RangosPuntuaciones.Veterano;
                else if(puntuacion >= 99 && puntuacion < 138) nuevo_rango = RangosPuntuaciones.Experto;
                else if(puntuacion >= 138 && puntuacion < 180) nuevo_rango = RangosPuntuaciones.Maestro;
                else nuevo_rango = RangosPuntuaciones.Leyenda;
                break;
            case "Crear_Intervalo":
            case "Halla_Ritmo":

            case "Realiza_Ritmo":
                if(puntuacion >= 0 && puntuacion < 30) nuevo_rango = RangosPuntuaciones.Principiante;
                else if(puntuacion >= 30 && puntuacion < 138) nuevo_rango = RangosPuntuaciones.Aprendiz;
                else if(puntuacion >= 138 && puntuacion < 225) nuevo_rango = RangosPuntuaciones.Veterano;
                else if(puntuacion >= 225 && puntuacion < 273) nuevo_rango = RangosPuntuaciones.Experto;
                else if(puntuacion >= 273 && puntuacion < 324) nuevo_rango = RangosPuntuaciones.Maestro;
                else if(puntuacion >= 324 && puntuacion < 378) nuevo_rango = RangosPuntuaciones.GranMaestro;
                else nuevo_rango = RangosPuntuaciones.Leyenda;
                break;
            default:break;
        }

        return nuevo_rango;
    }

    }
