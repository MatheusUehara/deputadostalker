package app.deputadostalker.deputado.gui;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import app.deputadostalker.R;
import app.deputadostalker.deputado.dominio.Deputado;
import app.deputadostalker.usuario.service.CircularNetworkImageView;
import app.deputadostalker.usuario.service.CustomVolleyRequest;
import app.deputadostalker.util.Session;
import io.realm.Realm;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Created by matheusuehara on 04/05/16.
 */
public class DeputadoAdapter extends RealmBaseAdapter<Deputado> implements ListAdapter {

    private Realm realm;
    private RealmResults<Deputado> realmResults;
    private Context context;

    public DeputadoAdapter(Context context, Realm realm, RealmResults<Deputado> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
        this.realm = realm;
        this.context = context;
        this.realmResults = realmResults;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.deputado_item_list_view, parent, false);
            holder = new CustomViewHolder();
            convertView.setTag(holder);

            holder.fotoDeputado = (CircularNetworkImageView) convertView.findViewById(R.id.fotoDeputado);
            holder.nomeDeputado = (TextView) convertView.findViewById(R.id.nomeDeputado);
            holder.partidoDeputado = (TextView) convertView.findViewById(R.id.partidoDeputado);

        } else {
            holder = (CustomViewHolder) convertView.getTag();
        }

        final Deputado d = realmResults.get(position);

        ImageLoader imageLoader;
        imageLoader = CustomVolleyRequest.getInstance(context.getApplicationContext()).getImageLoader();
        imageLoader.get(d.getUrlFoto(),
                ImageLoader.getImageListener(holder.fotoDeputado,
                        R.mipmap.ic_launcher,
                        R.mipmap.ic_launcher));
        holder.fotoDeputado.setImageUrl(d.getUrlFoto(), imageLoader);
        holder.nomeDeputado.setText(d.getNomeParlamentar());
        holder.partidoDeputado.setText(d.getPartido());

        convertView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(context, PerfilDeputado.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Session.setIdeCadastroDeputado(d.getIdeCadastro());
                context.startActivity(i);
            }
        });
        return convertView;
    }

    private static class CustomViewHolder {

        CircularNetworkImageView fotoDeputado;
        TextView nomeDeputado;
        TextView partidoDeputado;
    }
}
