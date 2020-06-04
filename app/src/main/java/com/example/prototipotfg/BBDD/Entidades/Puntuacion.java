package com.example.prototipotfg.BBDD.Entidades;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Enumerados.PuntosNiveles;
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
    private boolean iniciado;

    public Puntuacion(@NonNull String modoJuego, int nivel, @NonNull String correoUsuario, int puntuacionTotal, String rango, boolean iniciado) {
        this.modoJuego = modoJuego;
        this.nivel = nivel;
        this.correoUsuario = correoUsuario;
        this.puntuacionTotal = puntuacionTotal;
        this.rango = rango;
        this.iniciado = iniciado;
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
        this.nivel = PuntosNiveles.devuelveNivel(this.puntuacionTotal, ModoJuego.valueOf(this.modoJuego));
        this.rango = RangosPuntuaciones.actualizaRango(this.modoJuego, this.puntuacionTotal).toString();
    }

    public boolean isIniciado() {
        return iniciado;
    }

    public void setIniciado(boolean iniciado) {
        this.iniciado = iniciado;
    }
}
