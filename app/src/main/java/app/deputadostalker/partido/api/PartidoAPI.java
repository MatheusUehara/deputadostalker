package app.deputadostalker.partido.api;

import java.util.List;

import app.deputadostalker.partido.dominio.Partido;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Matheus Uehara on 06/05/2016.
 */
public interface PartidoAPI {

    @GET("partido/getPartidos/")
    Call<List<Partido>> getPartidos();
}
