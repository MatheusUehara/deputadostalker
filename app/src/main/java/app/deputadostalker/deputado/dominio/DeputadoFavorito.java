package app.deputadostalker.deputado.dominio;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by matheusuehara on 24/05/16.
 */
public class DeputadoFavorito extends RealmObject {

    private int ideCadastro;
    private String idUsuario;

    public int getIdeCadastro() {
        return ideCadastro;
    }

    public void setIdeCadastro(int ideCadastro) {
        this.ideCadastro = ideCadastro;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
}
