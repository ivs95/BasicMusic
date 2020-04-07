package com.example.prototipotfg.BBDD;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Usuario.class, NivelAdivinar.class, NivelImitar.class, Puntuacion.class}, version=1)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase instance;
    public abstract DAONivel daoNivel();
    public abstract DAOUsuario daoUsuario();
    public abstract  DAOPuntuacion daoPuntuacion();

    public static synchronized AppDatabase getInstance(Context context){
        if (instance == null){
            instance = create(context);
        }

        //context.deleteDatabase("database-name");
        return instance;
    }

    private static AppDatabase create(final Context context){
            return Room.databaseBuilder(context, AppDatabase.class, "database-name").allowMainThreadQueries().build();
    }
}
