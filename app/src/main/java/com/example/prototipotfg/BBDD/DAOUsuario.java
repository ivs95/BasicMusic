package com.example.prototipotfg.BBDD;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface DAOUsuario {

    @Query("SELECT * FROM usuario WHERE correo LIKE :correo")
    Usuario existeUsuario(String correo);

    @Insert
    public void insertaUsuario(Usuario usuario);






}
