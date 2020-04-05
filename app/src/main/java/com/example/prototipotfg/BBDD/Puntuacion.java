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
    @NonNull
    private String correoUsuario;
    private int puntuacionTotal;

    public Puntuacion(@NonNull String modoJuego, int nivel, @NonNull String correoUsuario, int puntuacionTotal) {
        this.modoJuego = modoJuego;
        this.nivel = nivel;
        this.correoUsuario = correoUsuario;
        this.puntuacionTotal = puntuacionTotal;
    }

    public String getModoJuego(){return this.modoJuego;}

    public  int getNivel(){return this.nivel;}

    public String getCorreoUsuario(){return this.correoUsuario;}

    public int getPuntuacionTotal(){return this.puntuacionTotal;}

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
    }
}
