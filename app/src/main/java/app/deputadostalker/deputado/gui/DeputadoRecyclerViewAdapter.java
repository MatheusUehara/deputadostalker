package app.deputadostalker.deputado.gui;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import app.deputadostalker.R;
import app.deputadostalker.deputado.dominio.Deputado;
import app.deputadostalker.util.Session;
import co.moonmonkeylabs.realmsearchview.RealmSearchAdapter;
import co.moonmonkeylabs.realmsearchview.RealmSearchViewHolder;

import io.realm.Realm;


/**
 * Created by matheusuehara on 04/05/16.
 */
public class DeputadoRecyclerViewAdapter extends RealmSearchAdapter<Deputado, DeputadoRecyclerViewAdapter.ViewHolder> {

    private Context context;

    public DeputadoRecyclerViewAdapter(Context context,Realm realm,String filterColumnName) {
        super(context, realm, filterColumnName);
        this.context = context;
    }

    public class ViewHolder extends RealmSearchViewHolder {

        private DeputadoItemView deputadoItemView;

        public ViewHolder(FrameLayout container, TextView footerTextView) {
            super(container, footerTextView);
        }

        public ViewHolder(DeputadoItemView deputadoItemView) {
            super(deputadoItemView);
            this.deputadoItemView = deputadoItemView;
        }
    }

    @Override
    public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int viewType) {
        ViewHolder vh = new ViewHolder(new DeputadoItemView(viewGroup.getContext()));
        return vh;
    }

    @Override
    public void onBindRealmViewHolder(ViewHolder viewHolder, int position) {
        final Deputado deputado = realmResults.get(position);
        viewHolder.deputadoItemView.bind(deputado);
        viewHolder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent i = new Intent( context, PerfilDeputado.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Session.setIdeCadastroDeputado(deputado.getIdeCadastro());
                context.startActivity(i);
            }
        });
    }

    @Override
    public ViewHolder onCreateFooterViewHolder(ViewGroup viewGroup) {
        View v = inflater.inflate(R.layout.footer_view, viewGroup, false);
        return new ViewHolder(
                (FrameLayout) v,
                (TextView) v.findViewById(R.id.footer_text_view));
    }

    @Override
        public void onBindFooterViewHolder(ViewHolder holder, int position) {
            super.onBindFooterViewHolder(holder, position);
            holder.itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    }
            );
        }
}
