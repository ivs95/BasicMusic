package com.example.prototipotfg.BBDD;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface DAOUsuario {

    @Query("SELECT * FROM usuario WHERE correo LIKE :correo")
    Usuario findUsuario(String correo);

    @Insert
    public void insertUsuario(Usuario usuario);

    @Update
    public void updateUsuario(Usuario usuario);

    @Delete
    public void deleteUsuario(Usuario usuario);

    @Query("SELECT * FROM usuario WHERE recordado = 1")
    Usuario findUsuarioRecordado();
}
