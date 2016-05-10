package app.deputadostalker.partido.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import app.deputadostalker.R;
import app.deputadostalker.partido.api.PartidoAPI;
import app.deputadostalker.partido.api.PartidoDes;
import app.deputadostalker.partido.dominio.Partido;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PartidoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partido);
    }


    @Override
    protected void onResume(){
        super.onResume();

        Gson gson = new GsonBuilder().registerTypeAdapter(Partido.class ,new PartidoDes()).create();

        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(getString(R.string.urlBase))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        PartidoAPI partidoAPI = retrofit.create(PartidoAPI.class);

        // Chamada de exemplo quando o request for de um único objeto
        /*
        Call<Partido> call = partidoAPI.getPartidos();
        call.enqueue(new Callback<Partido>() {
             @Override
             public void onResponse(Call<Partido> call, Response<Partido> response) {
                 Partido partido = response.body();
                 if (partido != null){
                     teste.setText(Html.fromHtml());
                 }
             }

             @Override
             public void onFailure(Call<Partido> call, Throwable t) {
                Log.i(TAG, "");
             }
        });
        */

        final Call<List<Partido>> callPartidos = partidoAPI.getPartidos();
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    List<Partido> listPartidos = callPartidos.execute().body();
                    if( listPartidos != null ){
                        for( Partido c : listPartidos ){
                            Log.i("Partido Activity", "Partido: "+c.getNome());
                        }
                    }
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("Partido Activity", "Partidos request Ok");
                    }
                });
            }
        }.start();
    }
}
