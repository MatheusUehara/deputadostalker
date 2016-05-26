package app.deputadostalker.proposicao.dominio;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by igormlgomes on 25/05/16.
 */
public class Proposicao extends RealmObject implements RealmModel {

    @PrimaryKey
    private int idProposicao;
    private String nome;
    private int numero;
    private String ano;
    private String txtEmenta;
    private String txtExplicacaoEmenta;
    private String dataApresentacao;
    private String dataUltimoDespacho;
    private String txtUltimoDespacho;
    private int orgao_idOrgao;
    private int situacaoProposicao_idSituacaoProposicao;
    private int tipoProposicao_idTipoProposicao;

    public int getIdProposicao() {
        return idProposicao;
    }

    public void setIdProposicao(int idProposicao) {
        this.idProposicao = idProposicao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getTxtEmenta() {
        return txtEmenta;
    }

    public void setTxtEmenta(String txtEmenta) {
        this.txtEmenta = txtEmenta;
    }

    public String getTxtExplicacaoEmenta() {
        return txtExplicacaoEmenta;
    }

    public void setTxtExplicacaoEmenta(String txtExplicacaoEmenta) {
        this.txtExplicacaoEmenta = txtExplicacaoEmenta;
    }

    public String getDataApresentacao() {
        return dataApresentacao;
    }

    public void setDataApresentacao(String dataApresentacao) {
        this.dataApresentacao = dataApresentacao;
    }

    public String getDataUltimoDespacho() {
        return dataUltimoDespacho;
    }

    public void setDataUltimoDespacho(String dataUltimoDespacho) {
        this.dataUltimoDespacho = dataUltimoDespacho;
    }

    public String getTxtUltimoDespacho() {
        return txtUltimoDespacho;
    }

    public void setTxtUltimoDespacho(String txtUltimoDespacho) {
        this.txtUltimoDespacho = txtUltimoDespacho;
    }

    public int getOrgao_idOrgao() {
        return orgao_idOrgao;
    }

    public void setOrgao_idOrgao(int orgao_idOrgao) {
        this.orgao_idOrgao = orgao_idOrgao;
    }

    public int getSituacaoProposicao_idSituacaoProposicao() {
        return situacaoProposicao_idSituacaoProposicao;
    }

    public void setSituacaoProposicao_idSituacaoProposicao(int situacaoProposicao_idSituacaoProposicao) {
        this.situacaoProposicao_idSituacaoProposicao = situacaoProposicao_idSituacaoProposicao;
    }

    public int getTipoProposicao_idTipoProposicao() {
        return tipoProposicao_idTipoProposicao;
    }

    public void setTipoProposicao_idTipoProposicao(int tipoProposicao_idTipoProposicao) {
        this.tipoProposicao_idTipoProposicao = tipoProposicao_idTipoProposicao;
    }
}
