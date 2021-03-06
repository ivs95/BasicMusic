package com.example.prototipotfg.Singletons;

import com.example.prototipotfg.Enumerados.Acordes;
import com.example.prototipotfg.Enumerados.Dificultad;
import com.example.prototipotfg.Enumerados.Intervalos;
import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Enumerados.Octavas;

import java.util.ArrayList;
import java.util.Arrays;

public final class Controlador {

    private static final Controlador INSTANCE = new Controlador();
    private ModoJuego modo_juego;
    private int nivel;
    private Dificultad dificultad;
    private ArrayList<Octavas> octavas = new ArrayList<>();
    private ArrayList<Intervalos> intervalos = new ArrayList<>();

    private int compas;
    private int num;
    private int longitud;
    private int pausa;

    private ArrayList<Acordes> acordes = new ArrayList<>();
    private int rango;
    private int num_opciones;

    private boolean mixIniciado;

    public static Controlador getInstance() {
        return INSTANCE;
    }

    private Controlador() {
    }

    public ArrayList<Acordes> getAcordes() {
        return acordes;
    }

    private void setAcordes(ArrayList<Acordes> acordes) {
        this.acordes = acordes;
    }

    public void setMixIniciado(boolean mixIniciado) {
        this.mixIniciado = mixIniciado;
    }

    public boolean getMixIniciado() {
        return this.mixIniciado;
    }

    private void setOctavas(ArrayList<Octavas> octavas) {
        this.octavas = octavas;
    }

    public ModoJuego getModo_juego() {
        return modo_juego;
    }

