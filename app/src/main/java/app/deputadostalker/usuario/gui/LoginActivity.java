package app.deputadostalker.usuario.gui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import app.deputadostalker.util.Session;
import app.deputadostalker.usuario.dominio.Usuario;
import app.deputadostalker.usuario.service.FacebookSign;
import app.deputadostalker.usuario.service.GoogleSign;
import app.deputadostalker.usuario.service.UsuarioService;
import app.deputadostalker.util.Constants;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleSign.InfoLoginGoogleCallback, FacebookSign.InfoLoginFaceCallback {


    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private Button mEmailSignInButton;
    private TextView txt_create, txt_forgot;

    GoogleSign googleSign;
    FacebookSign facebookSign;

    SignInButton signInButton;
    LoginButton loginButton;

    private void bindViews(){
        signInButton = (SignInButton)findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSign.signIn();
            }
        });
        signInButton.setColorScheme(SignInButton.COLOR_AUTO);
        loginButton = (LoginButton)findViewById(R.id.login_button);
        facebookSign.signInWithFaceButton(loginButton);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(LoginActivity.this);
        setContentView(R.layout.activity_login);

        final ImageView image = (ImageView) findViewById(R.id.logo);
        image.setImageResource(R.mipmap.ic_logo);

        this.isFacebookKeyGenerated();
        initInstances();

        googleSign      = new GoogleSign(this,this);

        facebookSign    = new FacebookSign(this,this);

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

        Intent i = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(i);
    }

    @Override
    public void connectionFailedApiClient(ConnectionResult connectionResult) {
        Toast.makeText(LoginActivity.this, R.string.google_api_connection_fail + connectionResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginFailed() {
        Toast.makeText(LoginActivity.this, R.string.google_login_fail, Toast.LENGTH_SHORT).show();
    }

    public void isFacebookKeyGenerated(){
        /**
         * Existe um problema na geração da chave do facebook, que uma vez cadastrada
         * a autenticação não deve cadastrar outra, é necessário usar a que foi usada FUREVER
         */
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "app.cambio",
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

        Intent i = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(i);
    }

    @Override
    public void cancelLoginFace() {
        Toast.makeText(LoginActivity.this, R.string.facebook_login_cancel, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void erroLoginFace(FacebookException e) {
        Toast.makeText(LoginActivity.this, R.string.facebook_login_fail, Toast.LENGTH_SHORT).show();
    }

    private void initInstances() {
        mEmailView = (AutoCompleteTextView) findViewById(R.id.txt_email);
        mPasswordView = (EditText) findViewById(R.id.txt_password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        txt_create = (TextView) findViewById(R.id.txt_create);
        txt_create.setOnClickListener(this);

        txt_forgot = (TextView) findViewById(R.id.txt_forgot);
        txt_forgot.setOnClickListener(this);

        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(this);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    @Override
    public void onClick(View v) {
        String email = mEmailView.getText().toString();

        switch (v.getId()) {
            case R.id.email_sign_in_button:
                attemptLogin();
                if (mEmailView.getError() == null && mPasswordView.getError() == null){
                    Intent i = new Intent (LoginActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
                break;
            case R.id.txt_create:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                intent.putExtra(Constants.TAG_EMAIL, email);
                startActivity(intent);
                finish();
                break;
            case R.id.txt_forgot:
                Intent intentForgot = new Intent(LoginActivity.this, ForgotPassActivity.class);
                intentForgot.putExtra(Constants.TAG_EMAIL, email);
                startActivity(intentForgot);
                finish();
                break;
        }
    }

    private void attemptLogin() {
        mEmailView.setError(null);
        mPasswordView.setError(null);

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        UsuarioService validateUserInfo = UsuarioService.getInstance();

        if (!TextUtils.isEmpty(password) && !validateUserInfo.isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!validateUserInfo.isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        }
    }


    @Override
    public void onStart(){
        super.onStart();
        googleSign.mGoogleApiClient.connect();
    }

    @Override
    public void onStop(){
        super.onStop();

        if(googleSign.mGoogleApiClient != null && googleSign.mGoogleApiClient.isConnected()){
            googleSign.mGoogleApiClient.disconnect();
        }
    }

}

