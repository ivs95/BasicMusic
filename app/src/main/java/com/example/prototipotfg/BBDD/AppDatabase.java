package com.example.prototipotfg.BBDD;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Usuario.class, NivelAdivinar.class, NivelImitar.class}, version=1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DAONivel daoNivel();
    public abstract DAOUsuario daoUsuario();
}
