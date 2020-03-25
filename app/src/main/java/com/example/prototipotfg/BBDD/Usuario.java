package com.example.prototipotfg.BBDD;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Usuario {



    @PrimaryKey @NonNull
    private String correo;
    private String nombre;
    private String password;


    public Usuario(){}

    public Usuario(@NonNull String correo, String nombre, String password) {
        this.correo = correo;
        this.nombre = nombre;
        this.password = password;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
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
