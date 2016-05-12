package app.deputadostalker.comissoes.api;

import java.util.List;

import app.deputadostalker.comissoes.dominio.Comissoes;
import app.deputadostalker.partido.dominio.Partido;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by igormlgomes on 10/05/16.
 */
public interface ComissoesAPI {

    @GET("comissoes/getComissoes/")
    Call<List<Comissoes>> getComissoes();
}
