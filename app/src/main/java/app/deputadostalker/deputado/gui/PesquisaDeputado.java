package app.deputadostalker.deputado.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;

import app.deputadostalker.R;
import app.deputadostalker.deputado.dominio.Deputado;
import app.deputadostalker.usuario.gui.MainActivity;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class PesquisaDeputado extends AppCompatActivity{

    private Realm realm;
    private RealmResults<Deputado> deputados;
    private RealmChangeListener realmChangeListener;
    private ListView lvDeputados;
    Toolbar toolbar;

    SearchView busca;


    @Override
    public void onBackPressed() {
        Intent i = new Intent(PesquisaDeputado.this, MainActivity.class);
        startActivity(i);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa_deputado);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);

        busca = (SearchView) findViewById(R.id.busca);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PesquisaDeputado.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        realm = Realm.getDefaultInstance();

        realmChangeListener = new RealmChangeListener() {
            @Override
            public void onChange() {
                ((DeputadoAdapter) lvDeputados.getAdapter()).notifyDataSetChanged();
            }
        };

        realm.addChangeListener(realmChangeListener);
        deputados = realm.where(Deputado.class)
                .contains("nomeCivil",busca.getQuery().toString())
                .or()
                .contains("nomeParlamentar",busca.getQuery().toString())
                .or()
                .contains("partido_idPartido",busca.getQuery().toString())
                .or()
                .contains("ufRepresentacaoAtual",busca.getQuery().toString())
                .findAll();
        lvDeputados = (ListView) findViewById(R.id.listaDeputados);
        lvDeputados.setAdapter(new DeputadoAdapter(this, realm, deputados, false));
        setupSearchView();
    }

    private void setupSearchView() {
        busca.setIconifiedByDefault(false);
        busca.setSubmitButtonEnabled(true);
        busca.setQueryHint("Pesquise por: Nome, Partido ou Estado");
    }

    public boolean onQueryTextSubmit(String query) {
        deputados = realm.where(Deputado.class)
                .contains("nomeCivil",busca.getQuery().toString())
                .or()
                .contains("nomeParlamentar",busca.getQuery().toString())
                .or()
                .contains("partido_idPartido",busca.getQuery().toString())
                .or()
                .contains("ufRepresentacaoAtual",busca.getQuery().toString())
                .findAll();
        lvDeputados.setAdapter(new DeputadoAdapter(this, realm, deputados, false));
        return false;
    }

    @Override
    protected void onDestroy() {
        realm.removeAllChangeListeners();
        realm.close();
        super.onDestroy();

    }

}