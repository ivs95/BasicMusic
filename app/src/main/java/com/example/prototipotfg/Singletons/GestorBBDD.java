package com.example.prototipotfg.Singletons;

import android.content.Context;

import com.example.prototipotfg.BBDD.AppDatabase;
import com.example.prototipotfg.BBDD.NivelAdivinar;
import com.example.prototipotfg.BBDD.NivelImitar;
import com.example.prototipotfg.BBDD.Puntuacion;
import com.example.prototipotfg.BBDD.Usuario;
import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Enumerados.RangosVocales;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class GestorBBDD {
    private static final GestorBBDD ourInstance = new GestorBBDD();
    private Context contexto;
    private Usuario usuarioLoggeado;
    private AppDatabase appDatabase;
    private final int NIVELES_ADIVINAR_NOTAS=10;
    private final int NIVELES_ADIVINAR_INTERVALOS=6;
    private final int NIVELES_CREAR_INTERVALOS=8;
    private final int NIVELES_ADIVINAR_ACORDES=6;
    private final int NIVELES_CREAR_ACORDES=6;
    private final String[] NIVELES_IMITAR_AUDIO = new String[]{"FACIL", "MEDIO", "DIFICIL"};
    private final int NIVELES_HALLAR_RITMO=8;
    private final int NIVELES_CREAR_RITMO=1;



    public static GestorBBDD getInstance(){
        return ourInstance;
    }
    public void setContexto(Context contexto){
        this.contexto = contexto;
        this.appDatabase = AppDatabase.getInstance(this.contexto);
    }

    public void setUsuarioLoggeado(Usuario usuario){
        this.usuarioLoggeado = usuario;
    }

    public boolean insertaUsuario(Usuario user){
        if (appDatabase.daoUsuario().findUsuario(user.getCorreo())==null){
            appDatabase.daoUsuario().insertUsuario(user);
            return true;
        }
        return false;
    }

    public void insertaNivelImitar(NivelImitar nivel){
        if (appDatabase.daoNivel().findNivelImitar(usuarioLoggeado.getCorreo(), nivel.getNivel(), nivel.getRangoVocal())==null){
            appDatabase.daoNivel().insertImitar(nivel);
        }
        else{
            actualizaNivelImitar(nivel);
        }
    }

    public void insertaNivelAdivinar(NivelAdivinar nivel){
        if (appDatabase.daoNivel().findNivelAdivinar(usuarioLoggeado.getCorreo(), nivel.getModoJuego(), nivel.getNivel())==null){
            appDatabase.daoNivel().insertAdivinar(nivel);
        }
        else{
            actualizaNivelAdivinar(nivel);
        }
    }

    private void actualizaNivelImitar(NivelImitar nivel){
        NivelImitar nivelOriginal = appDatabase.daoNivel().findNivelImitar(usuarioLoggeado.getCorreo(), nivel.getNivel(), nivel.getRangoVocal());
        nivelOriginal.actualizaPorcentajeAfinacion(nivel.getPorcentajeAfinacion());
        appDatabase.daoNivel().updateNivelImitar(nivelOriginal);

    }

    private void actualizaNivelAdivinar(NivelAdivinar nivel){
        NivelAdivinar nivelOriginal = appDatabase.daoNivel().findNivelAdivinar(usuarioLoggeado.getCorreo(), nivel.getModoJuego(), nivel.getNivel());
        nivelOriginal.actualizaEstadisticas(nivel.getSuperado());
        appDatabase.daoNivel().updateNivelAdivinar(nivelOriginal);
    }

    public void borraUsuario(Usuario usuario){
        if (appDatabase.daoUsuario().findUsuario(usuario.getCorreo())!= null)
            appDatabase.daoUsuario().deleteUsuario(usuario);
    }

    public void borraNivelImitar(NivelImitar nivel) {
        if (appDatabase.daoNivel().findNivelImitar(nivel.getCorreoUsuario(), nivel.getNivel(), nivel.getRangoVocal())!=null)
            appDatabase.daoNivel().deleteImitar(nivel);
    }

    public void borraNivelAdivinar(NivelAdivinar nivel){
        if (appDatabase.daoNivel().findNivelAdivinar(usuarioLoggeado.getCorreo(), nivel.getModoJuego(), nivel.getNivel())==null)
                appDatabase.daoNivel().deleteAdivinar(nivel);
    }

    public List<NivelImitar> devuelveNivelesImitar(String rangoVocal){
        return null;
    }

    public List<NivelAdivinar> devuelveNivelesAdivinar(String modo_juego){
        return null;
    }

    public Usuario getUsuarioLoggeado(){return usuarioLoggeado;}

    public boolean validaUsuario(String correo, String password, boolean recordado) {
        Usuario usuario = appDatabase.daoUsuario().findUsuario(correo);
        if (usuario != null) {
            if (usuario.getPassword().equals(password)){
                if (recordado) {
                    usuario.setRecordado(recordado);
                    appDatabase.daoUsuario().updateUsuario(usuario);
                }
                setUsuarioLoggeado(usuario);
                return true;
            }
        }
        return false;
    }

    public void cerrarSesion(){
        usuarioLoggeado.setRecordado(false);
        appDatabase.daoUsuario().updateUsuario(usuarioLoggeado);
        setUsuarioLoggeado(null);
    }

    public boolean registraUsuario(Usuario usuario) {
        if(appDatabase.daoUsuario().findUsuario(usuario.getCorreo())==null){
            appDatabase.daoUsuario().insertUsuario(usuario);
            return true;
        }
        return false;
    }

    public boolean UpdateUsuario(Usuario usuario) {
            appDatabase.daoUsuario().updateUsuario(usuario);
            return true;

    }

    public boolean usuarioRecordado() {
        Usuario usuario = appDatabase.daoUsuario().findUsuarioRecordado();
        if (usuario != null){
            setUsuarioLoggeado(usuario);
            return true;
        }
        return false;
    }

    public LinkedHashMap<String,String> devuelveEstadistica(String modo_juego){
        LinkedHashMap<String,String> retorno = null;
        switch(modo_juego) {
            case "Adivinar nota":
                retorno = estadisticaAdivinarNota();
                break;
            case "Adivinar intervalo":
                retorno = estadisticaAdivinarIntervalo();
                break;
            case "Crear intervalo":
                retorno = estadisticaCrearIntervalo();
                break;
            case "Adivinar acorde":
                retorno = estadisticaAdivinarAcorde();
                break;
            case "Crear acorde":
                retorno = estadisticaCrearAcorde();
                break;
            case "Imitar audio - Soprano":
                retorno = estadisticaImitarAudio(RangosVocales.Soprano.getNombre());
                break;
            case "Imitar audio - Mezzosoprano":
                retorno = estadisticaImitarAudio(RangosVocales.Mezzo.getNombre());
                break;
            case "Imitar audio - Contralto":
                retorno = estadisticaImitarAudio(RangosVocales.Contralto.getNombre());
                break;
            case "Imitar audio - Tenor":
                retorno = estadisticaImitarAudio(RangosVocales.Tenor.getNombre());
                break;
            case "Imitar audio - Barítono":
                retorno = estadisticaImitarAudio(RangosVocales.Baritono.getNombre());
                break;
            case "Imitar audio - Bajo":
                retorno = estadisticaImitarAudio(RangosVocales.Bajo.getNombre());
                break;
            case "Hallar ritmo":
                retorno = estadisticaHallarRitmo();
                break;
            case "Realizar ritmo":
                retorno = estadisticaRealizarRitmo();
                break;
            default:break;
        }
        return retorno;
    }

    private LinkedHashMap<String, String> estadisticaRealizarRitmo() {
        List<NivelAdivinar> listaNiveles = appDatabase.daoNivel().findNivelesAdivinarByCorreo(usuarioLoggeado.getCorreo(), ModoJuego.Realiza_Ritmo.toString());
        LinkedHashMap<String, String> retorno = new LinkedHashMap<>();
        String infoNivel = "";

        for (int i = 1; i <= NIVELES_CREAR_RITMO; i++){
            retorno.put("Nivel " + String.valueOf(i), "0 aciertos;0 fallos;0% acierto");
        }
        for (NivelAdivinar n : listaNiveles){
            infoNivel = n.getNumAciertos() + " aciertos;" + n.getNumFallos() + " fallos;" + (int)(((float)n.getNumAciertos()/(n.getNumAciertos()+n.getNumFallos()))*100) + "% acierto";
            retorno.put("Nivel " + n.getNivel().toString(), infoNivel);
        }
        return retorno;
    }

    private LinkedHashMap<String, String> estadisticaHallarRitmo() {
        List<NivelAdivinar> listaNiveles = appDatabase.daoNivel().findNivelesAdivinarByCorreo(usuarioLoggeado.getCorreo(), ModoJuego.Halla_Ritmo.toString());
        LinkedHashMap<String, String> retorno = new LinkedHashMap<>();
        String infoNivel = "";
        for (int i = 1; i <= NIVELES_HALLAR_RITMO; i++){
                retorno.put("Nivel " + String.valueOf(i), "0 aciertos;0 fallos;0% acierto");
        }
        for (NivelAdivinar n : listaNiveles){
            infoNivel = n.getNumAciertos() + " aciertos;" + n.getNumFallos() + " fallos;" + (int)(((float)n.getNumAciertos()/(n.getNumAciertos()+n.getNumFallos()))*100) + "% acierto";
            retorno.put("Nivel " + n.getNivel().toString(), infoNivel);
        }

        return retorno;
    }

    private LinkedHashMap<String, String> estadisticaImitarAudio(String rangoVocal) {
    List<NivelImitar> listaNiveles = appDatabase.daoNivel().findNivelesImitarByCorreo(usuarioLoggeado.getCorreo(), rangoVocal);
        LinkedHashMap<String, String> retorno = new LinkedHashMap<>();
        String infoNivel = "";
        for (int i = 0; i < NIVELES_IMITAR_AUDIO.length; i++){
            retorno.put("Dificultad " + NIVELES_IMITAR_AUDIO[i].toLowerCase(), "0 intentos;0% afinacion");
        }
        for (NivelImitar n : listaNiveles){
            infoNivel = n.getNumeroIntentos() + " intentos;" + n.getPorcentajeAfinacion() + "% afinacion";
            retorno.put("Dificultad " + n.getNivel(), infoNivel);
        }

        return retorno;
    }

    private LinkedHashMap<String, String> estadisticaCrearAcorde() {
        List<NivelAdivinar> listaNiveles = appDatabase.daoNivel().findNivelesAdivinarByCorreo(usuarioLoggeado.getCorreo(), ModoJuego.Crear_Acordes.toString());
        LinkedHashMap<String, String> retorno = new LinkedHashMap<>();
        String infoNivel = "";
        for (int i = 1; i <= NIVELES_CREAR_ACORDES; i++){
                retorno.put("Nivel " + String.valueOf(i), "0 aciertos;0 fallos;0% acierto");
        }
        for (NivelAdivinar n : listaNiveles){
            infoNivel = n.getNumAciertos() + " aciertos;" + n.getNumFallos() + " fallos;" + (int)(((float)n.getNumAciertos()/(n.getNumAciertos()+n.getNumFallos()))*100) + "% acierto";
            retorno.put("Nivel " + n.getNivel().toString(), infoNivel);
        }

        return retorno;
    }

    private LinkedHashMap<String, String> estadisticaAdivinarAcorde() {
        List<NivelAdivinar> listaNiveles = appDatabase.daoNivel().findNivelesAdivinarByCorreo(usuarioLoggeado.getCorreo(), ModoJuego.Adivinar_Acordes.toString());
        LinkedHashMap<String, String> retorno = new LinkedHashMap<>();
        String infoNivel = "";
        for (int i = 1; i <= NIVELES_ADIVINAR_ACORDES; i++){
                retorno.put("Nivel " + String.valueOf(i), "0 aciertos;0 fallos;0% acierto");
        }
        for (NivelAdivinar n : listaNiveles){
            infoNivel = n.getNumAciertos() + " aciertos;" + n.getNumFallos() + " fallos;" + (int)(((float)n.getNumAciertos()/(n.getNumAciertos()+n.getNumFallos()))*100) + "% acierto";
            retorno.put("Nivel " + n.getNivel().toString(), infoNivel);
        }

        return retorno;
    }

    private LinkedHashMap<String, String> estadisticaCrearIntervalo() {
        List<NivelAdivinar> listaNiveles = appDatabase.daoNivel().findNivelesAdivinarByCorreo(usuarioLoggeado.getCorreo(), ModoJuego.Crear_Intervalo.toString());
        LinkedHashMap<String, String> retorno = new LinkedHashMap<>();
        String infoNivel = "";
        for (int i = 1; i <= NIVELES_CREAR_INTERVALOS; i++){
            retorno.put("Nivel " + String.valueOf(i), "0 aciertos;0 fallos;0% acierto");

        }
        for (NivelAdivinar n : listaNiveles){
            infoNivel = n.getNumAciertos() + " aciertos;" + n.getNumFallos() + " fallos;" + (int)(((float)n.getNumAciertos()/(n.getNumAciertos()+n.getNumFallos()))*100) + "% acierto";
            retorno.put("Nivel " + n.getNivel().toString(), infoNivel);
        }

        return retorno;
    }

    private LinkedHashMap<String, String> estadisticaAdivinarIntervalo() {
        List<NivelAdivinar> listaNiveles = appDatabase.daoNivel().findNivelesAdivinarByCorreo(usuarioLoggeado.getCorreo(), ModoJuego.Adivinar_Intervalo.toString());
        LinkedHashMap<String, String> retorno = new LinkedHashMap<>();
        String infoNivel = "";
        for (int i = 1; i <= NIVELES_ADIVINAR_INTERVALOS; i++){
            retorno.put("Nivel " + String.valueOf(i), "0 aciertos;0 fallos;0% acierto");
        }
        for (NivelAdivinar n : listaNiveles){
            infoNivel = n.getNumAciertos() + " aciertos;" + n.getNumFallos() + " fallos;" + (int)(((float)n.getNumAciertos()/(n.getNumAciertos()+n.getNumFallos()))*100) + "% acierto";
            retorno.put("Nivel " + n.getNivel().toString(), infoNivel);
        }

        return retorno;
    }

    private LinkedHashMap<String, String> estadisticaAdivinarNota() {
        List<NivelAdivinar> listaNiveles = appDatabase.daoNivel().findNivelesAdivinarByCorreo(usuarioLoggeado.getCorreo(), ModoJuego.Adivinar_Notas.toString());
        LinkedHashMap<String, String> retorno = new LinkedHashMap<>();
        String infoNivel = "";
        for (int i = 1; i <= NIVELES_ADIVINAR_NOTAS; i++){
            retorno.put("Nivel " + String.valueOf(i), "0 aciertos;0 fallos;0% acierto");
        }
        for (NivelAdivinar n : listaNiveles){
            infoNivel = n.getNumAciertos() + " aciertos;" + n.getNumFallos() + " fallos;" + (int)(((float)n.getNumAciertos()/(n.getNumAciertos()+n.getNumFallos()))*100) + "% acierto";
            retorno.put("Nivel " + n.getNivel().toString(), infoNivel);
        }

        return retorno;
    }

    public Puntuacion devuelvePuntuacion(String modoJuego){
        Puntuacion puntuacion = appDatabase.daoPuntuacion().findPuntuacion(usuarioLoggeado.getCorreo(), modoJuego);
        return puntuacion;
    }

    public void actualizarPuntuacion(int nivel, String modoJuego, boolean superado){
        switch(modoJuego) {
            case "Adivinar nota":
                puntuacionAdivinarNota(nivel, superado);
                break;
            case "Adivinar intervalo":
                puntuacionAdivinarIntervalo(nivel, superado);
                break;
            case "Crear intervalo":
                puntuacionCrearIntervalo(nivel, superado);
                break;
            case "Adivinar acorde":
                puntuacionAdivinarAcorde(nivel, superado);
                break;
            case "Crear acorde":
                puntuacionCrearAcorde(nivel, superado);
                break;
            case "Imitar audio - Soprano":
                puntuacionImitarAudio(RangosVocales.Soprano.getNombre(), nivel, superado);
                break;
            case "Imitar audio - Mezzosoprano":
                puntuacionImitarAudio(RangosVocales.Mezzo.getNombre(), nivel, superado);
                break;
            case "Imitar audio - Contralto":
                puntuacionImitarAudio(RangosVocales.Contralto.getNombre(), nivel, superado);
                break;
            case "Imitar audio - Tenor":
                puntuacionImitarAudio(RangosVocales.Tenor.getNombre(), nivel, superado);
                break;
            case "Imitar audio - Barítono":
                puntuacionImitarAudio(RangosVocales.Baritono.getNombre(), nivel, superado);
                break;
            case "Imitar audio - Bajo":
                puntuacionImitarAudio(RangosVocales.Bajo.getNombre(), nivel, superado);
                break;
            case "Hallar ritmo":
                puntuacionHallarRitmo(nivel, superado);
                break;
            case "Realizar ritmo":
                puntuacionRealizarRitmo(nivel, superado);
                break;
            default:
                break;
        }

    }

    private void puntuacionRealizarRitmo(int nivel, boolean superado) {

    }

    private void puntuacionHallarRitmo(int nivel, boolean superado) {
        int puntuacion_fallo = 0;
        switch (nivel){
            case 1: puntuacion_fallo = 1;
            case 2: puntuacion_fallo = 1;
            case 3: puntuacion_fallo = 1;
            case 4: puntuacion_fallo = 1;
            case 5: puntuacion_fallo = 1;
            case 6: puntuacion_fallo = 2;
            case 7: puntuacion_fallo = 3;
            case 8: puntuacion_fallo = 3;
            default: break;
        }

        if(superado)
            this.appDatabase.daoPuntuacion().findPuntuacion(this.usuarioLoggeado.getCorreo(), ModoJuego.Halla_Ritmo.toString()).actualizarPuntuacionTotal(3, true);
        else
            this.appDatabase.daoPuntuacion().findPuntuacion(this.usuarioLoggeado.getCorreo(), ModoJuego.Halla_Ritmo.toString()).actualizarPuntuacionTotal(puntuacion_fallo, false);

    }

    private void puntuacionImitarAudio(String rangoVocal, int nivel, boolean superado) {

    }

    private void puntuacionCrearAcorde(int nivel, boolean superado) {
        int puntuacion_fallo = 0;
        switch (nivel){
            case 1: puntuacion_fallo = 1;
            case 2: puntuacion_fallo = 1;
            case 3: puntuacion_fallo = 2;
            case 4: puntuacion_fallo = 2;
            case 5: puntuacion_fallo = 3;
            case 6: puntuacion_fallo = 3;
            default: break;
        }

        if(superado)
            this.appDatabase.daoPuntuacion().findPuntuacion(this.usuarioLoggeado.getCorreo(), ModoJuego.Crear_Acordes.toString()).actualizarPuntuacionTotal(3, true);
        else
            this.appDatabase.daoPuntuacion().findPuntuacion(this.usuarioLoggeado.getCorreo(), ModoJuego.Crear_Acordes.toString()).actualizarPuntuacionTotal(puntuacion_fallo, false);

    }

    private void puntuacionAdivinarAcorde(int nivel, boolean superado) {
        int puntuacion_fallo = 0;
        switch (nivel){
            case 1: puntuacion_fallo = 1;
            case 2: puntuacion_fallo = 1;
            case 3: puntuacion_fallo = 2;
            case 4: puntuacion_fallo = 2;
            case 5: puntuacion_fallo = 3;
            case 6: puntuacion_fallo = 3;
            default: break;
        }

        if(superado)
            this.appDatabase.daoPuntuacion().findPuntuacion(this.usuarioLoggeado.getCorreo(), ModoJuego.Adivinar_Acordes.toString()).actualizarPuntuacionTotal(3, true);
        else
            this.appDatabase.daoPuntuacion().findPuntuacion(this.usuarioLoggeado.getCorreo(), ModoJuego.Adivinar_Acordes.toString()).actualizarPuntuacionTotal(puntuacion_fallo, false);

    }

    private void puntuacionCrearIntervalo(int nivel, boolean superado) {
        int puntuacion_fallo = 0;
        switch (nivel){
            case 1: puntuacion_fallo = 1;
            case 2: puntuacion_fallo = 1;
            case 3: puntuacion_fallo = 1;
            case 4: puntuacion_fallo = 2;
            case 5: puntuacion_fallo = 2;
            case 6: puntuacion_fallo = 2;
            case 7: puntuacion_fallo = 3;
            case 8: puntuacion_fallo = 3;
            default: break;
        }

        if(superado)
            this.appDatabase.daoPuntuacion().findPuntuacion(this.usuarioLoggeado.getCorreo(), ModoJuego.Crear_Intervalo.toString()).actualizarPuntuacionTotal(3, true);
        else
            this.appDatabase.daoPuntuacion().findPuntuacion(this.usuarioLoggeado.getCorreo(), ModoJuego.Crear_Intervalo.toString()).actualizarPuntuacionTotal(puntuacion_fallo, false);

    }

    private void puntuacionAdivinarIntervalo(int nivel, boolean superado) {

        int puntuacion_fallo = 0;
        switch (nivel){
            case 1: puntuacion_fallo = 1;
            case 2: puntuacion_fallo = 1;
            case 3: puntuacion_fallo = 2;
            case 4: puntuacion_fallo = 2;
            case 5: puntuacion_fallo = 3;
            case 6: puntuacion_fallo = 3;
            default: break;
        }

        if(superado)
            this.appDatabase.daoPuntuacion().findPuntuacion(this.usuarioLoggeado.getCorreo(), ModoJuego.Adivinar_Intervalo.toString()).actualizarPuntuacionTotal(3, true);
        else
            this.appDatabase.daoPuntuacion().findPuntuacion(this.usuarioLoggeado.getCorreo(), ModoJuego.Adivinar_Intervalo.toString()).actualizarPuntuacionTotal(puntuacion_fallo, false);

    }

    private void puntuacionAdivinarNota(int nivel, boolean superado) {
        int puntuacion_fallo = 0;
        switch (nivel){
            case 1: puntuacion_fallo = 1;
            case 2: puntuacion_fallo = 1;
            case 3: puntuacion_fallo = 1;
            case 4: puntuacion_fallo = 1;
            case 5: puntuacion_fallo = 2;
            case 6: puntuacion_fallo = 2;
            case 7: puntuacion_fallo = 2;
            case 8: puntuacion_fallo = 2;
            case 9: puntuacion_fallo = 3;
            case 10: puntuacion_fallo =3;
            default: break;
        }

        if(superado)
            this.appDatabase.daoPuntuacion().findPuntuacion(this.usuarioLoggeado.getCorreo(), ModoJuego.Adivinar_Notas.toString()).actualizarPuntuacionTotal(3, true);
        else
            this.appDatabase.daoPuntuacion().findPuntuacion(this.usuarioLoggeado.getCorreo(), ModoJuego.Adivinar_Notas.toString()).actualizarPuntuacionTotal(puntuacion_fallo, false);

    }



}
