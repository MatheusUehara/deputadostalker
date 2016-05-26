package app.deputadostalker.deputado.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import app.deputadostalker.R;
import app.deputadostalker.comissao.gui.ComissaoFragment;
import app.deputadostalker.deputado.dominio.DeputadoFavorito;
import app.deputadostalker.deputado.service.DeputadoService;
import app.deputadostalker.frequencia.gui.FrequenciaFragment;
import app.deputadostalker.proposicao.gui.ProposicaoFragment;
import app.deputadostalker.util.Session;
import android.support.v7.app.AppCompatActivity;


public class PerfilDeputado extends AppCompatActivity implements View.OnClickListener{

    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    TabLayout tabs;
    DeputadoService service = DeputadoService.getInstance();

    public int ideCadastro;

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        ideCadastro = intent.getIntExtra("ideCadastro",160976);

        setContentView(R.layout.activity_perfil_deputado);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);

        slidingTabs();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        FloatingActionButton favoriteButtom = (FloatingActionButton) findViewById(R.id.favorite);
        PerfilDeputado listener = new PerfilDeputado();
        favoriteButtom.setOnClickListener(listener);
    }

    public void slidingTabs() {
        pager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(pager);
        tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(pager);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new PerfilFragment(), "Perfil");
        adapter.addFrag(new ComissaoFragment(),"Comissoes");
        adapter.addFrag(new FrequenciaFragment(), "Frequência");
        adapter.addFrag(new ProposicaoFragment(), "Proposições");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch ( v.getId()){
            case R.id.favorite:
                DeputadoFavorito deputado = new DeputadoFavorito();
                deputado.setIdUsuario(Session.getUsuarioLogado().getId());
                deputado.setIdeCadastro(ideCadastro);
                Log.d("VALORES", deputado.getIdUsuario() + " " + deputado.getIdeCadastro());

                if (service.insertDeputadoFavorito(deputado)) {
                    Toast.makeText(v.getContext(), "Deputado Favoritado com sucesso", Toast.LENGTH_LONG).show();
                    Log.d("Deputado Favoritado", "COM SUCESSO");
                } else {
                    Toast.makeText(v.getContext(), "O Deputado solicitado já é favorito", Toast.LENGTH_LONG).show();
                    Log.d("Deputado não Favoritado", "ERRO");

                }
                break;
            default:
                break;

        }
    }
}
