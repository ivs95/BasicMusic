package com.example.prototipotfg;


//Clase creada especialmente para las notas que atrapa el dispatcher en el modo imitar para así juntar la nota con su octava y un contador del numero de veces que ha sonado
public class NotasImitar {
    Notas nota;
    Integer octava;
    Integer contador;

    NotasImitar(Notas n, Integer o, Integer c){
        nota = n;
        octava = o;
        contador = c;
    }
    NotasImitar(Notas n, Integer o){
        nota = n;
        octava = o;
    }
}
