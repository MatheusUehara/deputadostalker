package app.deputadostalker.proposicao.dominio;

import io.realm.RealmObject;

/**
 * Created by igormlgomes on 25/05/16.
 */
public class ProposicaoDeputado extends RealmObject {

    private int deputado_ideCadastro;
    private int proposicao_idProposicao;

    public int getDeputado_ideCadastro() {
        return deputado_ideCadastro;
    }

    public void setDeputado_ideCadastro(int deputado_ideCadastro) {
        this.deputado_ideCadastro = deputado_ideCadastro;
    }

    public int getProposicao_idProposicao() {
        return proposicao_idProposicao;
    }

    public void setProposicao_idProposicao(int proposicao_idProposicao) {
        this.proposicao_idProposicao = proposicao_idProposicao;
    }
}
