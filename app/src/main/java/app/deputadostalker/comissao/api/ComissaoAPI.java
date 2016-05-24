package app.deputadostalker.comissao.api;

import java.util.List;

import app.deputadostalker.comissao.dominio.Comissao;
import app.deputadostalker.partido.dominio.Partido;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by igormlgomes on 10/05/16.
 */
public interface ComissaoAPI {

    @GET("comissoes/getComissoes/")
    Call<List<Comissao>> getComissoes();
}
