package app.deputadostalker.util;

/**
 * Representa todos os estados Brasileiros.
 * @author  Matheus Uehara
 */
public enum Estado {
	AC("Acre"),
	AL("Alagoas"),
	AP("Amapá"),
	AM("Amazonas"),
	BA("Bahia"),
	CE("Ceará"),
	DF("Distrito Federal"),
	ES("Espírito Santo"),
	GO("Goiás"),
	MA("Maranhão"),
	MT("Mato Grosso"),
	MS("Mato Grosso do Sul"),
	MG("Minas Gerais"),
	PA("Pará"),
	PB("Paraíba"),
	PR("Paraná"),
	PE("Pernambuco"),
	PI("Piauí"),
	RR("Roraima"),
	RO("Rondônia"),
	RJ("Rio de Janeiro"),
	RN("Rio Grande do Norte"),
	RS("Rio Grande do Sul"),
	SC("Santa Catarina"),
	SP("São Paulo"),
	SE("Sergipe"),
	TO("Tocantins"),
	NONE("Não Encontrado");


	private String nomeEstado;

	Estado(String nomeEstado) {
		this.nomeEstado = nomeEstado;
	}

	public String getNomeEstado() {
		return nomeEstado;
	}

	public void setNomeEstado(String nomeEstado) {
		this.nomeEstado = nomeEstado;
	}

	/**
	 * A partir da sigla retorna o nome do estado.
	 *
	 * @param siglaEstado sigla do estado que será retornado o nome
	 * @return            nome do estado cuja sigla é representado pela siglaEstado
	 * @author            Matheus Uehara
	 */
	public static Estado getEstado(String siglaEstado){
		Estado retorno = null;
		switch(siglaEstado){
			case "AC":
				retorno =  Estado.AC;
				break;
			case "AL":
				retorno = Estado.AL;
				break;
			case"AP":
				retorno = Estado.AP;
				break;
			case "AM":
				retorno = Estado.AM;
				break;
			case "BA":
				retorno = Estado.BA;
				break;
			case "CE":
				retorno = Estado.CE;
				break;
			case "DF":
				retorno = Estado.DF;
				break;
			case "ES":
				retorno = Estado.ES;
				break;
			case "GO":
				retorno = Estado.GO;
				break;
			case "MA":
				retorno = Estado.MA;
				break;
			case "MT":
				retorno = Estado.MT;
				break;
			case "MS":
				retorno = Estado.MS;
				break;
			case "MG":
				retorno = Estado.MG;
				break;
			case "PA":
				retorno = Estado.PA;
				break;
			case "PB":
				retorno = Estado.PB;
				break;
			case "PR":
				retorno = Estado.PR;
				break;
			case "PE":
				retorno = Estado.PE;
				break;
			case "PI":
				retorno = Estado.PI;
				break;
			case "RR":
				retorno = Estado.RR;
				break;
			case "RO":
				retorno = Estado.RO;
				break;
			case "RJ":
				retorno = Estado.RJ;
				break;
			case "RN":
				retorno = Estado.RN;
				break;
			case "RS":
				retorno = Estado.RS;
				break;
			case "SC":
				retorno = Estado.SC;
				break;
			case "SP":
				retorno = Estado.SP;
				break;
			case "SE":
				retorno = Estado.SE;
				break;
			case "TO":
				retorno = Estado.TO;
				break;
			default:
				retorno = Estado.NONE;
		}
		return retorno;
	}
}
