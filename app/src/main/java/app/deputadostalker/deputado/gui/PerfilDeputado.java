package app.deputadostalker.deputado.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import app.deputadostalker.R;
import app.deputadostalker.usuario.gui.BlankFragment;
import app.deputadostalker.usuario.gui.MainActivity;
import app.deputadostalker.usuario.gui.ViewPagerAdapter;

public class PerfilDeputado extends android.support.v7.app.AppCompatActivity {


    Toolbar toolbar;

    ViewPager pager;
    ViewPagerAdapter adapter;
    TabLayout tabs;
    private int[] tabIcons = {
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher};


    @Override
    public void onBackPressed() {
        Intent i = new Intent(PerfilDeputado.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_deputado);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        slidingTabs();
    }

    public void slidingTabs(){
        pager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(pager);

        tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(pager);
        setupTabIcons();
    }

    private void setupTabIcons() {
        tabs.getTabAt(0).setIcon(tabIcons[0]);
        tabs.getTabAt(1).setIcon(tabIcons[1]);
        tabs.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new BlankFragment(), "PERFIL");
        adapter.addFrag(new BlankFragment(), "CALENDARIO");
        adapter.addFrag(new BlankFragment(), "PROPOSIÇÕES");
        viewPager.setAdapter(adapter);
    }
}
