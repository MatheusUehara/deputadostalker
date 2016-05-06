package app.deputadostalker.usuario.gui;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import app.deputadostalker.R;
import app.deputadostalker.usuario.dominio.Usuario;
import app.deputadostalker.usuario.service.FacebookSign;
import app.deputadostalker.usuario.service.GoogleSign;
import app.deputadostalker.util.Session;

public class LoginActivity extends AppCompatActivity implements GoogleSign.InfoLoginGoogleCallback, FacebookSign.InfoLoginFaceCallback {


    GoogleSign googleSign;
    FacebookSign facebookSign;

    SignInButton signInButton;
    LoginButton loginButton;

    private void bindViews() {
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSign.signIn();
            }
        });
        signInButton.setColorScheme(SignInButton.COLOR_AUTO);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        facebookSign.signInWithFaceButton(loginButton);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(LoginActivity.this);
        setContentView(R.layout.activity_login);
        final ImageView image = (ImageView) findViewById(R.id.logo);
        image.setImageResource(R.mipmap.ic_launcher);

        this.isFacebookKeyGenerated();

        googleSign = new GoogleSign(this, this);

        facebookSign = new FacebookSign(this, this);

        bindViews();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        googleSign.resultGoogleLogin(requestCode, resultCode, data);
        facebookSign.resultFaceLogin(requestCode, resultCode, data);
    }

    @Override
    public void getInfoLoginGoogle(GoogleSignInAccount account) {
        Usuario user = new Usuario();
        user.setName(account.getDisplayName());
        user.setEmail(account.getEmail());
        user.setId(account.getId());
        user.setImageUrl(account.getPhotoUrl().toString());
        Session.setUsuarioLogado(user);

        SharedPreferences sharedPref = getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("signed_in_with_google", true);
        editor.putString("user_google_id", account.getId());
        editor.putString("user_google_name", account.getDisplayName());
        editor.putString("user_google_image_url", account.getPhotoUrl().toString());
        editor.putString("user_google_email", account.getEmail());
        editor.commit();

        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public void connectionFailedApiClient(ConnectionResult connectionResult) {
        Toast.makeText(LoginActivity.this, "Falha na conexao com api" + connectionResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginFailed() {
        Toast.makeText(LoginActivity.this, "Falha no login", Toast.LENGTH_SHORT).show();
    }

    public void isFacebookKeyGenerated() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "app.deputadostalker",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getInfoFace(String id, String name, String email, String photo) {
        Usuario user = new Usuario();
        user.setName(name);
        user.setEmail(email);
        user.setId(id);
        user.setImageUrl(photo);
        Session.setUsuarioLogado(user);

        SharedPreferences sharedPref = getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("signed_in_with_facebook", true);
        editor.putString("user_facebook_id", id);
        editor.putString("user_facebook_name", name);
        editor.putString("user_facebook_image_url", photo);
        editor.putString("user_facebook_email", email);
        editor.commit();

        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public void cancelLoginFace() {
        Toast.makeText(LoginActivity.this, "Login facebook cancelado", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void erroLoginFace(FacebookException e) {
        Toast.makeText(LoginActivity.this, "Falha no login com facebook", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        googleSign.mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();

        if (googleSign.mGoogleApiClient != null && googleSign.mGoogleApiClient.isConnected()) {
            googleSign.mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}

