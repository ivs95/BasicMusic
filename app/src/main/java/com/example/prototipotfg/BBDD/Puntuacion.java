package com.example.prototipotfg.BBDD;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import com.example.prototipotfg.Enumerados.RangosPuntuaciones;

@Entity(primaryKeys={"correoUsuario","modoJuego"},
        foreignKeys = @ForeignKey(entity = Usuario.class, parentColumns = "correo", childColumns = "correoUsuario"))
public class Puntuacion {
    @NonNull
    private String modoJuego;
    private int nivel;
    @NonNull
    private String correoUsuario;
    private int puntuacionTotal;
    private String rango;

    public Puntuacion(@NonNull String modoJuego, int nivel, @NonNull String correoUsuario, int puntuacionTotal, String rango) {
        this.modoJuego = modoJuego;
        this.nivel = nivel;
        this.correoUsuario = correoUsuario;
        this.puntuacionTotal = puntuacionTotal;
        this.rango = rango;
    }

    public String getModoJuego(){return this.modoJuego;}

    public  int getNivel(){return this.nivel;}

    public String getCorreoUsuario(){return this.correoUsuario;}

    public int getPuntuacionTotal(){return this.puntuacionTotal;}

    public String getRango(){return this.rango;}

    public Puntuacion actualizarPuntuacionTotal(int puntuacion, boolean superado){
        if(superado)
            this.puntuacionTotal += puntuacion;
        else{
            if(this.puntuacionTotal - puntuacion < 0) puntuacionTotal = 0;
            else this.puntuacionTotal -= puntuacion;
        }
        actualizaNivel();
        return this;
    }

    private void actualizaNivel() {
        if(this.puntuacionTotal >= 0 && this.puntuacionTotal < 30) this.nivel = 1;
        else if (this.puntuacionTotal >= 30 && this.puntuacionTotal < 63) this.nivel = 2;
        else if (this.puntuacionTotal >= 63 && this.puntuacionTotal < 99) this.nivel = 3;
        else if (this.puntuacionTotal >= 99 && this.puntuacionTotal < 138) this.nivel = 4;
        else if (this.puntuacionTotal >= 138 && this.puntuacionTotal < 180) this.nivel = 5;
        else if (this.puntuacionTotal >= 180 && this.puntuacionTotal < 225) this.nivel = 6;
        else if (this.puntuacionTotal >= 225 && this.puntuacionTotal < 273) this.nivel = 7;
        else if (this.puntuacionTotal >= 273 && this.puntuacionTotal < 324) this.nivel = 8;
        else if (this.puntuacionTotal >= 324 && this.puntuacionTotal < 378) this.nivel = 9;
        else this.nivel = 10;

        this.rango = RangosPuntuaciones.actualizaRango(this.modoJuego, this.puntuacionTotal).toString();
    }
}
