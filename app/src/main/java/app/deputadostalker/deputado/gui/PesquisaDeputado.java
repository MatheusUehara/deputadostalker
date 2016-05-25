package app.deputadostalker.deputado.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import app.deputadostalker.R;
import app.deputadostalker.usuario.gui.MainActivity;
import co.moonmonkeylabs.realmsearchview.RealmSearchView;
import io.realm.Realm;

public class PesquisaDeputado extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Realm realm;
    Toolbar toolbar;
    DeputadoRecyclerViewAdapter adapter;


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

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PesquisaDeputado.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });


        Spinner spinner = (Spinner) findViewById(R.id.spinner_filtro);

        spinner.setOnItemSelectedListener(this);

        List<String> filtros = new ArrayList <String>();
        filtros.add("NOME");
        filtros.add("PARTIDO");
        filtros.add("ESTADO");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter <String>(this, android.R.layout.simple_spinner_item, filtros);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);

        RealmSearchView realmSearchView = (RealmSearchView) findViewById(R.id.busca);

        realm = Realm.getDefaultInstance();
        adapter = new DeputadoRecyclerViewAdapter(this, realm, "nomeParlamentar");
        realmSearchView.setAdapter(adapter);


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
            realm = null;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                adapter.setFilterKey("nomeParlamentar");
                break;
            case 1:
                adapter.setFilterKey("partido_idPartido");
                break;
            case 2:
                adapter.setFilterKey("ufRepresentacaoAtual");
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}