package app.deputadostalker.usuario.gui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.location.Address;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;

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
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;


/**
 * Created by Uehara on 16/07/2015.
 */
public class SplashAct extends Activity implements Runnable{

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler = new Handler();
        handler.postDelayed(this, 1000);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfiguration);

    }


    public RealmResults<Deputado> busca(String nome ){
        Realm realm = Realm.getDefaultInstance();
        init(realm);

        RealmResults<Deputado> deputados = realm.where(Deputado.class)
                .contains("nomeParlamentar",nome)
                .or()
                .contains("nomeCivil", nome)
                .findAll();

//        for (Deputado d : deputados) {
//            Log.i("LOG", "Id do deputado: " + d.getIdParlamentar());
//            Log.i("LOG", "Nome: " + d.getNomeParlamentar());
//            Log.i("LOG", "Estado que representa: " + d.getUfRepresentacaoAtual());
//        }

        realm.close();
        return deputados;
    };


    private void init(Realm realm) {
        SharedPreferences pref = getPreferences(MODE_PRIVATE);

        if (pref.getInt("flag", 0) == 0) {
            Log.i("LOG", "init()");
            pref.edit().putInt("flag", 1).apply();

            try {
                AssetManager assetManager = getAssets();
                InputStream is = null;

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
                realm.createAllFromJson( ComissoesDeputado.class, is );

            /* Deputados */
                is = assetManager.open("deputado.json");
                realm.createOrUpdateAllFromJson( Deputado.class, is );


                realm.commitTransaction();

            } catch (Exception e) {
                e.printStackTrace();
                realm.cancelTransaction();
            }
        } else {
            Log.i("LOG", "Caiu no else");

        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(this);
    }


    @Override
    public void run() {
        SharedPreferences pref = getSharedPreferences("login", Context.MODE_PRIVATE);
        boolean signedGoogle = pref.getBoolean("signed_in_with_google", false);
        boolean signedFacebook = pref.getBoolean("signed_in_with_facebook", false);

        if (signedFacebook){
            Usuario user = new Usuario();
            user.setName(pref.getString("user_facebook_name",""));
            user.setEmail(pref.getString("user_facebook_email",""));
            user.setId(pref.getString("user_facebook_id",""));
            user.setImageUrl(pref.getString("user_facebook_image_url",""));
            Session.setUsuarioLogado(user);

            Intent it = new Intent(SplashAct.this, MainActivity.class);
            startActivity(it);

            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }else if (signedGoogle){
            Usuario user = new Usuario();
            user.setName(pref.getString("user_google_name",""));
            user.setEmail(pref.getString("user_google_email",""));
            user.setId(pref.getString("user_google_id",""));
            user.setImageUrl(pref.getString("user_google_image_url",""));
            Session.setUsuarioLogado(user);

            Intent it = new Intent(SplashAct.this, MainActivity.class);
            startActivity(it);

            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }else {
            Intent it = new Intent(SplashAct.this, LoginActivity.class);
            startActivity(it);
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }
}
