package app.deputadostalker.usuario.gui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import app.deputadostalker.R;
import app.deputadostalker.deputado.api.DeputadoAPI;
import app.deputadostalker.deputado.api.DeputadoDes;
import app.deputadostalker.deputado.dominio.Deputado;
import app.deputadostalker.deputado.gui.DeputadoFavoritoActivity;
import app.deputadostalker.deputado.gui.PerfilDeputado;
import app.deputadostalker.deputado.gui.PesquisaDeputado;
import app.deputadostalker.usuario.service.CircularNetworkImageView;
import app.deputadostalker.usuario.service.CustomVolleyRequest;
import app.deputadostalker.util.Session;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public void onBackPressed() {
        logout();
    }

    Toolbar toolbar;

    String NAME;
    String EMAIL;
    String USER_IMAGE;

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    DrawerLayout drawer;

    ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(MainActivity.this);
        setContentView(R.layout.activity_main);

        FloatingActionButton pesquisa = (FloatingActionButton) findViewById(R.id.pesquisa_deputado);
        pesquisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,PesquisaDeputado.class);
                startActivity(i);
            }
        });


        FloatingActionButton favorito = (FloatingActionButton) findViewById(R.id.deputado_favorito);
        favorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,DeputadoFavoritoActivity.class);
                startActivity(i);
            }
        });


        favorito.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, DeputadoFavoritoActivity.class);
                startActivity(i);
            }
        });

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        davDrawer();
        if (Session.getDeputadosMaisPesquisados().size() == 0) {
            getMaisBuscados();
        }else {
            preencheImagens();
        }
    }


    public void davDrawer() {
        EMAIL = Session.getUsuarioLogado().getEmail();
        NAME = Session.getUsuarioLogado().getNome();
        USER_IMAGE = Session.getUsuarioLogado().getProfileUrl();

        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new DrawerAdapter(NAME, EMAIL, USER_IMAGE, this);
        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);

        mDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        drawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        final GestureDetector mGestureDetector = new GestureDetector(MainActivity.this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    drawer.closeDrawers();
                    int position = recyclerView.getChildAdapterPosition(child);

                    if (position == 1) {
                        Toast.makeText(MainActivity.this, "Clicou no PERFIL", Toast.LENGTH_SHORT).show();
                    } else if (position == 2) {
                        Toast.makeText(MainActivity.this, "Clicou em CONFIGURAÇÕES", Toast.LENGTH_SHORT).show();
                    } else if (position == 3) {
                        logout();
                    }
                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    public void logout() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setTitle("Sair");
        alertDialogBuilder.setMessage("Tem certeza que deseja sair?");
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences pref = getSharedPreferences("login", Context.MODE_PRIVATE);
                if (pref.getBoolean("signed_in", false)) {
                    LoginManager.getInstance().logOut();
                    SharedPreferences.Editor editor = pref.edit();
                    editor.clear();
                    editor.commit();

                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                }
            }
        });
        alertDialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialogBuilder.show();
    }


    private void getMaisBuscados(){
        Gson gson = new GsonBuilder().registerTypeAdapter(Deputado.class, new DeputadoDes()).create();
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(getString(R.string.urlBase))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        final DeputadoAPI deputadoAPI = retrofit.create(DeputadoAPI.class);

        final Call<List<Deputado>> callDeputado = deputadoAPI.maisBuscados();
        callDeputado.enqueue(new Callback<List<Deputado>>() {
            @Override
            public void onResponse(Call<List<Deputado>> call, Response<List<Deputado>> response) {
                List<Deputado> listDeputados = response.body();
                if( listDeputados != null ){
                    ArrayList<Deputado> deputadosMaisPesquisados = new ArrayList<Deputado>();
                    for( Deputado d : listDeputados ){
                        deputadosMaisPesquisados.add(d);
                    }
                    Session.setDeputadosMaisPesquisados(deputadosMaisPesquisados);
                    preencheImagens();
                }else{
                    Toast.makeText(MainActivity.this, R.string.erro_conectividade, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Deputado>> call, Throwable t) {

            }
        });
    }

    public void preencheImagens(){

        ImageLoader imageLoader;

        CircularNetworkImageView fotoDeputado0 = (CircularNetworkImageView) findViewById(R.id.img_deputado_0);
        imageLoader = CustomVolleyRequest.getInstance(MainActivity.this).getImageLoader();
        imageLoader.get(Session.getDeputadosMaisPesquisados().get(0).getUrlFoto(),
                ImageLoader.getImageListener(fotoDeputado0,
                        R.mipmap.ic_launcher,
                        R.mipmap.ic_launcher));
        fotoDeputado0.setImageUrl(Session.getDeputadosMaisPesquisados().get(0).getUrlFoto(), imageLoader);


        CircularNetworkImageView fotoDeputado1 = (CircularNetworkImageView) findViewById(R.id.img_deputado_1);
        imageLoader = CustomVolleyRequest.getInstance(MainActivity.this).getImageLoader();
        imageLoader.get(Session.getDeputadosMaisPesquisados().get(1).getUrlFoto(),
                ImageLoader.getImageListener(fotoDeputado1,
                        R.mipmap.ic_launcher,
                        R.mipmap.ic_launcher));
        fotoDeputado1.setImageUrl(Session.getDeputadosMaisPesquisados().get(1).getUrlFoto(), imageLoader);


        CircularNetworkImageView fotoDeputado2 = (CircularNetworkImageView) findViewById(R.id.img_deputado_2);
        imageLoader = CustomVolleyRequest.getInstance(MainActivity.this).getImageLoader();
        imageLoader.get(Session.getDeputadosMaisPesquisados().get(2).getUrlFoto(),
                ImageLoader.getImageListener(fotoDeputado2,
                        R.mipmap.ic_launcher,
                        R.mipmap.ic_launcher));
        fotoDeputado2.setImageUrl(Session.getDeputadosMaisPesquisados().get(2).getUrlFoto(), imageLoader);

        CircularNetworkImageView fotoDeputado3 = (CircularNetworkImageView) findViewById(R.id.img_deputado_3);
        imageLoader = CustomVolleyRequest.getInstance(MainActivity.this).getImageLoader();
        imageLoader.get(Session.getDeputadosMaisPesquisados().get(3).getUrlFoto(),
                ImageLoader.getImageListener(fotoDeputado3,
                        R.mipmap.ic_launcher,
                        R.mipmap.ic_launcher));
        fotoDeputado3.setImageUrl(Session.getDeputadosMaisPesquisados().get(3).getUrlFoto(), imageLoader);


        CircularNetworkImageView fotoDeputado4 = (CircularNetworkImageView) findViewById(R.id.img_deputado_4);
        imageLoader = CustomVolleyRequest.getInstance(MainActivity.this).getImageLoader();
        imageLoader.get(Session.getDeputadosMaisPesquisados().get(4).getUrlFoto(),
                ImageLoader.getImageListener(fotoDeputado4,
                        R.mipmap.ic_launcher,
                        R.mipmap.ic_launcher));
        fotoDeputado4.setImageUrl(Session.getDeputadosMaisPesquisados().get(4).getUrlFoto(), imageLoader);


        CircularNetworkImageView fotoDeputado5 = (CircularNetworkImageView) findViewById(R.id.img_deputado_5);
        imageLoader = CustomVolleyRequest.getInstance(MainActivity.this).getImageLoader();
        imageLoader.get(Session.getDeputadosMaisPesquisados().get(5).getUrlFoto(),
                ImageLoader.getImageListener(fotoDeputado5,
                        R.mipmap.ic_launcher,
                        R.mipmap.ic_launcher));
        fotoDeputado5.setImageUrl(Session.getDeputadosMaisPesquisados().get(5).getUrlFoto(), imageLoader);


        CircularNetworkImageView fotoDeputado6 = (CircularNetworkImageView) findViewById(R.id.img_deputado_6);
        imageLoader = CustomVolleyRequest.getInstance(MainActivity.this).getImageLoader();
        imageLoader.get(Session.getDeputadosMaisPesquisados().get(6).getUrlFoto(),
                ImageLoader.getImageListener(fotoDeputado6,
                        R.mipmap.ic_launcher,
                        R.mipmap.ic_launcher));
        fotoDeputado6.setImageUrl(Session.getDeputadosMaisPesquisados().get(6).getUrlFoto(), imageLoader);


        CircularNetworkImageView fotoDeputado7 = (CircularNetworkImageView) findViewById(R.id.img_deputado_7);
        imageLoader = CustomVolleyRequest.getInstance(MainActivity.this).getImageLoader();
        imageLoader.get(Session.getDeputadosMaisPesquisados().get(7).getUrlFoto(),
                ImageLoader.getImageListener(fotoDeputado7,
                        R.mipmap.ic_launcher,
                        R.mipmap.ic_launcher));
        fotoDeputado7.setImageUrl(Session.getDeputadosMaisPesquisados().get(7).getUrlFoto(), imageLoader);


        CircularNetworkImageView fotoDeputado8 = (CircularNetworkImageView) findViewById(R.id.img_deputado_8);
        imageLoader = CustomVolleyRequest.getInstance(MainActivity.this).getImageLoader();
        imageLoader.get(Session.getDeputadosMaisPesquisados().get(8).getUrlFoto(),
                ImageLoader.getImageListener(fotoDeputado8,
                        R.mipmap.ic_launcher,
                        R.mipmap.ic_launcher));
        fotoDeputado8.setImageUrl(Session.getDeputadosMaisPesquisados().get(8).getUrlFoto(), imageLoader);


        CircularNetworkImageView fotoDeputado9 = (CircularNetworkImageView) findViewById(R.id.img_deputado_9);
        imageLoader = CustomVolleyRequest.getInstance(MainActivity.this).getImageLoader();
        imageLoader.get(Session.getDeputadosMaisPesquisados().get(9).getUrlFoto(),
                ImageLoader.getImageListener(fotoDeputado9,
                        R.mipmap.ic_launcher,
                        R.mipmap.ic_launcher));
        fotoDeputado9.setImageUrl(Session.getDeputadosMaisPesquisados().get(9).getUrlFoto(), imageLoader);


        CircularNetworkImageView fotoDeputado10 = (CircularNetworkImageView) findViewById(R.id.img_deputado_10);
        imageLoader = CustomVolleyRequest.getInstance(MainActivity.this).getImageLoader();
        imageLoader.get(Session.getDeputadosMaisPesquisados().get(10).getUrlFoto(),
                ImageLoader.getImageListener(fotoDeputado10,
                        R.mipmap.ic_launcher,
                        R.mipmap.ic_launcher));
        fotoDeputado10.setImageUrl(Session.getDeputadosMaisPesquisados().get(10).getUrlFoto(), imageLoader);


        CircularNetworkImageView fotoDeputado11 = (CircularNetworkImageView) findViewById(R.id.img_deputado_11);
        imageLoader = CustomVolleyRequest.getInstance(MainActivity.this).getImageLoader();
        imageLoader.get(Session.getDeputadosMaisPesquisados().get(11).getUrlFoto(),
                ImageLoader.getImageListener(fotoDeputado11,
                        R.mipmap.ic_launcher,
                        R.mipmap.ic_launcher));
        fotoDeputado11.setImageUrl(Session.getDeputadosMaisPesquisados().get(11).getUrlFoto(), imageLoader);

        fotoDeputado0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDeputado(0);
            }
        });
        fotoDeputado1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDeputado(1);
            }
        });
        fotoDeputado2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDeputado(2);
            }
        });
        fotoDeputado3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDeputado(3);
            }
        });
        fotoDeputado4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDeputado(4);
            }
        });
        fotoDeputado5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDeputado(5);
            }
        });
        fotoDeputado6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDeputado(6);
            }
        });
        fotoDeputado7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDeputado(7);
            }
        });
        fotoDeputado8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDeputado(8);
            }
        });
        fotoDeputado9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDeputado(9);
            }
        });
        fotoDeputado10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDeputado(10);
            }
        });
        fotoDeputado11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDeputado(11);
            }
        });
    }

    public void onClickDeputado(int index ){
        if (Session.getDeputadosMaisPesquisados().size() > 0) {
            Intent i = new Intent(MainActivity.this, PerfilDeputado.class);
            i.putExtra("ideCadastro", Session.getDeputadosMaisPesquisados().get(index).getIdeCadastro());
            startActivity(i);
        }else{
            Toast.makeText(MainActivity.this, R.string.erro_conectividade_deputado_pesquisado, Toast.LENGTH_SHORT).show();
        }
    }

}