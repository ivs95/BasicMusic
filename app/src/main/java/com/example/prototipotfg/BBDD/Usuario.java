package com.example.prototipotfg.BBDD;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Usuario {
    @PrimaryKey
    private int id;
    private String nombre;
    private String password;


    public Usuario(){}

    public int getUid() {
        return id;
    }

    public void setUid(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
