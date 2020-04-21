package com.example.prototipotfg.BBDD;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface DAOPuntuacion {
    @Query("SELECT * FROM puntuacion WHERE correoUsuario = :correo AND modoJuego = :modo")
    Puntuacion findPuntuacion(String correo, String modo);

    @Insert
    void insertPuntuacion(Puntuacion puntuacion);

    @Insert
    void insertAllPuntuacion(Puntuacion... puntuaciones);

    @Update
    public void updatePuntuacion(Puntuacion puntuacion);

    @Delete
    public void deletePuntuacion(Puntuacion puntuacion);


}
