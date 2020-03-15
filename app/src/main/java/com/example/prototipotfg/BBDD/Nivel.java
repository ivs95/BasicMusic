package com.example.prototipotfg.BBDD;


import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(primaryKeys={"modo_juego","nivel"},
        foreignKeys = @ForeignKey(entity = Usuario.class, parentColumns = "id", childColumns = "id_usuario"))
public class Nivel {

    private String modo_juego;
    private int nivel;
    private Boolean superado;
    private int id_usuario;

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

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

}
