package com.example.prototipotfg.BBDD;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DAONivel {


    @Query("SELECT * FROM niveladivinar WHERE correo_usuario = :correo AND modo_juego = :modo AND nivel = :nivel")
    NivelAdivinar findNivelAdivinar(String correo, String modo,int nivel);

    @Query("SELECT * FROM nivelimitar WHERE correo_usuario = :correo AND nivel = :nivel")
    NivelImitar findNivelImitar(String correo, int nivel);

    @Query("SELECT * FROM niveladivinar WHERE correo_usuario = :correo")
    List<NivelAdivinar> findNivelesAdivinarByCorreo(String correo);

    @Query("SELECT * FROM nivelimitar WHERE correo_usuario LIKE :correo")
    List<NivelImitar> findNivelesImitarByCorreo(String correo);

    @Insert
    void insertAdivinar(NivelAdivinar nivelAdivinar);

    @Insert
    void insertImitar(NivelImitar nivelImitar);

    @Insert
    void insertAllAdivinar(NivelAdivinar... nivelesAdivinar);

    @Insert
    void insertAllImitar(NivelImitar... nivelesImitar);

    @Update
    public void updateNivelAdivinar(NivelAdivinar nivelAdivinar);

    @Update
    public void updateNivelImitar(NivelImitar nivelImitar);

    @Delete
    void deleteAdivinar(NivelAdivinar nivelAdivinar);

    @Delete
    void deleteImitar(NivelImitar nivelImitar);

}
