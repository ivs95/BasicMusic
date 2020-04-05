package com.example.prototipotfg.BBDD;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(primaryKeys={"correoUsuario","modoJuego"},
        foreignKeys = @ForeignKey(entity = Usuario.class, parentColumns = "correo", childColumns = "correoUsuario"))
public class Puntuacion {
    @NonNull
    private String modoJuego;
    private int nivel;
    private int ult_nivel;
    @NonNull
    private String correoUsuario;

    private int puntuacionTotal = 0;

    public Puntuacion(@NonNull String modoJuego, int nivel, int ult_nivel, @NonNull String correoUsuario, int puntuacionTotal) {
        this.modoJuego = modoJuego;
        this.nivel = nivel;
        this.ult_nivel = ult_nivel;
        this.correoUsuario = correoUsuario;
        this.puntuacionTotal = puntuacionTotal;
    }

    public String getModoJuego(){return this.modoJuego;}

    public  int getNivel(){return this.nivel;}

    public int getUlt_nivel(){return  this.ult_nivel;}

    public String getCorreoUsuario(){return this.correoUsuario;}

    public int getPuntuacionTotal(){return this.puntuacionTotal;}

    public void actualizarPuntuacionTotal(int puntuacion, boolean superado){
        if(superado)
            this.puntuacionTotal += puntuacion;
        else{
            if(this.puntuacionTotal - puntuacion < 0) puntuacionTotal = 0;
            else this.puntuacionTotal -= puntuacion;
        }

    }
}
