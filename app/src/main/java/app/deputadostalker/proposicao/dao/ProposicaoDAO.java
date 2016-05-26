package app.deputadostalker.proposicao.dao;

import java.util.ArrayList;

import app.deputadostalker.comissao.dominio.Comissao;
import app.deputadostalker.database.DAO;
import app.deputadostalker.proposicao.dominio.Proposicao;
import app.deputadostalker.proposicao.dominio.ProposicaoDeputado;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by igormlgomes on 25/05/16.
 */
public class ProposicaoDAO extends DAO {

    private static final ProposicaoDAO instance = new ProposicaoDAO();

    private Realm realm = Realm.getDefaultInstance();

    private ProposicaoDAO() {
        super();
    }

    public static ProposicaoDAO getInstance() {
        return instance;
    }

    public ArrayList<Proposicao> getProposicoes(int ideCadastro){

        ArrayList<Proposicao> proposicoes = new ArrayList<>();
        RealmResults<ProposicaoDeputado> proposicaoDeputados = getProposicaoDeputado(ideCadastro);

        for (ProposicaoDeputado i: proposicaoDeputados ){
            RealmResults<Proposicao> proposicao = realm.where(Proposicao.class).contains("idProposicao", String.valueOf(i.getProposicao_idProposicao())).findAll();
            proposicoes.add(proposicao.get(0));
        }

        return proposicoes;

    }

    public ArrayList<String> getProposicao(int idProposicao ){
        ArrayList<String> retorno = new ArrayList<String>();

        RealmResults<Proposicao> proposicao = realm.where(Proposicao.class).equalTo("idProposicao",idProposicao).findAll();

        if (proposicao.size() == 0 ) {
            Proposicao p = proposicao.get(0);
            retorno.add(p.getNome());
            retorno.add(String.valueOf(p.getNumero()));
            retorno.add(p.getAno());
            retorno.add(p.getTxtEmenta());
            retorno.add(p.getTxtExplicacaoEmenta());
            retorno.add(p.getDataApresentacao());
            retorno.add(p.getDataUltimoDespacho());
            retorno.add(p.getTxtUltimoDespacho());
            RealmResults<Comissao> comissoes = realm.where(Comissao.class).equalTo("idOrgao", p.getOrgao_idOrgao()).findAll();
            retorno.add(comissoes.get(0).getNomeComissao());
            //TODO implementar as classes SituacaoProposicao e TipoProposicao
            return retorno;
        }else{
            return retorno;

        }
    }

    public RealmResults<ProposicaoDeputado> getProposicaoDeputado(int ideCadastro){
        RealmResults<ProposicaoDeputado> proposicaoDeputado;
        return proposicaoDeputado = realm.where(ProposicaoDeputado.class).equalTo("deputado_ideCadastro", ideCadastro).findAll();
    }
}
