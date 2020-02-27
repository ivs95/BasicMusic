package com.example.prototipotfg.Enumerados;

public enum DuracionSonido {
   // Blanca(500)
    Negra(500,  1),
    Corchea(250,  2),
    Semicorchea(125,  3),
    Silencio(500, 0);

    private int silencio;
    private int simbolo;

    private DuracionSonido (int silencio, int simbolo){
        this.silencio=silencio;
        this.simbolo=simbolo;
    }

    public int getSimbolo(){
        return this.simbolo;
    }

    public static DuracionSonido getSonidoPorSimbolo(int simbolo){
        for (DuracionSonido d: DuracionSonido.values()
        ) {
            if (d.getSimbolo()==simbolo) return d;
        }
        return null;
    }


    public int getSilencio() {
        return silencio;
    }
}
