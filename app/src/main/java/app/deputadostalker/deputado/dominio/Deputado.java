package app.deputadostalker.deputado.dominio;

import app.deputadostalker.gabinete.dominio.Gabinete;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Classe de dominio dos deputados.
 *
 * @author Matheus Uehara
 */
public class Deputado extends RealmObject {

    @PrimaryKey
    private String ideCadastro;
    private int matricula;
    private int idParlamentar;
    private String nomeCivil;
    private String nomeParlamentar;
    private String urlFoto;
    private String sexo;
    private String ufRepresentacaoAtual;
    private String email;
    private String dataNascimento;
    private String nomeProfissao;
    private String situacaoNaLegislaturaAtual;
    private int gabinete_idGabinete;
    private String partido_idPartido;

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public int getIdParlamentar() {
        return idParlamentar;
    }

    public void setIdParlamentar(int idParlamentar) {
        this.idParlamentar = idParlamentar;
    }

    public String getNomeParlamentar() {
        return nomeParlamentar;
    }

    public void setNomeParlamentar(String nomeParlamentar) {
        this.nomeParlamentar = nomeParlamentar;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public int getIdGabinete() {
        return gabinete_idGabinete;
    }

    public void setIdGabinete(Gabinete idGabinete) {
        this.gabinete_idGabinete = idGabinete.getIdGabinete();
    }

    public String getPartido() {
        return partido_idPartido;
    }

    public void setPartido(app.deputadostalker.partido.dominio.Partido partido) {
        this.partido_idPartido = partido.getIdPartido();
    }

    public String getIdeCadastro() {
        return ideCadastro;
    }

    public void setIdeCadastro(String ideCadastro) {
        this.ideCadastro = ideCadastro;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomeProfissao() {
        return nomeProfissao;
    }

    public void setNomeProfissao(String nomeProfissao) {
        this.nomeProfissao = nomeProfissao;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getUfRepresentacaoAtual() {
        return ufRepresentacaoAtual;
    }

    public void setUfRepresentacaoAtual(String ufRepresentacaoAtual) {
        this.ufRepresentacaoAtual = ufRepresentacaoAtual;
    }

    public String getSituacaoNaLegislaturaAtual() {
        return situacaoNaLegislaturaAtual;
    }

    public void setSituacaoNaLegislaturaAtual(String situacaoNaLegislaturaAtual) {
        this.situacaoNaLegislaturaAtual = situacaoNaLegislaturaAtual;
    }

    public String getNomeCivil() {
        return nomeCivil;
    }

    public void setNomeCivil(String nomeCivil) {
        this.nomeCivil = nomeCivil;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

}
