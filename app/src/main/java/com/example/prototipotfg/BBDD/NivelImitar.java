package com.example.prototipotfg.BBDD;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;


@Entity(primaryKeys={"correoUsuario","nivel", "rangoVocal"},
        foreignKeys = @ForeignKey(entity = Usuario.class, parentColumns = "correo", childColumns = "correoUsuario"))
public class NivelImitar {
    @NonNull
    private String correoUsuario;
    private boolean superado;
    private float porcentajeAfinacion;
    private int numeroIntentos;
    @NonNull
    private String rangoVocal;
    @NonNull
    private int nivel;

    public NivelImitar(@NonNull String correoUsuario, boolean superado, float porcentajeAfinacion, int numeroIntentos, @NonNull String rangoVocal, @NonNull int nivel){
        this.correoUsuario=correoUsuario;
        this.superado=superado;
        this.porcentajeAfinacion=porcentajeAfinacion;
        this.numeroIntentos=numeroIntentos;
        this.rangoVocal=rangoVocal;
        this.nivel=nivel;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    public boolean isSuperado() {
        return superado;
    }

    public void setSuperado(boolean superado) {
        this.superado = superado;
    }

    public float getPorcentajeAfinacion() {
        return porcentajeAfinacion;
    }

    public void setPorcentajeAfinacion(float porcentajeAfinacion) {
        this.porcentajeAfinacion = porcentajeAfinacion;
    }

    public int getNumeroIntentos() {
        return numeroIntentos;
    }

    public void setNumeroIntentos(int numeroIntentos) {
        this.numeroIntentos = numeroIntentos;
    }

    public String getRangoVocal(){
        return rangoVocal;
    }


    public void setRangoVocal(String rangoVocal) {
        this.rangoVocal = rangoVocal;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    //Funcion para actualizar el porcentaje y nº de aciertos con cada nuevo intento
    public void actualizaPorcentajeAfinacion(float ultimoResultado){
        float total = this.porcentajeAfinacion*this.numeroIntentos + ultimoResultado;
        this.numeroIntentos++;
        setPorcentajeAfinacion(total/numeroIntentos);
    }

}
