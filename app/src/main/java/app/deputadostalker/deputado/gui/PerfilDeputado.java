package app.deputadostalker.deputado.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import app.deputadostalker.R;
import app.deputadostalker.util.Session;


public class PerfilDeputado extends android.support.v7.app.AppCompatActivity {

    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    TabLayout tabs;

    @Override
    public void onBackPressed() {
        Session.setIdeCadastroDeputado(0);
        Intent i = new Intent(PerfilDeputado.this, PesquisaDeputado.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_deputado);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);

        slidingTabs();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Session.setIdeCadastroDeputado(0);
                Intent i = new Intent(PerfilDeputado.this, PesquisaDeputado.class);
                startActivity(i);
                finish();
            }
        });

        FloatingActionButton favoriteButtom = (FloatingActionButton) findViewById(R.id.favorite);

        favoriteButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PerfilDeputado.this,"CLICOU EM FAVORITAR O DEPUTADO "+Session.getIdeCadastroDeputado(),Toast.LENGTH_LONG).show();
            }
        });

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
        adapter.addFrag(new ProposicaoDeputado(), "Proposições");
        viewPager.setAdapter(adapter);
    }

}
