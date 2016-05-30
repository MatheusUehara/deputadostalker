package app.deputadostalker.proposicao.gui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import app.deputadostalker.R;
import app.deputadostalker.deputado.gui.PerfilDeputado;
import app.deputadostalker.proposicao.api.ProposicaoAPI;
import app.deputadostalker.proposicao.api.ProposicaoDes;
import app.deputadostalker.proposicao.dominio.Proposicao;
import app.deputadostalker.proposicao.service.ProposicaoService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProposicaoFragment extends Fragment {

    ProposicaoService proposicaoService = ProposicaoService.getInstance();

    PerfilDeputado perfilDeputado;

    ArrayList<Proposicao> proposicoes = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_proposicao_deputado, container, false);

        perfilDeputado = (PerfilDeputado) getActivity();

        int ideCadastro = perfilDeputado.ideCadastro;

        Log.d("ideCadastro" , String.valueOf(ideCadastro));

        final ListView list = (ListView) view.findViewById(R.id.proposicoes);

        requisicaoTest(ideCadastro, list);
/*
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),ProposicaoActivity.class);
                Proposicao proposicao = proposicoes.get(position);
                //intent.putExtra("idProposicao", proposicao.getIdProposicao() );
                intent.putExtra("nome",proposicao.getNome());
                intent.putExtra("numero",proposicao.getNumero());
                intent.putExtra("ano",proposicao.getAno());
                intent.putExtra("ementa",proposicao.getTxtEmenta());
                intent.putExtra("expEmenta",proposicao.getTxtExplicacaoEmenta());
                intent.putExtra("dataApresentacao",proposicao.getDataApresentacao());
                intent.putExtra("dataUltDesp",proposicao.getDataUltimoDespacho());
                intent.putExtra("ultDesp",proposicao.getTxtUltimoDespacho());
                intent.putExtra("orgao",proposicao.getOrgao_idOrgao());
                startActivity(intent);
            }
        });
*/
        return view;
}

    private void requisicaoTest(int ideCadastro, final ListView list){
        final Gson gson = new GsonBuilder().registerTypeAdapter(Proposicao.class, new ProposicaoDes()).create();
        final Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(getString(R.string.urlBase))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        final ProposicaoAPI proposicaoAPI = retrofit.create(ProposicaoAPI.class);

        final Call<List<Proposicao>> callProposicao = proposicaoAPI.getProposicoes(ideCadastro);
        callProposicao.enqueue(new Callback<List<Proposicao>>() {
            @Override
            public void onResponse(Call<List<Proposicao>> call, Response<List<Proposicao>> response) {
                List<Proposicao> listProposicoes = response.body();
                if(listProposicoes != null){
                    Log.d("TAMANHO" , String.valueOf(listProposicoes.size()));
                    for( Proposicao p : listProposicoes ){
                        proposicoes.add(p);
                    }
                    ProposicaoAdapter proposicaoAdapter = new ProposicaoAdapter(getContext(), proposicoes);
                    list.setAdapter(proposicaoAdapter);
                }else{
                    Log.i("ERRO",String.valueOf(response.errorBody()));
                }
            }
            @Override
            public void onFailure(Call<List<Proposicao>> call, Throwable t) {}
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }



public class ProposicaoAdapter extends ArrayAdapter<Proposicao> implements ListAdapter {

    private Context ctx;
    private ArrayList<Proposicao> proposicoes;

    public ProposicaoAdapter(Context ctx , ArrayList<Proposicao> proposicoes){
        super(ctx , R.layout.proposicoes_item_list_view, proposicoes);
        this.proposicoes = proposicoes;
        this.ctx = ctx;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){

        if (view == null){
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            view = inflater.inflate(R.layout.proposicoes_item_list_view,null);
        }

        Proposicao proposicao = proposicoes.get(position);

        TextView nomeProposicao = (TextView) view.findViewById(R.id.nomeProposicao);
        nomeProposicao.setText(proposicao.getTxtEmenta());
        return view;
    }
}

}
