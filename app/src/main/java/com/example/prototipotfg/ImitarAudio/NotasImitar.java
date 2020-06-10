package com.example.prototipotfg.ImitarAudio;


import com.example.prototipotfg.Enumerados.Notas;

//Clase creada especialmente para las notas que atrapa el dispatcher en el modo imitar para así juntar la nota con su octava y un contador del numero de veces que ha sonado
public class NotasImitar {
    private Notas nota;
    private Integer octava;
    private Integer contador;

    public NotasImitar(Notas n, Integer o, Integer c) {
        nota = n;
        octava = o;
        contador = c;
    }

    public NotasImitar(Notas n, Integer o) {
        nota = n;
        octava = o;
    }

    public Notas getNota() {
        return nota;
    }

    public Integer getOctava() {
        return octava;
    }

    public Integer getContador() {
        return contador;
    }
}
