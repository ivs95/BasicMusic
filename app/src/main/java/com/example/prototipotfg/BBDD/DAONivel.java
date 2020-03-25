package com.example.prototipotfg.BBDD;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DAONivel {


    @Query("SELECT * FROM niveladivinar WHERE correoUsuario = :correo AND modoJuego = :modo AND nivel = :nivel")
    NivelAdivinar findNivelAdivinar(String correo, String modo,int nivel);

    @Query("SELECT * FROM nivelimitar WHERE correoUsuario = :correo AND nivel = :nivel AND rangoVocal = :rango")
    NivelImitar findNivelImitar(String correo, String nivel, String rango);

    @Query("SELECT * FROM niveladivinar WHERE correoUsuario = :correo")
    List<NivelAdivinar> findNivelesAdivinarByCorreo(String correo);

    @Query("SELECT * FROM nivelimitar WHERE correoUsuario LIKE :correo")
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
