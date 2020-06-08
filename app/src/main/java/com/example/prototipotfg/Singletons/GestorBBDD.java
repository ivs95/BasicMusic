package com.example.prototipotfg.Singletons;

import android.content.Context;

import com.example.prototipotfg.BBDD.AppDatabase;
import com.example.prototipotfg.BBDD.Entidades.NivelAdivinar;
import com.example.prototipotfg.BBDD.Entidades.NivelImitar;
import com.example.prototipotfg.BBDD.Entidades.Puntuacion;
import com.example.prototipotfg.BBDD.Entidades.Usuario;
import com.example.prototipotfg.Enumerados.ModoJuego;
import com.example.prototipotfg.Enumerados.RangosPuntuaciones;
import com.example.prototipotfg.Enumerados.RangosVocales;

import org.jetbrains.annotations.NotNull;

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
    private final int NIVELES_IMITAR_AUDIO = 8;
    private final int NIVELES_HALLAR_RITMO=8;
    private final int NIVELES_CREAR_RITMO=8;
    private final int NIVELES_MODO_MIX = 10;



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

    private void inicializaPuntuacionUsuario(Usuario user) {
        for (int i = 0; i < ModoJuego.values().length; i++) {
                Puntuacion p = new Puntuacion(ModoJuego.values()[i].toString(), 1, user.getCorreo(), 0, RangosPuntuaciones.Principiante.toString(), false);
                appDatabase.daoPuntuacion().insertPuntuacion(p);
        }

    }

    public boolean registraUsuario(Usuario usuario) {
        if(appDatabase.daoUsuario().findUsuario(usuario.getCorreo())==null){
            appDatabase.daoUsuario().insertUsuario(usuario);
            inicializaPuntuacionUsuario(usuario);
            }
        return true;
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
            case "Adivinar Notas":
                retorno = estadisticaAdivinarNota();
                break;
            case "Adivinar Intervalos":
                retorno = estadisticaAdivinarIntervalo();
                break;
            case "Crear Intervalos":
                retorno = estadisticaCrearIntervalo();
                break;
            case "Adivinar Acordes":
                retorno = estadisticaAdivinarAcorde();
                break;
            case "Crear Acordes":
                retorno = estadisticaCrearAcorde();
                break;
            case "Entonación - Hombre":
                retorno = estadisticaImitarAudio(RangosVocales.Hombre.getNombre());
                break;
            case "Entonación - Mujer":
                retorno = estadisticaImitarAudio(RangosVocales.Mujer.getNombre());
                break;
            case "Entonación - Niño":
                retorno = estadisticaImitarAudio(RangosVocales.Niño.getNombre());
                break;
            case "Dibujar Ritmos":
                retorno = estadisticaHallarRitmo();
                break;
            case "Imitar Ritmos":
                retorno = estadisticaRealizarRitmo();
                break;
            case "Modo Mix":
                retorno = estadisticaModoMix();
                break;
            default:break;
        }
        return retorno;
    }

    private LinkedHashMap<String, String> estadisticaModoMix() {
        List<NivelAdivinar> listaNiveles = appDatabase.daoNivel().findNivelesAdivinarByCorreo(usuarioLoggeado.getCorreo(), ModoJuego.Modo_Mix.getNombre());
        LinkedHashMap<String, String> retorno = new LinkedHashMap<>();
        String infoNivel = "";
        for (int i = 1; i <= NIVELES_MODO_MIX; i++){
            retorno.put("Nivel " + String.valueOf(i), "0 aciertos;0 fallos;0% acierto");
        }
        for (NivelAdivinar n : listaNiveles){
            infoNivel = n.getNumAciertos() + " aciertos;" + n.getNumFallos() + " fallos;" + (int)(((float)n.getNumAciertos()/(n.getNumAciertos()+n.getNumFallos()))*100) + "% acierto";
            retorno.put("Nivel " + n.getNivel().toString(), infoNivel);
        }
        return retorno;
    }

    private LinkedHashMap<String, String> estadisticaRealizarRitmo() {
        List<NivelAdivinar> listaNiveles = appDatabase.daoNivel().findNivelesAdivinarByCorreo(usuarioLoggeado.getCorreo(), ModoJuego.Realiza_Ritmo.getNombre());
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
        List<NivelAdivinar> listaNiveles = appDatabase.daoNivel().findNivelesAdivinarByCorreo(usuarioLoggeado.getCorreo(), ModoJuego.Halla_Ritmo.getNombre());
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
        for (int i = 1; i <= NIVELES_IMITAR_AUDIO; i++){
            retorno.put("Nivel " + String.valueOf(i), "0 intentos;0% afinacion");
        }
        for (NivelImitar n : listaNiveles){
            infoNivel = n.getNumeroIntentos() + " intentos;" + n.getPorcentajeAfinacion() + "% afinacion";
            retorno.put("Nivel " + n.getNivel(), infoNivel);
        }

        return retorno;
    }

    private LinkedHashMap<String, String> estadisticaCrearAcorde() {
        List<NivelAdivinar> listaNiveles = appDatabase.daoNivel().findNivelesAdivinarByCorreo(usuarioLoggeado.getCorreo(), ModoJuego.Crear_Acordes.getNombre());
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
        List<NivelAdivinar> listaNiveles = appDatabase.daoNivel().findNivelesAdivinarByCorreo(usuarioLoggeado.getCorreo(), ModoJuego.Adivinar_Acordes.getNombre());
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
        List<NivelAdivinar> listaNiveles = appDatabase.daoNivel().findNivelesAdivinarByCorreo(usuarioLoggeado.getCorreo(), ModoJuego.Crear_Intervalo.getNombre());
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
        List<NivelAdivinar> listaNiveles = appDatabase.daoNivel().findNivelesAdivinarByCorreo(usuarioLoggeado.getCorreo(), ModoJuego.Adivinar_Intervalo.getNombre());
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
        List<NivelAdivinar> listaNiveles = appDatabase.daoNivel().findNivelesAdivinarByCorreo(usuarioLoggeado.getCorreo(), ModoJuego.Adivinar_Notas.getNombre());
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

    public void actualizarPuntuacion(int nivel, @NotNull String modoJuego, boolean superado){
        switch(modoJuego) {
            case "Adivinar_Notas":
                puntuacionAdivinarNota(nivel, superado);
                break;
            case "Adivinar_Intervalo":
                puntuacionAdivinarIntervalo(nivel, superado);
                break;
            case "Crear_Intervalo":
                puntuacionCrearIntervalo(nivel, superado);
                break;
            case "Adivinar_Acordes":
                puntuacionAdivinarAcorde(nivel, superado);
                break;
            case "Crear_Acordes":
                puntuacionCrearAcorde(nivel, superado);
                break;
            case "Imitar_Audio":
                puntuacionImitarAudio(nivel, superado);
                break;
            case "Halla_Ritmo":
                puntuacionHallarRitmo(nivel, superado);
                break;
            case "Realiza_Ritmo":
                puntuacionRealizarRitmo(nivel, superado);
                break;
            case "Modo_Mix":
                puntuacionModoMix(nivel, superado);
                break;
            default:
                break;
        }

    }


    private void puntuacionRealizarRitmo(int nivel, boolean superado) {
        int puntuacion_fallo = 0;
        switch (nivel){
            case 1: puntuacion_fallo = 1; break;
            case 2: puntuacion_fallo = 1; break;
            case 3: puntuacion_fallo = 1; break;
            case 4: puntuacion_fallo = 1; break;
            case 5: puntuacion_fallo = 1; break;
            case 6: puntuacion_fallo = 2; break;
            case 7: puntuacion_fallo = 3; break;
            case 8: puntuacion_fallo = 3; break;
            default: break;
        }

        if(superado)
            this.appDatabase.daoPuntuacion().updatePuntuacion(this.appDatabase.daoPuntuacion().findPuntuacion(this.usuarioLoggeado.getCorreo(), ModoJuego.Realiza_Ritmo.toString()).actualizarPuntuacionTotal(3, true));
        else
            this.appDatabase.daoPuntuacion().updatePuntuacion(this.appDatabase.daoPuntuacion().findPuntuacion(this.usuarioLoggeado.getCorreo(), ModoJuego.Realiza_Ritmo.toString()).actualizarPuntuacionTotal(puntuacion_fallo, false));
    }

    private void puntuacionHallarRitmo(int nivel, boolean superado) {
        int puntuacion_fallo = 0;
        switch (nivel){
            case 1: puntuacion_fallo = 1; break;
            case 2: puntuacion_fallo = 1; break;
            case 3: puntuacion_fallo = 1; break;
            case 4: puntuacion_fallo = 1; break;
            case 5: puntuacion_fallo = 1; break;
            case 6: puntuacion_fallo = 2; break;
            case 7: puntuacion_fallo = 3; break;
            case 8: puntuacion_fallo = 3; break;
            default: break;
        }


        if(superado)
            this.appDatabase.daoPuntuacion().updatePuntuacion(this.appDatabase.daoPuntuacion().findPuntuacion(this.usuarioLoggeado.getCorreo(), ModoJuego.Halla_Ritmo.toString()).actualizarPuntuacionTotal(3, true));
        else
            this.appDatabase.daoPuntuacion().updatePuntuacion(this.appDatabase.daoPuntuacion().findPuntuacion(this.usuarioLoggeado.getCorreo(), ModoJuego.Halla_Ritmo.toString()).actualizarPuntuacionTotal(puntuacion_fallo, false));
    }

    private void puntuacionImitarAudio(int nivel, boolean superado) {
        int puntuacion_fallo = 0;
        switch (nivel){
            case 1: puntuacion_fallo = 1; break;
            case 2: puntuacion_fallo = 1; break;
            case 3: puntuacion_fallo = 1; break;
            case 4: puntuacion_fallo = 1; break;
            case 5: puntuacion_fallo = 1; break;
            case 6: puntuacion_fallo = 2; break;
            case 7: puntuacion_fallo = 3; break;
            case 8: puntuacion_fallo = 3; break;
            default: break;
        }


        if(superado)
            this.appDatabase.daoPuntuacion().updatePuntuacion(this.appDatabase.daoPuntuacion().findPuntuacion(this.usuarioLoggeado.getCorreo(), ModoJuego.Imitar_Audio.toString()).actualizarPuntuacionTotal(3, true));
        else
            this.appDatabase.daoPuntuacion().updatePuntuacion(this.appDatabase.daoPuntuacion().findPuntuacion(this.usuarioLoggeado.getCorreo(), ModoJuego.Imitar_Audio.toString()).actualizarPuntuacionTotal(puntuacion_fallo, false));
    }

    private void puntuacionCrearAcorde(int nivel, boolean superado) {
        int puntuacion_fallo = 0;
        switch (nivel){
            case 1: puntuacion_fallo = 1; break;
            case 2: puntuacion_fallo = 1; break;
            case 3: puntuacion_fallo = 2; break;
            case 4: puntuacion_fallo = 2; break;
            case 5: puntuacion_fallo = 3; break;
            case 6: puntuacion_fallo = 3; break;
            default: break;
        }

        if(superado)
            this.appDatabase.daoPuntuacion().updatePuntuacion(this.appDatabase.daoPuntuacion().findPuntuacion(this.usuarioLoggeado.getCorreo(), ModoJuego.Crear_Acordes.toString()).actualizarPuntuacionTotal(3, true));
        else
            this.appDatabase.daoPuntuacion().updatePuntuacion(this.appDatabase.daoPuntuacion().findPuntuacion(this.usuarioLoggeado.getCorreo(), ModoJuego.Crear_Acordes.toString()).actualizarPuntuacionTotal(puntuacion_fallo, false));
    }

    private void puntuacionAdivinarAcorde(int nivel, boolean superado) {
        int puntuacion_fallo = 0;
        switch (nivel){
            case 1: puntuacion_fallo = 1; break;
            case 2: puntuacion_fallo = 1; break;
            case 3: puntuacion_fallo = 2; break;
            case 4: puntuacion_fallo = 2; break;
            case 5: puntuacion_fallo = 3; break;
            case 6: puntuacion_fallo = 3; break;
            default: break;
        }

        if(superado)
            this.appDatabase.daoPuntuacion().updatePuntuacion(this.appDatabase.daoPuntuacion().findPuntuacion(this.usuarioLoggeado.getCorreo(), ModoJuego.Adivinar_Acordes.toString()).actualizarPuntuacionTotal(3, true));
        else
            this.appDatabase.daoPuntuacion().updatePuntuacion(this.appDatabase.daoPuntuacion().findPuntuacion(this.usuarioLoggeado.getCorreo(), ModoJuego.Adivinar_Acordes.toString()).actualizarPuntuacionTotal(puntuacion_fallo, false));
    }

    private void puntuacionCrearIntervalo(int nivel, boolean superado) {
        int puntuacion_fallo = 0;
        switch (nivel){
            case 1: puntuacion_fallo = 1; break;
            case 2: puntuacion_fallo = 1; break;
            case 3: puntuacion_fallo = 1; break;
            case 4: puntuacion_fallo = 2; break;
            case 5: puntuacion_fallo = 2; break;
            case 6: puntuacion_fallo = 2; break;
            case 7: puntuacion_fallo = 3; break;
            case 8: puntuacion_fallo = 3; break;
            default: break;
        }

        if(superado)
            this.appDatabase.daoPuntuacion().updatePuntuacion(this.appDatabase.daoPuntuacion().findPuntuacion(this.usuarioLoggeado.getCorreo(), ModoJuego.Crear_Intervalo.toString()).actualizarPuntuacionTotal(3, true));
        else
            this.appDatabase.daoPuntuacion().updatePuntuacion(this.appDatabase.daoPuntuacion().findPuntuacion(this.usuarioLoggeado.getCorreo(), ModoJuego.Crear_Intervalo.toString()).actualizarPuntuacionTotal(puntuacion_fallo, false));
    }

    private void puntuacionAdivinarIntervalo(int nivel, boolean superado) {

        int puntuacion_fallo = 0;
        switch (nivel){
            case 1: puntuacion_fallo = 1; break;
            case 2: puntuacion_fallo = 1; break;
            case 3: puntuacion_fallo = 2; break;
            case 4: puntuacion_fallo = 2; break;
            case 5: puntuacion_fallo = 3; break;
            case 6: puntuacion_fallo = 3; break;
            default: break;
        }

        if(superado)
            this.appDatabase.daoPuntuacion().updatePuntuacion(this.appDatabase.daoPuntuacion().findPuntuacion(this.usuarioLoggeado.getCorreo(), ModoJuego.Adivinar_Intervalo.toString()).actualizarPuntuacionTotal(3, true));
        else
            this.appDatabase.daoPuntuacion().updatePuntuacion(this.appDatabase.daoPuntuacion().findPuntuacion(this.usuarioLoggeado.getCorreo(), ModoJuego.Adivinar_Intervalo.toString()).actualizarPuntuacionTotal(puntuacion_fallo, false));
    }

    private void puntuacionAdivinarNota(int nivel, boolean superado) {
        int puntuacion_fallo = 0;
        switch (nivel){
            case 1: puntuacion_fallo = 1; break;
            case 2: puntuacion_fallo = 1; break;
            case 3: puntuacion_fallo = 1; break;
            case 4: puntuacion_fallo = 1; break;
            case 5: puntuacion_fallo = 2; break;
            case 6: puntuacion_fallo = 2; break;
            case 7: puntuacion_fallo = 2; break;
            case 8: puntuacion_fallo = 2; break;
            case 9: puntuacion_fallo = 3; break;
            case 10: puntuacion_fallo =3; break;
            default: break;
        }

        if(superado)
            this.appDatabase.daoPuntuacion().updatePuntuacion(this.appDatabase.daoPuntuacion().findPuntuacion(this.usuarioLoggeado.getCorreo(), ModoJuego.Adivinar_Notas.toString()).actualizarPuntuacionTotal(3, true));
        else
            this.appDatabase.daoPuntuacion().updatePuntuacion(this.appDatabase.daoPuntuacion().findPuntuacion(this.usuarioLoggeado.getCorreo(), ModoJuego.Adivinar_Notas.toString()).actualizarPuntuacionTotal(puntuacion_fallo, false));

    }

    private void puntuacionModoMix(int nivel, boolean superado) {
        int puntuacion_fallo = 0;
        switch (nivel){
            case 1: puntuacion_fallo = 1; break;
            case 2: puntuacion_fallo = 1; break;
            case 3: puntuacion_fallo = 1; break;
            case 4: puntuacion_fallo = 1; break;
            case 5: puntuacion_fallo = 2; break;
            case 6: puntuacion_fallo = 2; break;
            case 7: puntuacion_fallo = 2; break;
            case 8: puntuacion_fallo = 2; break;
            case 9: puntuacion_fallo = 3; break;
            case 10: puntuacion_fallo =3; break;
            default: break;
        }

        if(superado)
            this.appDatabase.daoPuntuacion().updatePuntuacion(this.appDatabase.daoPuntuacion().findPuntuacion(this.usuarioLoggeado.getCorreo(), ModoJuego.Modo_Mix.toString()).actualizarPuntuacionTotal(3, true));
        else
            this.appDatabase.daoPuntuacion().updatePuntuacion(this.appDatabase.daoPuntuacion().findPuntuacion(this.usuarioLoggeado.getCorreo(), ModoJuego.Modo_Mix.toString()).actualizarPuntuacionTotal(puntuacion_fallo, false));

    }

    public void compruebaUsuarioPrueba() {
        if (appDatabase.daoUsuario().findUsuario("usuario@prueba.com") == null){
            Usuario usuario = new Usuario("usuario@prueba.com", "prueba", "1234", false, true);
            appDatabase.daoUsuario().insertUsuario(usuario);
            for (int i = 0; i < ModoJuego.values().length; i++) {
                Puntuacion p = new Puntuacion(ModoJuego.values()[i].toString(), ModoJuego.values()[i].getMax_level(), usuario.getCorreo(), 400, RangosPuntuaciones.Leyenda.toString(), true);
                appDatabase.daoPuntuacion().insertPuntuacion(p);
            }
        }
    }

    public boolean esPrimerNivelAdivinar(ModoJuego modo_juego, int nivel){
        NivelAdivinar n = appDatabase.daoNivel().findNivelAdivinar(usuarioLoggeado.getCorreo(), modo_juego.getNombre(), nivel);
        return (n == null || (n.getNumFallos() == 0 && n.getNumAciertos() == 0));
    }

    public boolean esPrimerNivelImitar(String rango, int dificultad){
        NivelImitar n = appDatabase.daoNivel().findNivelImitar(usuarioLoggeado.getCorreo(), dificultad, rango);
        return (n == null || n.getNumeroIntentos() == 0);
    }

    public boolean existeUsuario(String correo) {
        return appDatabase.daoUsuario().findUsuario(correo) != null;
    }

    public boolean esPrimeraVezModo(ModoJuego modo){
        Puntuacion p = appDatabase.daoPuntuacion().findPuntuacion(getUsuarioLoggeado().getCorreo(), modo.toString());
        if (p.isIniciado())
            return false;
        return true;
    }

    public void modoRealizado(ModoJuego modo){
        Puntuacion p = appDatabase.daoPuntuacion().findPuntuacion(getUsuarioLoggeado().getCorreo(), modo.toString());
        p.setIniciado(true);
        appDatabase.daoPuntuacion().updatePuntuacion(p);
    }

    public boolean esPrimeraVezApp(){
        Usuario user = appDatabase.daoUsuario().findUsuario(usuarioLoggeado.getCorreo());
        if (user.getPrimeraVez())
            return false;
        user.setPrimeraVez(true);
        appDatabase.daoUsuario().updateUsuario(user);
        return true;
    }


}
