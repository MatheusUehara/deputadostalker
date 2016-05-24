package app.deputadostalker.comissao.gui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import app.deputadostalker.R;
import app.deputadostalker.comissao.dominio.Comissao;
import app.deputadostalker.comissao.gui.ComissaoAdapter;
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
}

