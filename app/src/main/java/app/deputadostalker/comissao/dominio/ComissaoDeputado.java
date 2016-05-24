package app.deputadostalker.comissao.dominio;

import io.realm.RealmObject;

/**
 * Created by igormlgomes on 01/05/16.
 */
public class ComissaoDeputado extends RealmObject {

    private String orgao_idOrgao;
    private int deputado_ideCadastro;

    public String getOrgao_idOrgao() {
        return orgao_idOrgao;
    }

    public void setOrgao_idOrgao(String orgao_idOrgao) {
        this.orgao_idOrgao = orgao_idOrgao;
    }

    public int getDeputado_ideCadastro() {
        return deputado_ideCadastro;
    }

    public void setDeputado_ideCadastro(int deputado_ideCadastro) {
        this.deputado_ideCadastro = deputado_ideCadastro;
    }
}
