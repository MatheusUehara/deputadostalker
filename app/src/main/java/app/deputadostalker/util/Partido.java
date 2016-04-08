package app.deputadostalker.util;

public enum Partido {

	PMDB("PARTIDO DO MOVIMENTO DEMOCRÁTICO BRASILEIRO"),
	PTB("PARTIDO TRABALHISTA BRASILEIRO"),
	PDT("PARTIDO DEMOCRÁTICO TRABALHISTA"),
	PT("PARTIDO DOS TRABALHADORES"),
	DEM("DEMOCRATAS"),

	;


	private String nomeExtenso;

	Partido(String nomeExtenso) {
		this.nomeExtenso = nomeExtenso;
	}
}
