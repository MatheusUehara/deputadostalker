package app.deputadostalker.proposicao.api;

import java.util.List;

import app.deputadostalker.proposicao.dominio.Proposicao;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by igormlgomes on 25/05/16.
 */
public interface ProposicaoAPI {

    @GET("proposicao/{ideCadastro}")
    Call<List<Proposicao>> getProposicoes(@Path("ideCadastro") int ideCadastro);


}
