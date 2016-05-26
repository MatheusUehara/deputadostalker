package app.deputadostalker.proposicao.service;

import java.util.ArrayList;

import app.deputadostalker.proposicao.dao.ProposicaoDAO;
import app.deputadostalker.proposicao.dominio.Proposicao;

/**
 * Created by igormlgomes on 25/05/16.
 */
public class ProposicaoService {

    ProposicaoDAO proposicaoDAO = ProposicaoDAO.getInstance();

    private static final ProposicaoService instance = new ProposicaoService();

    private ProposicaoService() {
        super();
    }

    public static ProposicaoService getInstance() {
        return instance;
    }

    public ArrayList<Proposicao> getProposicoes(int idProposicao){
        return proposicaoDAO.getProposicoes(idProposicao);
    }


    public ArrayList<String> getProposicao (int idProposicao){
        return proposicaoDAO.getProposicao(idProposicao);
    }

}
