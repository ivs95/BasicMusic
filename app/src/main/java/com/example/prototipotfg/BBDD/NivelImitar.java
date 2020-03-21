package com.example.prototipotfg.BBDD;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import com.example.prototipotfg.Enumerados.RangosVocales;

import java.util.ArrayList;

@Entity(primaryKeys={"correo_usuario","nivel"},
        foreignKeys = @ForeignKey(entity = Usuario.class, parentColumns = "correo", childColumns = "correo_usuario"))
public class NivelImitar {
    @NonNull
    private String correo_usuario;
    private boolean superado;
    private float porcentajeAfinacion;
    private int numeroIntentos;
    private String rangosVocales;
    private int nivel;


    public String getCorreo_usuario() {
        return correo_usuario;
    }

    public void setCorreo_usuario(String correo_usuario) {
        this.correo_usuario = correo_usuario;
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

    public String getRangosVocales(){
        return rangosVocales;
    }

    public ArrayList<RangosVocales> getRangosVocalesNivel() {
        String[] rangos = rangosVocales.split(",");
        ArrayList<RangosVocales> retorno = new ArrayList<>();
        for (int i = 0; i < rangos.length; i++){
            retorno.add(RangosVocales.devuelveRVPorNombre(rangos[i]));
        }
        return retorno;
    }

    public void setRangosVocales(String rangosVocales) {
        this.rangosVocales = rangosVocales;
    }

    public void setRangosVocalesNivel(ArrayList<RangosVocales> rangosVocales) {
        this.rangosVocales = "";
        for (RangosVocales r : rangosVocales){
            this.rangosVocales.concat(r.getNombre() + ",");
        }
        this.rangosVocales = this.rangosVocales.substring(0, this.rangosVocales.length()-1);
    }

    //Funcion para añadir un rangoVocal al nivel
    public void addRangoVocal(RangosVocales rango){
        if (this.rangosVocales.isEmpty())
            this.rangosVocales = rango.getNombre();
        else
            this.rangosVocales.concat("," + rango.getNombre());
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
