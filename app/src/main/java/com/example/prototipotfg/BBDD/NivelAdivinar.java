package com.example.prototipotfg.BBDD;


import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(primaryKeys={"correo_usuario","modo_juego","nivel"},
        foreignKeys = @ForeignKey(entity = Usuario.class, parentColumns = "correo", childColumns = "correo_usuario"))
public class NivelAdivinar {

    private String modo_juego;
    private int nivel;
    private boolean superado;
    private String correo_usuario;
    private int numAciertos;
    private int numFallos;


    public int getNumAciertos() {
        return numAciertos;
    }

    public void setNumAciertos(int numAciertos) {
        this.numAciertos = numAciertos;
    }

    public int getNumFallos() {
        return numFallos;
    }

    public void setNumFallos(int numFallos) {
        this.numFallos = numFallos;
    }
    public String getModo_juego() {
        return modo_juego;
    }

    public void setModo_juego(String modo_juego) {
        this.modo_juego = modo_juego;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Boolean getSuperado() {
        return superado;
    }

    public void setSuperado(Boolean superado) {
        this.superado = superado;
    }

    public String getCorreo_usuario() {
        return correo_usuario;
    }

    public void setCorreo_usuario(String correo_usuario) {
        this.correo_usuario = correo_usuario;
    }

}
