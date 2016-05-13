package app.deputadostalker.comissoes.dominio;

import io.realm.RealmObject;

/**
 * Created by igormlgomes on 01/05/16.
 */
public class ComissoesDeputado extends RealmObject {

    private int orgao_idOrgao;
    private int deputado_ideCadastro;

    public int getOrgao_idOrgao() {
        return orgao_idOrgao;
    }

    public void setOrgao_idOrgao(int orgao_idOrgao) {
        this.orgao_idOrgao = orgao_idOrgao;
    }

    public int getDeputado_ideCadastro() {
        return deputado_ideCadastro;
    }

    public void setDeputado_ideCadastro(int deputado_ideCadastro) {
        this.deputado_ideCadastro = deputado_ideCadastro;
    }
}
