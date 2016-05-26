package app.deputadostalker.proposicao.gui;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import app.deputadostalker.R;
import app.deputadostalker.proposicao.service.ProposicaoService;

/**
 * Created by igormlgomes on 26/05/16.
 */
public class ProposicaoActivity extends AppCompatActivity{

    ProposicaoService service = ProposicaoService.getInstance();

    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_proposicoes);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        int idProposicao = intent.getIntExtra("idProposicao", 0);

        TextView nome = (TextView) findViewById(R.id.nomeProposicao);
        TextView numero = (TextView) findViewById(R.id.numeroProposicao);
        TextView ano = (TextView) findViewById(R.id.anoProposicao);
        TextView ementa = (TextView) findViewById(R.id.ementaProposicao);
        TextView expEmenta = (TextView) findViewById(R.id.explicacaoProposicao);
        TextView dataApresentacao = (TextView) findViewById(R.id.dataProposicao);
        TextView dataUltDesp = (TextView) findViewById(R.id.dataDespachoProposicao);
        TextView ultDesp = (TextView) findViewById(R.id.txtDespachoProposicao);
        TextView orgao = (TextView) findViewById(R.id.orgaoProposicao);
        TextView situacao = (TextView) findViewById(R.id.situacaoProposicao);
        TextView tipo = (TextView) findViewById(R.id.tipoProposicao);


        ArrayList<String> proposicao = service.getProposicao(idProposicao);

        if (proposicao.size() == 9){
            nome.append(proposicao.get(0));
            numero.append(proposicao.get(1));
            ano.append(proposicao.get(2));
            ementa.append(proposicao.get(3));
            expEmenta.append(proposicao.get(4));
            dataApresentacao.append(proposicao.get(5));
            dataUltDesp.append(proposicao.get(6));
            ultDesp.append(proposicao.get(7));
            orgao.append(proposicao.get(8));
        }else{
            Toast.makeText(ProposicaoActivity.this,"Não foi possivel carregar os dados desta proposicao",Toast.LENGTH_SHORT).show();
        }
    }
}
