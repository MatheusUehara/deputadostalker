package app.deputadostalker.usuario.gui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.io.InputStream;

import app.deputadostalker.R;
import app.deputadostalker.comissoes.dominio.Comissoes;
import app.deputadostalker.comissoes.dominio.ComissoesDeputado;
import app.deputadostalker.deputado.dominio.Deputado;
import app.deputadostalker.gabinete.dominio.Gabinete;
import app.deputadostalker.partido.dominio.Partido;
import app.deputadostalker.usuario.dominio.Usuario;
import app.deputadostalker.util.Session;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;


/**
 * Created by Uehara on 16/07/2015.
 */
public class SplashAct extends Activity{

    private Realm realm;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name("deputado-stalker.realm")
                .build();
        Realm.setDefaultConfiguration( realmConfiguration );

        realm = Realm.getDefaultInstance();

        pref = getSharedPreferences("login",Context.MODE_PRIVATE);

        if (pref.getInt("flag",0) == 0) {
            init();
        }
        checkLogin();
    }

    private void init() {
        Log.i("LOG", "Iniciou a base de dados via JSON utilizando o Realm");
        pref.edit().putInt("flag", 1).apply();

        try {
            AssetManager assetManager = getAssets();
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
            realm.createOrUpdateAllFromJson(Comissoes.class, is);

        /* ComissoesDeputado */
            is = assetManager.open("comissoesDeputado.json");
            realm.createAllFromJson(ComissoesDeputado.class, is);

        /* Deputados */
            is = assetManager.open("deputado.json");
            realm.createOrUpdateAllFromJson(Deputado.class, is);

            realm.commitTransaction();

        } catch (Exception e) {
            e.printStackTrace();
            realm.cancelTransaction();
        }
    }


    public void checkLogin() {
        boolean signedIn = pref.getBoolean("signed_in", false);

        if (signedIn) {
            String id = pref.getString("user_id", "");
            RealmResults<Usuario> users = realm.where(Usuario.class).equalTo("id",id).findAll();
            Session.setUsuarioLogado(users.get(0));
            Intent it = new Intent(SplashAct.this, MainActivity.class);
            startActivity(it);
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            Intent it = new Intent(SplashAct.this, LoginActivity.class);
            startActivity(it);
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }
}