    public void setModo_juego(ModoJuego modo_juego) {
        this.modo_juego = modo_juego;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getNum_opciones() {
        return this.num_opciones;
    }

    public int getNivel() {
        return this.nivel;
    }

    public Dificultad getDificultad() {
        return dificultad;
    }

    public int getLongitud() {
        return this.longitud;
    }

    public int getCompas() {
        return this.compas;
    }

    public int getPausa() {
        return this.pausa;
    }

    public void estableceDificultad() {

        /*
         * Hacer un switch del tipo modo y modo juego como corresponda
         * Segun el nivel pues seguir tablita del word para setear numOpciones, Octavas y dificultad (configuracion en la tablita)
         * */

        switch (this.modo_juego) {
            case Adivinar_Intervalo:
                estableceDificultadAdivinarIntervalo();
                break;

            case Adivinar_Notas:
                estableceDificultadAdivinarNota();
                break;
            case Crear_Intervalo:
                estableceDificultadCrearIntervalo();
                break;
            case Adivinar_Acordes:
                estableceDificultadAdivinarAcorde();
                break;
            case Crear_Acordes:
                estableceDificultadCrearAcorde();
                break;
            case Halla_Ritmo:
                estableceDificultadDibujarRitmos();
                break;
            case Realiza_Ritmo:
                estableceDificultadImitarRitmos();
                break;
            default:
                break;
        }

    }

    private void estableceDificultadAdivinarAcorde() {
        switch (this.nivel) {
            case 1:
                this.num_opciones = 2;
                setAcordes(new ArrayList<>(Arrays.asList(Acordes.Acorde_mayor, Acordes.Acorde_menor,
                        Acordes.Acorde_2º_suspendida, Acordes.Acorde_4º_suspendida)));
                this.octavas = new ArrayList<>(Arrays.asList(Octavas.Cuarta, Octavas.Quinta));
                this.dificultad = Dificultad.Facil;
                break;
            case 2:
                this.num_opciones = 3;
                setAcordes(new ArrayList<>(Arrays.asList(Acordes.Acorde_mayor, Acordes.Acorde_menor,
                        Acordes.Acorde_2º_suspendida, Acordes.Acorde_4º_suspendida, Acordes.Acorde_disminuido, Acordes.Acorde_aumentado)));
                setOctavas(new ArrayList<>(Arrays.asList(Octavas.Tercera, Octavas.Cuarta, Octavas.Quinta)));
                this.dificultad = Dificultad.Medio;
                break;
            case 3:
                this.num_opciones = 3;
                setAcordes(new ArrayList<>(Arrays.asList(Acordes.Acorde_mayor, Acordes.Acorde_menor,
                        Acordes.Acorde_2º_suspendida, Acordes.Acorde_4º_suspendida, Acordes.Acorde_disminuido, Acordes.Acorde_aumentado,
                        Acordes.Acorde_mayor_7_menor, Acordes.Acorde_mayor_7_mayor)));

                setOctavas(new ArrayList<>(Arrays.asList(Octavas.Segunda, Octavas.Tercera, Octavas.Cuarta, Octavas.Quinta)));
                this.dificultad = Dificultad.Medio;
                break;
            case 4:
                this.num_opciones = 4;
                setAcordes(new ArrayList<>(Arrays.asList(Acordes.Acorde_mayor, Acordes.Acorde_menor,
                        Acordes.Acorde_2º_suspendida, Acordes.Acorde_4º_suspendida, Acordes.Acorde_disminuido, Acordes.Acorde_aumentado,
                        Acordes.Acorde_mayor_7_menor, Acordes.Acorde_mayor_7_mayor, Acordes.Acorde_menor_7_menor,
                        Acordes.Acorde_menor_7_mayor)));
                setOctavas(new ArrayList<>(Arrays.asList(Octavas.Segunda, Octavas.Tercera, Octavas.Cuarta, Octavas.Quinta, Octavas.Sexta)));
                this.dificultad = Dificultad.Medio;
                break;
            case 5:
                this.num_opciones = 4;
                setAcordes(new ArrayList<>(Arrays.asList(Acordes.Acorde_mayor, Acordes.Acorde_menor,
                        Acordes.Acorde_2º_suspendida, Acordes.Acorde_4º_suspendida, Acordes.Acorde_disminuido, Acordes.Acorde_aumentado,
                        Acordes.Acorde_mayor_7_menor, Acordes.Acorde_mayor_7_mayor, Acordes.Acorde_menor_7_menor,
                        Acordes.Acorde_menor_7_mayor, Acordes.Acorde_disminuido_7_menor, Acordes.Acorde_7_disminuida)));
                setOctavas(new ArrayList<>(Arrays.asList(Octavas.Segunda, Octavas.Tercera, Octavas.Cuarta, Octavas.Quinta, Octavas.Sexta)));
                this.dificultad = Dificultad.Medio;
                break;
            case 6:
                this.num_opciones = 5;
                setAcordes(new ArrayList<>(Arrays.asList(Acordes.Acorde_mayor, Acordes.Acorde_menor,
                        Acordes.Acorde_2º_suspendida, Acordes.Acorde_4º_suspendida, Acordes.Acorde_disminuido, Acordes.Acorde_aumentado,
                        Acordes.Acorde_mayor_7_menor, Acordes.Acorde_mayor_7_mayor, Acordes.Acorde_menor_7_menor,
                        Acordes.Acorde_menor_7_mayor, Acordes.Acorde_disminuido_7_menor, Acordes.Acorde_7_disminuida)));
                setOctavas(new ArrayList<>(Arrays.asList(Octavas.Primera, Octavas.Segunda, Octavas.Tercera, Octavas.Cuarta, Octavas.Quinta, Octavas.Sexta)));
                this.dificultad = Dificultad.Dificil;
                break;
            default:
                break;

        }
    }

    private void estableceDificultadCrearAcorde() {
        switch (this.nivel) {
            case 1:
                this.num_opciones = 2;
                setAcordes(new ArrayList<>(Arrays.asList(Acordes.Acorde_mayor, Acordes.Acorde_menor,
                        Acordes.Acorde_2º_suspendida, Acordes.Acorde_4º_suspendida)));
                this.octavas = new ArrayList<>(Arrays.asList(Octavas.Cuarta, Octavas.Quinta));
                this.dificultad = Dificultad.Facil;
                break;
            case 2:
                this.num_opciones = 3;
                setAcordes(new ArrayList<>(Arrays.asList(Acordes.Acorde_mayor, Acordes.Acorde_menor,
                        Acordes.Acorde_2º_suspendida, Acordes.Acorde_4º_suspendida, Acordes.Acorde_disminuido, Acordes.Acorde_aumentado)));
                setOctavas(new ArrayList<>(Arrays.asList(Octavas.Tercera, Octavas.Cuarta, Octavas.Quinta)));
                this.dificultad = Dificultad.Medio;
                break;
            case 3:
                this.num_opciones = 3;
                setAcordes(new ArrayList<>(Arrays.asList(Acordes.Acorde_mayor, Acordes.Acorde_menor,
                        Acordes.Acorde_2º_suspendida, Acordes.Acorde_4º_suspendida, Acordes.Acorde_disminuido, Acordes.Acorde_aumentado,
                        Acordes.Acorde_mayor_7_menor, Acordes.Acorde_mayor_7_mayor)));

                setOctavas(new ArrayList<>(Arrays.asList(Octavas.Segunda, Octavas.Tercera, Octavas.Cuarta, Octavas.Quinta)));
                this.dificultad = Dificultad.Medio;
                break;
            case 4:
                this.num_opciones = 4;
                setAcordes(new ArrayList<>(Arrays.asList(Acordes.Acorde_mayor, Acordes.Acorde_menor,
                        Acordes.Acorde_2º_suspendida, Acordes.Acorde_4º_suspendida, Acordes.Acorde_disminuido, Acordes.Acorde_aumentado,
                        Acordes.Acorde_mayor_7_menor, Acordes.Acorde_mayor_7_mayor, Acordes.Acorde_menor_7_menor,
                        Acordes.Acorde_menor_7_mayor)));
                setOctavas(new ArrayList<>(Arrays.asList(Octavas.Segunda, Octavas.Tercera, Octavas.Cuarta, Octavas.Quinta, Octavas.Sexta)));
                this.dificultad = Dificultad.Medio;
                break;
            case 5:
                this.num_opciones = 4;
                setAcordes(new ArrayList<>(Arrays.asList(Acordes.Acorde_mayor, Acordes.Acorde_menor,
                        Acordes.Acorde_2º_suspendida, Acordes.Acorde_4º_suspendida, Acordes.Acorde_disminuido, Acordes.Acorde_aumentado,
                        Acordes.Acorde_mayor_7_menor, Acordes.Acorde_mayor_7_mayor, Acordes.Acorde_menor_7_menor,
                        Acordes.Acorde_menor_7_mayor, Acordes.Acorde_disminuido_7_menor, Acordes.Acorde_7_disminuida)));
                setOctavas(new ArrayList<>(Arrays.asList(Octavas.Segunda, Octavas.Tercera, Octavas.Cuarta, Octavas.Quinta, Octavas.Sexta)));
                this.dificultad = Dificultad.Medio;
                break;
            case 6:
                this.num_opciones = 5;
                setAcordes(new ArrayList<>(Arrays.asList(Acordes.Acorde_mayor, Acordes.Acorde_menor,
                        Acordes.Acorde_2º_suspendida, Acordes.Acorde_4º_suspendida, Acordes.Acorde_disminuido, Acordes.Acorde_aumentado,
                        Acordes.Acorde_mayor_7_menor, Acordes.Acorde_mayor_7_mayor, Acordes.Acorde_menor_7_menor,
                        Acordes.Acorde_menor_7_mayor, Acordes.Acorde_disminuido_7_menor, Acordes.Acorde_7_disminuida)));
                setOctavas(new ArrayList<>(Arrays.asList(Octavas.Primera, Octavas.Segunda, Octavas.Tercera, Octavas.Cuarta, Octavas.Quinta, Octavas.Sexta)));
                this.dificultad = Dificultad.Dificil;
                break;
            default:
                break;
        }
    }

    private void estableceDificultadAdivinarIntervalo() {
        switch (this.nivel) {
            case 1:
                this.num_opciones = 2;
                setOctavas(new ArrayList<>(Arrays.asList(Octavas.Cuarta, Octavas.Quinta)));
                this.dificultad = Dificultad.Facil;
                this.rango = 3;
                this.intervalos = Intervalos.getIntervalosDeRango(getRango());
                break;
            case 2:
                this.num_opciones = 3;
                setOctavas(new ArrayList<>(Arrays.asList(Octavas.Tercera, Octavas.Cuarta, Octavas.Quinta, Octavas.Sexta)));
                this.dificultad = Dificultad.Medio;
                this.rango = 5;
                this.intervalos = Intervalos.getIntervalosDeRango(getRango());
                break;
            case 3:
                this.num_opciones = 4;
                setOctavas(new ArrayList<>(Arrays.asList(Octavas.Tercera, Octavas.Cuarta, Octavas.Quinta, Octavas.Sexta)));
                this.dificultad = Dificultad.Medio;
                this.rango = 7;
                this.intervalos = Intervalos.getIntervalosDeRango(getRango());
                break;
            case 4:
                this.num_opciones = 4;
                setOctavas(new ArrayList<>(Arrays.asList(Octavas.Segunda, Octavas.Tercera, Octavas.Cuarta, Octavas.Quinta, Octavas.Sexta)));
                this.dificultad = Dificultad.Medio;
                this.rango = 7;
                this.intervalos = Intervalos.getIntervalosDeRango(getRango());
                break;
            case 5:
                this.num_opciones = 4;
                setOctavas(new ArrayList<>(Arrays.asList(Octavas.Segunda, Octavas.Tercera, Octavas.Cuarta, Octavas.Quinta, Octavas.Sexta)));
                this.dificultad = Dificultad.Medio;
                this.rango = 9;
                this.intervalos = Intervalos.getIntervalosDeRango(getRango());
                break;
            case 6:
                this.num_opciones = 5;
                setOctavas(new ArrayList<>(Arrays.asList(Octavas.Segunda, Octavas.Tercera, Octavas.Cuarta, Octavas.Quinta, Octavas.Sexta, Octavas.Septima)));
                this.dificultad = Dificultad.Medio;
                this.rango = 12;
                this.intervalos = Intervalos.getIntervalosDeRango(getRango());
                break;
            default:
                break;
        }
    }

    private void estableceDificultadAdivinarNota() {
        switch (this.nivel) {
            case 1:
                this.num_opciones = 2;
                setOctavas(new ArrayList<>(Arrays.asList(Octavas.Cuarta, Octavas.Quinta)));
                this.dificultad = Dificultad.Facil;
                break;
            case 2:
                this.num_opciones = 2;
                setOctavas(new ArrayList<>(Arrays.asList(Octavas.Tercera, Octavas.Cuarta, Octavas.Quinta)));
                this.dificultad = Dificultad.Medio;
                break;
            case 3:
                this.num_opciones = 3;
                setOctavas(new ArrayList<>(Arrays.asList(Octavas.Tercera, Octavas.Cuarta, Octavas.Quinta)));
                this.dificultad = Dificultad.Medio;
                break;
            case 4:
                this.num_opciones = 3;
                setOctavas(new ArrayList<>(Arrays.asList(Octavas.Tercera, Octavas.Cuarta, Octavas.Quinta, Octavas.Sexta)));
                this.dificultad = Dificultad.Medio;
                break;
            case 5:
                this.num_opciones = 4;
                setOctavas(new ArrayList<>(Arrays.asList(Octavas.Tercera, Octavas.Cuarta, Octavas.Quinta, Octavas.Sexta)));
                this.dificultad = Dificultad.Medio;
                break;
            case 6:
                this.num_opciones = 4;
                setOctavas(new ArrayList<>(Arrays.asList(Octavas.Segunda, Octavas.Tercera, Octavas.Cuarta, Octavas.Quinta, Octavas.Sexta)));
                this.dificultad = Dificultad.Medio;
                break;
            case 7:
                this.num_opciones = 5;
                setOctavas(new ArrayList<>(Arrays.asList(Octavas.Segunda, Octavas.Tercera, Octavas.Cuarta, Octavas.Quinta, Octavas.Sexta)));
                this.dificultad = Dificultad.Medio;
                break;
            case 8:
                this.num_opciones = 5;
                setOctavas(new ArrayList<>(Arrays.asList(Octavas.Segunda, Octavas.Tercera, Octavas.Cuarta, Octavas.Quinta, Octavas.Sexta, Octavas.Septima)));
                this.dificultad = Dificultad.Medio;
                break;
            case 9:
                this.num_opciones = 6;
                setOctavas(new ArrayList<>(Arrays.asList(Octavas.Segunda, Octavas.Tercera, Octavas.Cuarta, Octavas.Quinta, Octavas.Sexta, Octavas.Septima)));
                this.dificultad = Dificultad.Medio;
                break;
            case 10:
                this.num_opciones = 7;
                setOctavas(new ArrayList<>(Arrays.asList(Octavas.Primera, Octavas.Segunda, Octavas.Tercera, Octavas.Cuarta, Octavas.Quinta, Octavas.Sexta, Octavas.Septima)));
                this.dificultad = Dificultad.Dificil;
                break;
            default:
                break;
        }
    }


    private void estableceDificultadCrearIntervalo() {
        switch (this.nivel) {
            case 1:
                this.num_opciones = 2;
                setOctavas(new ArrayList<>(Arrays.asList(Octavas.Cuarta, Octavas.Quinta)));
                this.dificultad = Dificultad.Facil;
                this.rango = 3;
                break;
            case 2:
                this.num_opciones = 3;
                setOctavas(new ArrayList<>(Arrays.asList(Octavas.Tercera, Octavas.Cuarta, Octavas.Quinta)));
                this.dificultad = Dificultad.Facil;
                this.rango = 5;
                break;
            case 3:
                this.num_opciones = 3;
                setOctavas(new ArrayList<>(Arrays.asList(Octavas.Tercera, Octavas.Cuarta, Octavas.Quinta, Octavas.Sexta)));
                this.dificultad = Dificultad.Facil;
                this.rango = 7;
                break;
            case 4:
                this.num_opciones = 3;
                setOctavas(new ArrayList<>(Arrays.asList(Octavas.Segunda, Octavas.Tercera, Octavas.Cuarta, Octavas.Quinta, Octavas.Sexta)));
                this.dificultad = Dificultad.Facil;
                this.rango = 9;
                break;
            case 5:
                this.num_opciones = 4;
                setOctavas(new ArrayList<>(Arrays.asList(Octavas.Segunda, Octavas.Tercera, Octavas.Cuarta, Octavas.Quinta, Octavas.Sexta)));
                this.dificultad = Dificultad.Dificil;
                this.rango = 9;
                break;
            case 6:
                this.num_opciones = 4;
                setOctavas(new ArrayList<>(Arrays.asList(Octavas.Segunda, Octavas.Tercera, Octavas.Cuarta, Octavas.Quinta, Octavas.Sexta)));
                this.dificultad = Dificultad.Dificil;
                this.rango = 11;
                break;
            case 7:
                this.num_opciones = 5;
                setOctavas(new ArrayList<>(Arrays.asList(Octavas.Segunda, Octavas.Tercera, Octavas.Cuarta, Octavas.Quinta, Octavas.Sexta)));
                this.dificultad = Dificultad.Dificil;
                this.rango = 11;
                break;
            case 8:
                this.num_opciones = 6;
                setOctavas(new ArrayList<>(Arrays.asList(Octavas.Segunda, Octavas.Tercera, Octavas.Cuarta, Octavas.Quinta, Octavas.Sexta)));
                this.dificultad = Dificultad.Dificil;
                this.rango = 12;
                break;
            default:
                break;
        }
    }

    private void estableceDificultadDibujarRitmos() {
        switch (this.nivel) {
            case 1:
                compas = 4;
                num = 2;
                longitud = compas * num;
                pausa = 325;
                break;
            case 2:
                compas = 4;
                num = 2;
                longitud = compas * num;
                pausa = 275;
                break;
            case 3:
                compas = 4;
                num = 2;
                longitud = compas * num;
                pausa = 325;
                break;
            case 4:
            case 6:
                compas = 4;
                num = 4;
                longitud = compas * num;
                pausa = 275;
                break;
            case 5:
            case 7:
                compas = 4;
                num = 4;
                longitud = compas * num;
                pausa = 325;
                break;
            case 8:
                compas = 4;
                num = 4;
                longitud = compas * num;
                pausa = 250;
                break;
        }
    }

    private void estableceDificultadImitarRitmos() {
        switch (this.nivel) {
            case 1:
            case 3:
                compas = 4;
                num = 2;
                longitud = compas * num;
                pausa = 275;
                break;
            case 2:
                compas = 4;
                num = 2;
                longitud = compas * num;
                pausa = 250;
                break;
            case 4:
            case 6:
                compas = 4;
                num = 4;
                longitud = compas * num;
                pausa = 250;
                break;
            case 5:
            case 7:
                compas = 4;
                num = 4;
                longitud = compas * num;
                pausa = 275;
                break;
            case 8:
                compas = 4;
                num = 4;
                longitud = compas * num;
                pausa = 225;
                break;
        }
    }

    public ArrayList<Octavas> getOctavas() {
        return this.octavas;
    }

    public int getRango() {
        return this.rango;
    }

    public ArrayList<Intervalos> getIntervalos() {
        return this.intervalos;
    }

}
