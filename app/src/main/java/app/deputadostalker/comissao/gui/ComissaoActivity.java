package app.deputadostalker.comissao.gui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import app.deputadostalker.R;
import app.deputadostalker.comissao.api.ComissaoAPI;
import app.deputadostalker.comissao.api.ComissaoDes;
import app.deputadostalker.comissao.dominio.Comissao;
import app.deputadostalker.partido.dominio.Partido;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by igormlgomes on 10/05/16.
 */
public class ComissaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comissoes);
    }


    @Override
    protected void onResume(){
        super.onResume();

        Gson gson = new GsonBuilder().registerTypeAdapter(Partido.class ,new ComissaoDes()).create();

        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(getString(R.string.urlBase))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ComissaoAPI comissoesAPI = retrofit.create(ComissaoAPI.class);


        final Call<List<Comissao>> callComissoes = comissoesAPI.getComissoes();
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    List<Comissao> listComissoes = callComissoes.execute().body();
                    if( listComissoes != null ){
                        for( Comissao c : listComissoes ){
                            Log.i("Comissoes Activity", "Comissoes: "+c.getNomeComissao());
                        }
                    }
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("Comissoes Activity", "Comissoes request Ok");
                    }
                });
            }
        }.start();
    }

}
