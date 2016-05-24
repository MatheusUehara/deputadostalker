package app.deputadostalker.deputado.gui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

import app.deputadostalker.R;
import app.deputadostalker.deputado.dominio.Deputado;
import app.deputadostalker.deputado.service.DeputadoService;
import app.deputadostalker.usuario.service.CircularNetworkImageView;
import app.deputadostalker.usuario.service.CustomVolleyRequest;
import app.deputadostalker.util.Session;

public class DeputadoFavoritoActivity extends AppCompatActivity {

    DeputadoService service = DeputadoService.getInstance();
    ListView list;
    ArrayList<Deputado> deputados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_deputado_favorito);

        list = (ListView) findViewById(R.id.deputado_favorito_list_view);

        deputados = service.getDeputadosFavoritos(Session.getUsuarioLogado());

        Log.d("DEPUTADOS FAVORITOS ", String.valueOf(deputados.size()));

        DeputadoAdapter deputadoAdapter= new DeputadoAdapter(getApplicationContext());
        list.setAdapter(deputadoAdapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Deputado deputadoSelecionado = (Deputado) list.getItemAtPosition(i);
                Session.setIdeCadastroDeputado(deputadoSelecionado.getIdeCadastro());

                Intent intent = new Intent (DeputadoFavoritoActivity.this, PerfilDeputado.class);
                startActivity(intent);
            }
        });
    }


    public class DeputadoAdapter  extends ArrayAdapter<Deputado> {

        private Context ctx;
        TextView nomeParlamentar;
        TextView partido_idPartido;
        CircularNetworkImageView urlFoto;


        public DeputadoAdapter(Context ctx) {
            super(ctx , R.layout.comissao_item_list_view, deputados);
            this.ctx = ctx;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent){

            if (view == null){
                LayoutInflater inflater = (LayoutInflater) ctx.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                view = inflater.inflate(R.layout.deputado_item_list_view,null);

                nomeParlamentar = (TextView) view.findViewById(R.id.nomeParlamentar);
                partido_idPartido = (TextView) view.findViewById(R.id.partido_idPartido);
                urlFoto = (CircularNetworkImageView) view.findViewById(R.id.urlFoto);
            }

            Deputado deputado= deputados.get(position);

            nomeParlamentar.setText(deputado.getNomeParlamentar());
            partido_idPartido.setText(deputado.getPartido_idPartido());

            ImageLoader imageLoader;
            imageLoader = CustomVolleyRequest.getInstance(getContext()).getImageLoader();
            imageLoader.get(deputado.getUrlFoto(),
                    ImageLoader.getImageListener(urlFoto,
                            R.mipmap.ic_launcher,
                            R.mipmap.ic_launcher));
            urlFoto.setImageUrl(deputado.getUrlFoto(), imageLoader);
            return view;
        }
    }
}
