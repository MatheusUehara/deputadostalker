package app.deputadostalker.frequencia.dominio;

import io.realm.RealmObject;

/**
 * Created by Delando on 22/05/2016.
 */
public class Frequencia extends RealmObject {
    private String data;
    private String frequencia;
    private String justificativa;
    private int qtdeSessoes;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(String frequencia) {
        this.frequencia = frequencia;
    }

    public String getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(String justificativa) {
        this.justificativa = justificativa;
    }

    public int getQtdeSessoes() {
        return qtdeSessoes;
    }

    public void setQtdeSessoes(int qtdeSessoes) {
        this.qtdeSessoes = qtdeSessoes;
    }


}
