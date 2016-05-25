package app.deputadostalker.deputado.gui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.List;
import app.deputadostalker.R;
import app.deputadostalker.deputado.api.DeputadoAPI;
import app.deputadostalker.deputado.api.DeputadoDes;
import app.deputadostalker.deputado.dominio.Deputado;
import app.deputadostalker.deputado.dominio.DeputadoFavorito;
import app.deputadostalker.deputado.service.DeputadoService;
import app.deputadostalker.usuario.service.CircularNetworkImageView;
import app.deputadostalker.usuario.service.CustomVolleyRequest;
import app.deputadostalker.util.Session;
import io.realm.exceptions.RealmIOException;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PerfilFragment extends Fragment {

    DeputadoService service = DeputadoService.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        Deputado deputado = service.getDeputado(Session.getIdeCadastroDeputado());

        TextView nomeParlamentar = (TextView) view.findViewById(R.id.nomeParlamentar);
        nomeParlamentar.setText(deputado.getNomeParlamentar());

        TextView nomeCivil = (TextView) view.findViewById(R.id.nomeCivil);
        nomeCivil.append(deputado.getNomeCivil());

        TextView partido = (TextView) view.findViewById(R.id.partido);
        partido.append(deputado.getPartido_idPartido());

        TextView email = (TextView) view.findViewById(R.id.email);
        email.append(deputado.getEmail());

        TextView estado = (TextView) view.findViewById(R.id.ufRepresentacao);
        estado.append(deputado.getUfRepresentacaoAtual());

        TextView sexo = (TextView) view.findViewById(R.id.sexo);
        sexo.append(deputado.getSexo());

        TextView profissao = (TextView) view.findViewById(R.id.profissao);

        if (deputado.getNomeProfissao() != null) {
            profissao.append(deputado.getNomeProfissao());
        } else {
            profissao.append("Não informado.");
        }

        CircularNetworkImageView fotoDeputado = (CircularNetworkImageView) view.findViewById(R.id.fotoDeputado);

        ImageLoader imageLoader;

        imageLoader = CustomVolleyRequest.getInstance(view.getContext()).getImageLoader();

        imageLoader.get(deputado.getUrlFoto(),
                ImageLoader.getImageListener(fotoDeputado,
                        R.mipmap.ic_launcher,
                        R.mipmap.ic_launcher));
        fotoDeputado.setImageUrl(deputado.getUrlFoto(), imageLoader);

/*

*/
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
