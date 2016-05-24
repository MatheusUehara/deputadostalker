package app.deputadostalker.comissao.service;

import java.util.ArrayList;

import app.deputadostalker.comissao.dao.ComissaoDAO;
import app.deputadostalker.comissao.dominio.Comissao;

/**
 * Created by Matheus Uehara on 22/05/2016.
 */
public class ComissaoService {

    ComissaoDAO comissaoDAO = ComissaoDAO.getInstance();

    private static final ComissaoService instance = new ComissaoService();

    private ComissaoService() {
        super();
    }

    public static ComissaoService getInstance() {
        return instance;
    }

    public ArrayList<Comissao> getComissoes(int ideCadastro){
        return comissaoDAO.getComissoes(ideCadastro);
    }
}
