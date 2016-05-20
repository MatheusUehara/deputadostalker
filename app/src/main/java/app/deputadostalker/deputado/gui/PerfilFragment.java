package app.deputadostalker.deputado.gui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import app.deputadostalker.R;
import app.deputadostalker.comissoes.dominio.Comissoes;
import app.deputadostalker.comissoes.dominio.ComissoesDeputado;
import app.deputadostalker.deputado.api.DeputadoAPI;
import app.deputadostalker.deputado.api.DeputadoDes;
import app.deputadostalker.deputado.dominio.Deputado;
import app.deputadostalker.usuario.service.CircularNetworkImageView;
import app.deputadostalker.usuario.service.CustomVolleyRequest;
import app.deputadostalker.util.Session;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        ListView list = (ListView) view.findViewById(R.id.comissoes);

        RealmResults<Deputado> deputados;
        RealmResults<Comissoes> comissao;
        RealmResults<ComissoesDeputado> comissaoDeputado;
        ArrayList<RealmResults<Comissoes>> comissoes = new ArrayList<>();


        Deputado deputado;

        Realm realm = Realm.getDefaultInstance();
        deputados = realm.where(Deputado.class)
                .equalTo("ideCadastro", Session.getIdeCadastroDeputado())
                .findAll();

        deputado = deputados.get(0);

        comissaoDeputado = realm.where(ComissoesDeputado.class).equalTo("deputado_ideCadastro", deputado.getIdeCadastro()).findAll();


        for (ComissoesDeputado i:comissaoDeputado){
            comissao = realm.where(Comissoes.class).contains("idOrgao", String.valueOf(i.getOrgao_idOrgao())).findAll();
            comissoes.add(comissao);
        }

        TextView nomeParlamentar = (TextView) view.findViewById(R.id.nomeParlamentar);
        nomeParlamentar.setText(deputado.getNomeParlamentar());

        TextView nomeCivil = (TextView) view.findViewById(R.id.nomeCivil);
        nomeCivil.append(deputado.getNomeCivil());

        TextView partido = (TextView) view.findViewById(R.id.partido);
        partido.append(deputado.getPartido_idPartido());

        TextView email = (TextView) view.findViewById(R.id.email);
        email.append(deputado.getEmail());

        TextView profissao = (TextView) view.findViewById(R.id.profissao);

        if (deputado.getNomeProfissao() != null) {
            profissao.append(deputado.getNomeProfissao());
        } else {
            profissao.append("Não informado.");
        }

        TextView estado = (TextView) view.findViewById(R.id.ufRepresentacao);
        estado.append(deputado.getUfRepresentacaoAtual());

        TextView sexo = (TextView) view.findViewById(R.id.sexo);
        sexo.append(deputado.getSexo());

        CircularNetworkImageView fotoDeputado = (CircularNetworkImageView) view.findViewById(R.id.fotoDeputado);

        ImageLoader imageLoader;

        imageLoader = CustomVolleyRequest.getInstance(view.getContext()).getImageLoader();

        imageLoader.get(deputado.getUrlFoto(),
                ImageLoader.getImageListener(fotoDeputado,
                        R.mipmap.ic_launcher,
                        R.mipmap.ic_launcher));
        fotoDeputado.setImageUrl(deputado.getUrlFoto(), imageLoader);

        //testeReq();
        return view;

    }


    private void testeReq(){
        Gson gson = new GsonBuilder().registerTypeAdapter(Deputado.class ,new DeputadoDes()).create();

        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(getString(R.string.urlBase))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        DeputadoAPI deputadoAPI = retrofit.create(DeputadoAPI.class);


        final Call<List<Deputado>> callDeputado = deputadoAPI.getDeputados();
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    List<Deputado> listDeputados = callDeputado.execute().body();
                    if( listDeputados != null ){
                        for( Deputado d : listDeputados ){
                            Log.i("Perfil Deputado", "Deputado: "+d.getNomeParlamentar());
                        }
                    }
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

}
