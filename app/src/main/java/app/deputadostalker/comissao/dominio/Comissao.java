package app.deputadostalker.comissao.dominio;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by igormlgomes on 30/04/16.
 */
public class Comissao extends RealmObject implements RealmModel{

    @PrimaryKey
    private String idOrgao;
    private String siglaComissao;
    private String nomeComissao;

    public String getIdOrgao() {
        return idOrgao;
    }

    public void setIdOrgao(String idOrgao) {
        this.idOrgao = idOrgao;
    }

    public String getSiglaComissao() {
        return siglaComissao;
    }

    public void setSiglaComissao(String siglaComissao) {
        this.siglaComissao = siglaComissao;
    }

    public String getNomeComissao() {
        return nomeComissao;
    }

    public void setNomeComissao(String nomeComissao) {
        this.nomeComissao = nomeComissao;
    }
}
