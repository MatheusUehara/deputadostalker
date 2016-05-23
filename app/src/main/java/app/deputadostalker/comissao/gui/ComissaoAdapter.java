package app.deputadostalker.comissao.gui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import app.deputadostalker.comissao.dominio.Comissao;

import java.util.ArrayList;

import app.deputadostalker.R;

/**
 * Created by Matheus Uehara on 21/05/2016.
 */

public class ComissaoAdapter extends ArrayAdapter<Comissao> {

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
