package com.example.prototipotfg.Enumerados;

import com.example.prototipotfg.R;

public enum RangosPuntuaciones {
    Principiante("Principiante", R.drawable.semifusa),
    Aprendiz("Aprendiz", R.drawable.fusa),
    Veterano("Veterano", R.drawable.semicorchea),
    Experto("Experto", R.drawable.corchea),
    Maestro("Maestro", R.drawable.negra),
    GranMaestro("Gran Maestro", R.drawable.blanca),
    Leyenda("Leyenda", R.drawable.redonda);

    private String nombre;
    private int image;

    RangosPuntuaciones(String nombre, int image){
        this.nombre=nombre;
        this.image = image;
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
                else if(puntuacion >= 324 && puntuacion < 398) nuevo_rango = RangosPuntuaciones.GranMaestro;
                else return RangosPuntuaciones.Leyenda;
                break;
            case "Adivinar_Intervalo":

            case "Adivinar_Acordes":

            case "Crear_Acordes":
                if(puntuacion >= 0 && puntuacion < 15) nuevo_rango = RangosPuntuaciones.Principiante;
                else if(puntuacion >= 15 && puntuacion < 30) nuevo_rango = RangosPuntuaciones.Aprendiz;
                else if(puntuacion >= 30 && puntuacion < 63) nuevo_rango = RangosPuntuaciones.Veterano;
                else if(puntuacion >= 63 && puntuacion < 99) nuevo_rango = RangosPuntuaciones.Experto;
                else if(puntuacion >= 99 && puntuacion < 138) nuevo_rango = RangosPuntuaciones.Maestro;
                else if(puntuacion >= 138 && puntuacion < 200) nuevo_rango = RangosPuntuaciones.GranMaestro;
                else nuevo_rango = RangosPuntuaciones.Leyenda;
                break;
            case "Crear_Intervalo":
            case "Halla_Ritmo":

            case "Realiza_Ritmo":
                if(puntuacion >= 0 && puntuacion < 30) nuevo_rango = RangosPuntuaciones.Principiante;
                else if(puntuacion >= 30 && puntuacion < 99) nuevo_rango = RangosPuntuaciones.Aprendiz;
                else if(puntuacion >= 99 && puntuacion < 138) nuevo_rango = RangosPuntuaciones.Veterano;
                else if(puntuacion >= 138 && puntuacion < 180) nuevo_rango = RangosPuntuaciones.Experto;
                else if(puntuacion >= 180 && puntuacion < 225) nuevo_rango = RangosPuntuaciones.Maestro;
                else if(puntuacion >= 225 && puntuacion < 293) nuevo_rango = RangosPuntuaciones.GranMaestro;
                else nuevo_rango = RangosPuntuaciones.Leyenda;
                break;
            default:break;
        }

        return nuevo_rango;
    }

    public int getImage(){return this.image;}

    public static RangosPuntuaciones getRangoPorNombre(String name){
        RangosPuntuaciones rango = null;
        for(int i = 0; i < RangosPuntuaciones.values().length; i++){
            if(RangosPuntuaciones.values()[i].nombre.equals(name)) rango = RangosPuntuaciones.values()[i];

        }
        return rango;
    }

    }
