package app.deputadostalker.deputado.gui;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import app.deputadostalker.R;
import app.deputadostalker.deputado.dominio.Deputado;
import app.deputadostalker.usuario.service.CircularNetworkImageView;
import app.deputadostalker.usuario.service.CustomVolleyRequest;

/**
 * Created by matheusuehara on 12/05/16.
 */
public class DeputadoItemView extends RelativeLayout {

    TextView nomeParlamentar;
    TextView partido_idPartido;
    CircularNetworkImageView urlFoto;

    public DeputadoItemView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.deputado_item_list_view, this);
        nomeParlamentar = (TextView) findViewById(R.id.nomeParlamentar);
        partido_idPartido = (TextView) findViewById(R.id.partido_idPartido);
        urlFoto = (CircularNetworkImageView) findViewById(R.id.urlFoto);
    }

    public void bind(Deputado deputado) {
        nomeParlamentar.setText(deputado.getNomeParlamentar());
        partido_idPartido.setText(deputado.getPartido_idPartido());

        ImageLoader imageLoader;
        imageLoader = CustomVolleyRequest.getInstance(getContext()).getImageLoader();
        imageLoader.get(deputado.getUrlFoto(),
                ImageLoader.getImageListener(urlFoto,
                        R.mipmap.ic_launcher,
                        R.mipmap.ic_launcher));
        urlFoto.setImageUrl(deputado.getUrlFoto(), imageLoader);

    }
}
