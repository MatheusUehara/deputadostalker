package app.deputadostalker.deputado.dominio;

import app.deputadostalker.util.Estado;
import app.deputadostalker.util.Partido;
import app.deputadostalker.util.Sexo;

/**
 * Classe de dominio dos deputados.
 * @author  Matheus Uehara
 */
public class Deputado {

	String ideCadastro; // Usado pra pesquisar dados dos deputados
	String email;
	String nomeProfissao;
	String dataNascimento ;	
	String situacaoNaLegislaturaAtual;
	String nomeParlamentarAtual;
	String nomeCivil;
	Estado ufRepresentacaoAtual;
	Sexo sexo;
	Partido partido;
	
	public Deputado(String siglaEstado){
		this.ufRepresentacaoAtual = Estado.getEstado(siglaEstado);
		System.out.println(this.ufRepresentacaoAtual.getNomeEstado());
	}
	
	public Deputado(){
		
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

	public Estado getUfRepresentacaoAtual() {
		return ufRepresentacaoAtual;
	}

	public void setUfRepresentacaoAtual(Estado ufRepresentacaoAtual) {
		this.ufRepresentacaoAtual = ufRepresentacaoAtual;
	}

	public String getSituacaoNaLegislaturaAtual() {
		return situacaoNaLegislaturaAtual;
	}

	public void setSituacaoNaLegislaturaAtual(String situacaoNaLegislaturaAtual) {
		this.situacaoNaLegislaturaAtual = situacaoNaLegislaturaAtual;
	}

	public String getNomeParlamentarAtual() {
		return nomeParlamentarAtual;
	}

	public void setNomeParlamentarAtual(String nomeParlamentarAtual) {
		this.nomeParlamentarAtual = nomeParlamentarAtual;
	}

	public String getNomeCivil() {
		return nomeCivil;
	}

	public void setNomeCivil(String nomeCivil) {
		this.nomeCivil = nomeCivil;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}
	
	
}
