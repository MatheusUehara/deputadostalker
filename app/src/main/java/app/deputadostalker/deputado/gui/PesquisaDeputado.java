package app.deputadostalker.deputado.gui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.deputadostalker.R;
import app.deputadostalker.deputado.dominio.Deputado;
import app.deputadostalker.usuario.gui.MainActivity;
import app.deputadostalker.util.Session;
import co.moonmonkeylabs.realmsearchview.RealmSearchAdapter;
import co.moonmonkeylabs.realmsearchview.RealmSearchView;
import co.moonmonkeylabs.realmsearchview.RealmSearchViewHolder;
import io.realm.BaseRealm;
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


    public class DeputadoRecyclerViewAdapter extends RealmSearchAdapter<Deputado, DeputadoRecyclerViewAdapter.ViewHolder> {

        public DeputadoRecyclerViewAdapter(Context context, Realm realm, String filterColumnName) {
            super(context, realm, filterColumnName);
        }

        public class ViewHolder extends RealmSearchViewHolder {

            private DeputadoItemView deputadoItemView;

            public ViewHolder(FrameLayout container, TextView footerTextView) {
                super(container, footerTextView);
            }

            public ViewHolder(DeputadoItemView deputadoItemView) {
                super(deputadoItemView);
                this.deputadoItemView = deputadoItemView;
            }
        }

        @Override
        public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int viewType) {
            ViewHolder vh = new ViewHolder(new DeputadoItemView(viewGroup.getContext()));
            return vh;
        }

        @Override
        public void onBindRealmViewHolder(final ViewHolder viewHolder, int position) {
            final Deputado deputado = realmResults.get(position);
            viewHolder.deputadoItemView.bind(deputado);
            viewHolder.itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO IMPLEMENTAR LOGICA DE INCREMENTAR 1 NO BANCO REMOTO
                    realm.close();
                    Intent i = new Intent( v.getContext(), PerfilDeputado.class);
                    Session.setIdeCadastroDeputado(deputado.getIdeCadastro());
                    startActivity(i);
                    finish();


                }
            });
        }


        @Override
        public ViewHolder onCreateFooterViewHolder(ViewGroup viewGroup) {
            View v = inflater.inflate(R.layout.footer_view, viewGroup, false);
            return new ViewHolder(
                    (FrameLayout) v,
                    (TextView) v.findViewById(R.id.footer_text_view));
        }

        @Override
        public void onBindFooterViewHolder(ViewHolder holder, int position) {
            super.onBindFooterViewHolder(holder, position);
            holder.itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    }
            );
        }
    }
}