package app.deputadostalker.deputado.service;

import app.deputadostalker.deputado.dao.DeputadoDAO;
import app.deputadostalker.deputado.dominio.Deputado;

/**
 * Created by matheusuehara on 04/05/16.
 */
public class DeputadoService {

    private static final DeputadoService instance = new DeputadoService();

    DeputadoDAO deputadoDAO = DeputadoDAO.getInstance();

    private DeputadoService() {
        super();
    }
    public static DeputadoService getInstance() {
        return instance;
    }

    public Deputado getDeputado(int ideCadastro){
        return deputadoDAO.getDeputado(ideCadastro);
    }
}
