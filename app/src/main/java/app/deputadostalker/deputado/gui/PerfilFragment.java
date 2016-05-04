package app.deputadostalker.deputado.gui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import app.deputadostalker.R;
import app.deputadostalker.deputado.dominio.Deputado;
import app.deputadostalker.usuario.service.CircularNetworkImageView;
import app.deputadostalker.usuario.service.CustomVolleyRequest;
import app.deputadostalker.util.Session;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {

    private RealmResults<Deputado> deputados;
    private Deputado deputado;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        Realm realm = Realm.getDefaultInstance();
        deputados = realm.where(Deputado.class)
                .contains("ideCadastro", Session.getIdeCadastroDeputado())
                .findAll();
        deputado = deputados.get(0);

        TextView nomeParlamentar = (TextView) view.findViewById(R.id.nomeParlamentar);
        nomeParlamentar.setText(deputado.getNomeParlamentar());
        TextView nomeCivil = (TextView) view.findViewById(R.id.nomeCivil);
        nomeCivil.append(deputado.getNomeCivil());
        TextView partido = (TextView) view.findViewById(R.id.partido);
        partido.append(deputado.getPartido());
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

        return view;

    }

}
