package app.deputadostalker.deputado.dao;

import java.util.ArrayList;

import app.deputadostalker.database.DAO;
import app.deputadostalker.deputado.dominio.Deputado;
import app.deputadostalker.usuario.dominio.Usuario;
import app.deputadostalker.util.Session;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;

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

}
