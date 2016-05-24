package app.deputadostalker.usuario.dominio;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Matheus Uehara on 07/03/2016.
 */
public class Usuario extends RealmObject {

    @PrimaryKey
    private String id;
    private String nome;
    private String profileUrl;
    private String email;
    private String redeSocial;
    private String idTokenPush;

    public String getRedeSocial() {
        return redeSocial;
    }

    public void setRedeSocial(String redeSocial) {
        this.redeSocial = redeSocial;
    }

    public String getIdTokenPush() {
        return idTokenPush;
    }

    public void setIdTokenPush(String idTokenPush) {
        this.idTokenPush = idTokenPush;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String name) {
        this.nome = name;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }


}
