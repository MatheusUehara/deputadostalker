package app.deputadostalker.gabinete.dominio;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by igormlgomes on 30/04/16.
 */
public class Gabinete extends RealmObject{
    @PrimaryKey
    private int idGabinete;
    private int anexo;
    private String telefone;

    public int getIdGabinete() {
        return idGabinete;
    }

    public void setIdGabinete(int idGabinete) {
        this.idGabinete = idGabinete;
    }

    public int getAnexo() {
        return anexo;
    }

    public void setAnexo(int anexo) {
        this.anexo = anexo;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
