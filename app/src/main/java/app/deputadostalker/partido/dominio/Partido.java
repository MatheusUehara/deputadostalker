package app.deputadostalker.partido.dominio;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by igormlgomes on 30/04/16.
 */
public class Partido extends RealmObject {

    private String idPartido;
    private String nome;

    public String getIdPartido() {
        return idPartido;
    }

    public void setIdPartido(String idPartido) {
        this.idPartido = idPartido;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
