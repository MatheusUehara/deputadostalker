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
import android.widget.Toast;

import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import app.deputadostalker.R;
import app.deputadostalker.usuario.api.UsuarioAPI;
import app.deputadostalker.usuario.api.UsuarioDes;
import app.deputadostalker.usuario.dominio.Usuario;
import app.deputadostalker.usuario.service.FacebookSign;
import app.deputadostalker.usuario.service.GoogleSign;
import app.deputadostalker.usuario.service.UsuarioService;
import app.deputadostalker.util.Session;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity implements GoogleSign.InfoLoginGoogleCallback, FacebookSign.InfoLoginFaceCallback {

    GoogleSign googleSign;
    FacebookSign facebookSign;

    SignInButton signInButton;
    LoginButton loginButton;

    UsuarioService service = UsuarioService.getInstance();

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
        Usuario verificaCadastrado = service.getUsuario(account.getId());
        if (verificaCadastrado.getNome() != null ){
            Session.setUsuarioLogado(verificaCadastrado);
            goMainActivity(verificaCadastrado.getId());
        }else {
            Usuario user = new Usuario();
            user.setNome(account.getDisplayName());
            user.setEmail(account.getEmail());
            user.setId(account.getId());
            user.setRedeSocial("GOOGLE+");
            user.setProfileUrl(account.getPhotoUrl().toString());

            if (service.insereUsuario(user)){
                addUsuarioServidor(user);
                Session.setUsuarioLogado(user);
                goMainActivity(user.getId());
            }else{
                Toast.makeText(this,"Falha no Cadastro",Toast.LENGTH_SHORT).show();
            }
        }
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
            PackageInfo info = getPackageManager().getPackageInfo("app.deputadostalker",PackageManager.GET_SIGNATURES);
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
        Usuario verificaCadastrado = service.getUsuario(id);
        if (verificaCadastrado.getNome() != null ){
            Session.setUsuarioLogado(verificaCadastrado);
            goMainActivity(verificaCadastrado.getId());
        }else {
            Usuario user = new Usuario();
            user.setNome(name);
            user.setEmail(email);
            user.setRedeSocial("FACEBOOK");
            user.setId(id);
            user.setProfileUrl(photo);

            if (service.insereUsuario(user)){
                addUsuarioServidor(user);
                Session.setUsuarioLogado(user);
                goMainActivity(user.getId());
            }else{
                Toast.makeText(this,"Falha no Cadastro",Toast.LENGTH_SHORT).show();
            }
        }
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

    public void addUsuarioServidor(Usuario usuario){

        Gson gson = new GsonBuilder().registerTypeAdapter(Usuario.class, new UsuarioDes()).create();
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(getString(R.string.urlBase))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        final UsuarioAPI usuarioAPI = retrofit.create(UsuarioAPI.class);

        final Call<Usuario> callUsuario = usuarioAPI.cadastraUsuario(usuario.getId(),
                usuario.getNome(),
                usuario.getProfileUrl(),
                usuario.getEmail(),
                usuario.getRedeSocial(),
                "");
        callUsuario.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if( response.code() == 201 ){
                    Log.d("ADICIONOU USUARIO", "NO SERVIDOR");
                }else{
                    Log.d("NÃO ADICIONOU USUARIO", "NO SERVIDOR");
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.d("Sem conexão c internet", "");
            }
        });
    }

    public void goMainActivity(String userId){
        SharedPreferences sharedPref = getSharedPreferences("login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("signed_in", true);
        editor.putString("user_id", userId);
        editor.commit();

        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
    }
}

