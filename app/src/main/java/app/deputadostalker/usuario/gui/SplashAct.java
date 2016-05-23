package app.deputadostalker.usuario.gui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import app.deputadostalker.R;
import app.deputadostalker.usuario.dominio.Usuario;
import app.deputadostalker.usuario.service.UsuarioService;



/**
 * Created by Uehara on 16/07/2015.
 */
public class SplashAct extends AppCompatActivity{

    UsuarioService service = UsuarioService.getInstance();
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        pref = getSharedPreferences("login",Context.MODE_PRIVATE);

        if (pref.getInt("flag",0) == 0) {
            service.iniciaBase(pref , getApplicationContext());
        }else{
            service.iniciaBase(getApplicationContext());
        }
        checkLogin();
    }



    public void checkLogin() {

        boolean signedIn = pref.getBoolean("signed_in", false);

        if (signedIn) {
            String id = pref.getString("user_id", "");
            Usuario usuarioLogado = service.getUsuario(id);
            if (usuarioLogado.getNome() != null ){
                goActivity(new MainActivity());
            }else{
                goActivity(new LoginActivity());
            }
        } else {
            goActivity(new LoginActivity());
        }
    }

    public void goActivity(Activity act){
        Intent it = new Intent(SplashAct.this, act.getClass());
        startActivity(it);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}
