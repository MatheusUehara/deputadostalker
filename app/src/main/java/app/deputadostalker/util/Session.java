package app.deputadostalker.util;

import android.content.Context;

import app.deputadostalker.usuario.dominio.Usuario;

/**
 * Created by Matheus Uehara on 07/03/2016.
 */
public class Session {

    public static Usuario usuarioLogado;

    public static String ideCadastroDeputado;

    private static Context contexto = null;

    public static String getIdeCadastroDeputado() {
        return ideCadastroDeputado;
    }

    public static void setIdeCadastroDeputado(String ideCadastroDeputado) {
        Session.ideCadastroDeputado = ideCadastroDeputado;
    }

    public static void setUsuarioLogado(Usuario usuario) {
        usuarioLogado = usuario;
    }

    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public static void setContexto(Context context) {
        contexto = context;
    }

    public static Context getContexto() {
        return contexto;
    }
}