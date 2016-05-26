package app.deputadostalker.deputado.dao;

import java.util.ArrayList;

import app.deputadostalker.database.DAO;
import app.deputadostalker.deputado.dominio.Deputado;
import app.deputadostalker.deputado.dominio.DeputadoFavorito;
import app.deputadostalker.usuario.dominio.Usuario;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Matheus Uehara on 22/05/2016.
 */
public class DeputadoDAO extends DAO {

    private static final DeputadoDAO instance = new DeputadoDAO();

    private Realm realm = Realm.getDefaultInstance();

    private DeputadoDAO() {
        super();
    }

    public static DeputadoDAO getInstance() {
        return instance;
    }

    public Deputado getDeputado(int ideCadastro){
        RealmResults<Deputado> deputados = realm.where(Deputado.class)
                .equalTo("ideCadastro", ideCadastro)
                .findAll();
        if (deputados.size() > 0){
            return deputados.get(0);
        }else{
            return new Deputado();
        }
    }


    public boolean insertDeputadoFavorito(DeputadoFavorito deputado){
        if (!verificaFavorito(deputado)) {
            realm.beginTransaction();
            DeputadoFavorito deputadoFavorito = realm.createObject(DeputadoFavorito.class);
            deputadoFavorito.setIdeCadastro(deputado.getIdeCadastro());
            deputadoFavorito.setIdUsuario(deputado.getIdUsuario());
            realm.commitTransaction();
            return true;
        }else{
            return false;
        }
    }

    public boolean verificaFavorito(DeputadoFavorito deputado){
        RealmResults<DeputadoFavorito> deputados = realm.where(DeputadoFavorito.class).equalTo("ideCadastro",deputado.getIdeCadastro()).equalTo("idUsuario",deputado.getIdUsuario()).findAll();
        if (deputados.size() > 0){
            return true;
        }else{
            return false;
        }
    }

    public ArrayList<Deputado> getDeputadosFavoritos(Usuario usuario){

        ArrayList<Deputado> deputadosFavoritos = new ArrayList<Deputado>();

        RealmResults<DeputadoFavorito> deputados = realm.where(DeputadoFavorito.class).equalTo("idUsuario",usuario.getId()).findAll();
        if (deputados.size() > 0){
            for (DeputadoFavorito i : deputados){
                deputadosFavoritos.add(getDeputado(i.getIdeCadastro()));
            }
        }
        return deputadosFavoritos;
    }

}
