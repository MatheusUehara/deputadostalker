package app.deputadostalker.comissao.gui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import app.deputadostalker.R;
import app.deputadostalker.comissao.dominio.Comissao;
import app.deputadostalker.comissao.service.ComissaoService;
import app.deputadostalker.util.Session;


public class ComissaoFragment extends Fragment {

    ComissaoService comissaoService = ComissaoService.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_comissao, container, false);

        ListView list = (ListView) view.findViewById(R.id.comissoes);

        ArrayList<Comissao> comissoes = comissaoService.getComissoes(Session.getIdeCadastroDeputado());

        Log.d("NUMERO DE COMISSOES ", String.valueOf(comissoes.size()));

        ComissaoAdapter comissaoAdapter = new ComissaoAdapter(getContext(), comissoes);
        list.setAdapter(comissaoAdapter);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public class ComissaoAdapter extends ArrayAdapter<Comissao> implements ListAdapter {

        private Context ctx;
        private ArrayList<Comissao> comissoes;

        public ComissaoAdapter(Context ctx , ArrayList<Comissao> comissoes){
            super(ctx , R.layout.comissao_item_list_view, comissoes);
            this.comissoes = comissoes;
            this.ctx = ctx;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent){

            if (view == null){
                LayoutInflater inflater = (LayoutInflater) ctx.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                view = inflater.inflate(R.layout.comissao_item_list_view,null);
            }

            Comissao comissao = comissoes.get(position);

            TextView nomeComissao = (TextView) view.findViewById(R.id.nomeComissao);
            nomeComissao.setText(comissao.getNomeComissao());
            return view;
        }
    }

}




