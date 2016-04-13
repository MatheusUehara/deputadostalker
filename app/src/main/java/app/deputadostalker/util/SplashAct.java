package app.deputadostalker.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import app.deputadostalker.R;
import app.deputadostalker.usuario.dominio.Usuario;
import app.deputadostalker.usuario.gui.LoginActivity;
import app.deputadostalker.usuario.gui.MainActivity;

/**
 * Created by Uehara on 16/07/2015.
 */
public class SplashAct extends Activity implements Runnable{


    private Handler handler;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final ImageView image = (ImageView) findViewById(R.id.imageView);
        image.setImageResource(R.mipmap.ic_launcher);
        handler = new Handler();
        handler.postDelayed(this, 2000);
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
