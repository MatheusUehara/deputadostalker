package app.deputadostalker.usuario.service;

import android.content.Context;
import android.content.SharedPreferences;

import app.deputadostalker.usuario.dao.UsuarioDAO;
import app.deputadostalker.usuario.dominio.Usuario;
import io.realm.Realm;

/**
 * Created by Matheus Uehara on 07/03/2016.
 */
public class UsuarioService {

    private static final UsuarioService instance = new UsuarioService();

    UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();

    private UsuarioService() {
        super();
    }

    public static UsuarioService getInstance() {
        return instance;
    }

    public void iniciaBase(SharedPreferences pref , Context ctx){
        usuarioDAO.init(pref,ctx);
    }

    public boolean insereUsuario(Usuario usuario){
        return usuarioDAO.insereUsuario(usuario);
    }

    public void iniciaBase(Context ctx){
        usuarioDAO.init(ctx);
    }

    public Usuario getUsuario(String idUsuario){
        return usuarioDAO.getUsuario(idUsuario);
    }
}
