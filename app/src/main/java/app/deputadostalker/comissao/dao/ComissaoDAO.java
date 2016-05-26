package app.deputadostalker.comissao.dao;

import java.util.ArrayList;

import app.deputadostalker.comissao.dominio.Comissao;
import app.deputadostalker.comissao.dominio.ComissaoDeputado;
import app.deputadostalker.database.DAO;
import app.deputadostalker.deputado.dominio.Deputado;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Matheus Uehara on 22/05/2016.
 */
public class ComissaoDAO extends DAO {


    private static final ComissaoDAO instance = new ComissaoDAO();

    private Realm realm = Realm.getDefaultInstance();

    private ComissaoDAO() {
        super();
    }

    public static ComissaoDAO getInstance() {
        return instance;
    }

    public ArrayList<Comissao> getComissoes(int ideCadastro){

        ArrayList<Comissao> comissoes = new ArrayList<>();
        RealmResults<ComissaoDeputado> comissaoDeputados = getComissaoDeputado(ideCadastro);

        for (ComissaoDeputado i: comissaoDeputados ){
            RealmResults<Comissao> comissao = realm.where(Comissao.class).contains("idOrgao", String.valueOf(i.getOrgao_idOrgao())).findAll();
            comissoes.add(comissao.get(0));
        }
        return comissoes;

    }

    public Comissao getComissao(String idOrgao ){
        RealmResults<Comissao> comissao = realm.where(Comissao.class).equalTo("idOrgao",idOrgao).findAll();
        return comissao.get(0);

    }

    public RealmResults<ComissaoDeputado> getComissaoDeputado(int ideCadastro){
        RealmResults<ComissaoDeputado> comissaoDeputado;
        comissaoDeputado = realm.where(ComissaoDeputado.class).equalTo("deputado_ideCadastro", ideCadastro).findAll();
        return comissaoDeputado;
    }
}
