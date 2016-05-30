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

    //ProposicaoService service = ProposicaoService.getInstance();

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


        try{
            nome.append(intent.getStringExtra("nome"));
            numero.append(String.valueOf(intent.getIntExtra("numero",0)));
            ano.append(intent.getStringExtra("ano"));
            ementa.append(intent.getStringExtra("ementa"));
            expEmenta.append(intent.getStringExtra("expEmenta"));
            dataApresentacao.append(intent.getStringExtra("dataApresentacao"));
            dataUltDesp.append(intent.getStringExtra("dataUltDesp"));
            ultDesp.append(intent.getStringExtra("ultDesp"));
            orgao.append(intent.getStringExtra("orgao"));
        }catch (RuntimeException e){
            Toast.makeText(ProposicaoActivity.this,"Não foi possivel carregar os dados desta proposicao",Toast.LENGTH_SHORT).show();
        }
    }
}
