package app.deputadostalker.util;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;

import app.deputadostalker.R;
import app.deputadostalker.usuario.dominio.Usuario;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Matheus Uehara on 07/03/2016.
 */
public class Session extends Application {

    public static Usuario usuarioLogado;

    //TODO retirar o ide da sessao e passar via intent;
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