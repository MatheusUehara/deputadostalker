package app.deputadostalker.deputado.api;

import java.util.List;

import app.deputadostalker.deputado.dominio.Deputado;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by igormlgomes on 10/05/16.
 */
public interface DeputadoAPI {
    @GET("deputado/getDeputados/")
    Call<List<Deputado>> getDeputados();
}
