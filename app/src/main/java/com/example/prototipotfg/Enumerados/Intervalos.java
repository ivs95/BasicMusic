package com.example.prototipotfg.Enumerados;

import com.example.prototipotfg.Singletons.Controlador;

import java.util.ArrayList;

public enum Intervalos {

    //Unisono("Unisono", 0, 0),
    Segunda_menor("Segunda Menor Ascendente",1, 1),
    Segunda_mayor("Segunda Mayor Ascendente",2, 2),
    Tercera_menor("Tercera Menor Ascendente",3, 3),
    Tercera_mayor("Tercera Mayor Ascendente",4, 4),
    Cuarta_justa("Cuarta Justa Ascendente",5, 5),
    Cuarta_aumentada("Cuarta Aumentada Ascendente",6, 6),
    Quinta_justa("Quinta Justa Ascendente",7, 7),
    Sexta_menor("Sexta Menor Ascendente",8, 8),
    Sexta_mayor("Sexta Mayor Ascendente",9, 9),
    Septima_menor("Septima Menor Ascendente",10, 10),
    Septima_mayor("Septima Mayor Ascendente",11, 11),
    Octava_justa("Octava Justa Ascendente",12, 12),
    Segunda_menor_descendente("Segunda Menor Descendente",-1, -1),
    Segunda_mayor_descendente("Segunda Mayor Descendente",-2, -2),
    Tercera_menor_descendente("Tercera Menor Descendente",-3, -3),
    Tercera_mayor_descendente("Tercera Mayor Descendente",-4, -4),
    Cuarta_justa_descendente("Cuarta Justa Descendente",-5, -5),
    Cuarta_aumentada_descendente("Cuarta Aumentada Descendente",-6, -6),
    Quinta_justa_descendente("Quinta Justa Descendente",-7, -7),
    Sexta_menor_descendente("Sexta Menor Descendente", -8, -8),
    Sexta_mayor_descendente("Sexta Mayor Descendente", -9, -9),
    Septima_menor_descendente("Septima Menor Descendente",-10, -10),
    Septima_mayor_descendente("Septima Mayor Descendente",-11, -11),
    Octava_justa_descendente("Octava Justa Descendente",-12, -12);

    private String nombre;
    private int diferencia;
    private int numero;

    Intervalos(String nombre, int diferencia, int numero){
        this.nombre=nombre;
        this.diferencia=diferencia;
        this.numero=numero;
    }

    public static ArrayList<Intervalos> getIntervalosDeRango(int rango) {
        ArrayList<Intervalos> retorno = new ArrayList<>();
        for (Intervalos i : Intervalos.values()) {
            if (Math.abs(i.getDiferencia()) <= rango)
                retorno.add(i);
        }
        return retorno;
    }

    public String getNombre() {
        return nombre;
    }

    public int getDiferencia() {
        return diferencia;
    }

    public int getNumero() {
        return numero;
    }

    public static Intervalos getIntervaloPorDiferencia(int tono){
        for (Intervalos i: Intervalos.values()
        ) {
            if (i.getDiferencia()==tono) return i;
        }
        return null;
    }


    public static ArrayList<Intervalos> devuelveIntervalosPosibles(Notas nota){

        ArrayList<Intervalos> retorno = new ArrayList<>();
        int tono = nota.getTono();
        int negativos = -(nota.getTono() - 1);
        int positivos = 12 - nota.getTono();

        for (int i = -1; i >= negativos; i--){
                retorno.add(getIntervaloPorDiferencia(i));
        }
        for (int i = 1; i <= positivos; i++){
                retorno.add(getIntervaloPorDiferencia(tono + i));
        }

        return retorno;
    }



}
