package app.deputadostalker.usuario.dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.InputStream;

import app.deputadostalker.comissao.dominio.Comissao;
import app.deputadostalker.comissao.dominio.ComissaoDeputado;
import app.deputadostalker.database.DAO;
import app.deputadostalker.deputado.dominio.Deputado;
import app.deputadostalker.gabinete.dominio.Gabinete;
import app.deputadostalker.partido.dominio.Partido;
import app.deputadostalker.usuario.dominio.Usuario;
import app.deputadostalker.util.Session;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

/**
 * Created by Matheus Uehara on 22/05/2016.
 */
public class UsuarioDAO extends DAO {

    private Realm realm;

    private static final UsuarioDAO instance = new UsuarioDAO();

    private UsuarioDAO() {
        super();
    }

    public static UsuarioDAO getInstance(){
        return instance;
    }

    public void init(Context ctx){
        DAO.getDataBaseHelper(ctx);
        realm = Realm.getDefaultInstance();
    }

    public void init(SharedPreferences pref , Context ctx) {
        DAO.getDataBaseHelper(ctx);
        realm = Realm.getDefaultInstance();

        Log.i("LOG", "Iniciou a base de dados via JSON utilizando o Realm");
        pref.edit().putInt("flag", 1).apply();

        try {
            AssetManager assetManager = ctx.getAssets();
            InputStream is;
            realm.beginTransaction();

        /* Gabinete */
            is = assetManager.open("gabinete.json");
            realm.createAllFromJson(Gabinete.class, is);

        /* Partido */
            is = assetManager.open("partido.json");
            realm.createAllFromJson(Partido.class, is);

        /* Comissoes */
            is = assetManager.open("comissoes.json");
            realm.createOrUpdateAllFromJson(Comissao.class, is);

        /* ComissoesDeputado */
            is = assetManager.open("comissoesDeputado.json");
            realm.createAllFromJson(ComissaoDeputado.class, is);

        /* Deputados */
            is = assetManager.open("deputado.json");
            realm.createOrUpdateAllFromJson(Deputado.class, is);

            realm.commitTransaction();

        } catch (Exception e) {
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }

    public boolean insereUsuario(Usuario usuario){
        try{
            realm.beginTransaction();
            Usuario user = realm.createObject(Usuario.class);
            user.setNome(usuario.getNome());
            user.setEmail(usuario.getEmail());
            user.setId(usuario.getId());
            user.setProfileUrl(usuario.getProfileUrl());
            realm.commitTransaction();
            return true;
        }catch (RealmPrimaryKeyConstraintException e ){
            return false;
        }
    }

    public Usuario getUsuario(String idUsuario){
        RealmResults<Usuario> users = realm.where(Usuario.class).equalTo("id",idUsuario).findAll();
        if (users.size() > 0){
            Session.setUsuarioLogado(users.get(0));
            return users.get(0);
        }else{
            return new Usuario();
        }
    }

}
