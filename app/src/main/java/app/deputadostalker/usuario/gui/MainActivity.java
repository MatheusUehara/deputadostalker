package app.deputadostalker.usuario.gui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;


import app.deputadostalker.R;
//import app.deputadostalker.cambista.gui.GlobalFragment;
//import app.deputadostalker.cambista.gui.MatchFragment;
import app.deputadostalker.util.Session;

public class MainActivity extends android.support.v7.app.AppCompatActivity{

    public void onBackPressed() {
        logout();
    }

    Toolbar toolbar;

    ViewPager pager;
    ViewPagerAdapter adapter;
    TabLayout tabs;
    private int[] tabIcons = {
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher};

    String TITLES1[] = {"Perfil","Configurações","Sair"};

    int ICONS[] = {R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher};

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

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        EMAIL = Session.getUsuarioLogado().getEmail();
        NAME = Session.getUsuarioLogado().getName();
        USER_IMAGE = Session.getUsuarioLogado().getImageUrl();

        slidingTabs();
        davDrawer();
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
        adapter.addFrag(new BlankFragment(), "match");
        adapter.addFrag(new BlankFragment(), "location");
        adapter.addFrag(new BlankFragment(), "chat");
        viewPager.setAdapter(adapter);
    }

    public void davDrawer(){
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new DrawerAdapter(TITLES1,ICONS,NAME,EMAIL,USER_IMAGE,this);
        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);

        mDrawerToggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close){
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
            @Override public boolean onSingleTapUp(MotionEvent e) {
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
                if (pref.getBoolean("signed_in_with_facebook",false)){
                    LoginManager.getInstance().logOut();
                    SharedPreferences.Editor editor = pref.edit();
                    editor.clear();
                    editor.commit();

                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                }else if(pref.getBoolean("signed_in_with_google",false)){
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
}
