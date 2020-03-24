package com.example.prototipotfg.Singletons;

import android.content.Context;

import com.example.prototipotfg.BBDD.AppDatabase;
import com.example.prototipotfg.BBDD.NivelAdivinar;
import com.example.prototipotfg.BBDD.NivelImitar;
import com.example.prototipotfg.BBDD.Usuario;

import java.util.List;

public class GestorBBDD {
    private static final GestorBBDD ourInstance = new GestorBBDD();
    private Context contexto;
    private Usuario usuarioLoggeado;
    private AppDatabase appDatabase;

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

}
