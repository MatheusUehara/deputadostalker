package app.deputadostalker.util;

/**
 * Representa todos os estados Brasileiros.
 * @author  Matheus Uehara
 */
public enum Estado {
	AC("Acre"),
	AL("Alagoas"),
	AP("Amap?"),
	AM("Amazonas"),
	BA("Bahia"),
	CE("Cear?"),
	DF("Distrito Federal"),
	ES("Esp?rito Santo"),
	GO("Goi?s"),
	MA("Maranh?o"),
	MT("Mato Grosso"),
	MS("Mato Grosso do Sul"),
	MG("Minas Gerais"),
	PA("Par?"),
	PB("Para?ba"),
	PR("Paran?"),
	PE("Pernambuco"),
	PI("Piau?"),
	RR("Roraima"),
	RO("Rond?nia"),
	RJ("Rio de Janeiro"),
	RN("Rio Grande do Norte"),
	RS("Rio Grande do Sul"),
	SC("Santa Catarina"),
	SP("S?o Paulo"),
	SE("Sergipe"),
	TO("Tocantins"),
	NONE("N?o Encontrado");


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
	 * @param siglaEstado sigla do estado que ser? retornado o nome
	 * @return            nome do estado cuja sigla ? representado pela siglaEstado
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
