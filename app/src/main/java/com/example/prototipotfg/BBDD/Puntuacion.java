package com.example.prototipotfg.BBDD;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

@Entity(primaryKeys={"correoUsuario","modoJuego"},
        foreignKeys = @ForeignKey(entity = Usuario.class, parentColumns = "correo", childColumns = "correoUsuario"))
public class Puntuacion {
    @NonNull
    private String modoJuego;
    private int nivel;
    @NonNull
    private String correoUsuario;
    private int puntuacionTotal;

    public Puntuacion(@NonNull String modoJuego, int nivel, @NonNull String correoUsuario, int puntuacionTotal) {
        this.modoJuego = modoJuego;
        this.nivel = nivel;
        this.correoUsuario = correoUsuario;
        this.puntuacionTotal = puntuacionTotal;
    }

    public String getModoJuego(){return this.modoJuego;}

    public  int getNivel(){return this.nivel;}

    public String getCorreoUsuario(){return this.correoUsuario;}

    public int getPuntuacionTotal(){return this.puntuacionTotal;}

    public Puntuacion actualizarPuntuacionTotal(int puntuacion, boolean superado){
        if(superado)
            this.puntuacionTotal += puntuacion;
        else{
            if(this.puntuacionTotal - puntuacion < 0) puntuacionTotal = 0;
            else this.puntuacionTotal -= puntuacion;
        }
        actualizaNivel();
        return this;
    }

    private void actualizaNivel() {
       switch (this.modoJuego){
           case "Adivinar_Notas":
               if(this.puntuacionTotal >= 0 && this.puntuacionTotal < 30) this.nivel = 1;
               else if (this.puntuacionTotal >= 30 && this.puntuacionTotal < 60) this.nivel = 2;
               else if (this.puntuacionTotal >= 60 && this.puntuacionTotal < 90) this.nivel = 3;
               else if (this.puntuacionTotal >= 90 && this.puntuacionTotal < 123) this.nivel = 4;
               else if (this.puntuacionTotal >= 120 && this.puntuacionTotal < 156) this.nivel = 5;
               else if (this.puntuacionTotal >= 150 && this.puntuacionTotal < 189) this.nivel = 6;
               else if (this.puntuacionTotal >= 180 && this.puntuacionTotal < 225) this.nivel = 7;
               else if (this.puntuacionTotal >= 225 && this.puntuacionTotal < 261) this.nivel = 8;
               else if (this.puntuacionTotal >= 261 && this.puntuacionTotal < 297) this.nivel = 9;
               else this.nivel = 10;
               break;
           default:break;

       }
    }
}
