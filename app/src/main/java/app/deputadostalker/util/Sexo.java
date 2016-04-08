package app.deputadostalker.util;

/**
 * Representa os sexos.
 * @author  Matheus Uehara
 */
public enum Sexo {
	MASCULINO("Masculino"),FEMININO("Feminino"), NONE("Não Encontrado");

	private String descricao;

	Sexo(String descricao){
		this.setDescricao(descricao);
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * A partir da sigla retorna o sexo.
	 *
	 * @param siglaSexo sigla do sexo que será retornado o nome
	 * @return            nome por extenso do sexo que será retornado
	 * @author            Matheus Uehara
	 */
	public static Sexo sexo(String siglaSexo){
		Sexo retorno = null;
		switch(siglaSexo){
			case "M":
				retorno =  Sexo.MASCULINO;
				break;
			case "S":
				retorno =  Sexo.FEMININO;
				break;
			default:
				retorno = Sexo.NONE;
				break;
		}
		return retorno;
	}
}