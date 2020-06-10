package com.example.prototipotfg.BBDD;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.prototipotfg.BBDD.Entidades.Puntuacion;

@Dao
public interface DAOPuntuacion {
    @Query("SELECT * FROM puntuacion WHERE correoUsuario = :correo AND modoJuego = :modo")
    Puntuacion findPuntuacion(String correo, String modo);

    @Insert
    void insertPuntuacion(Puntuacion puntuacion);


    @Update
    public void updatePuntuacion(Puntuacion puntuacion);


}
