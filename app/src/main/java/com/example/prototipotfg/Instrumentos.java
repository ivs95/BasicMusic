package com.example.prototipotfg;

public enum Instrumentos {
    Piano("Piano","piano/"),
    Guitarra("Guitarra","guitarra/");
    /*Primera("Primera","uno/", 1),
    Primera("Primera","uno/", 1),
    Primera("Primera","uno/", 1),
    Primera("Primera","uno/", 1),
    Primera("Primera","uno/", 1),*/


    private String nombre;
    private String path;

    private Instrumentos(String nombre, String path){
        this.nombre=nombre;
        this.path=path;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }



}
