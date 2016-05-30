package app.deputadostalker.frequencia.api;

import java.util.List;

import app.deputadostalker.frequencia.dominio.Frequencia;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Matheus Uehara on 23/05/2016.
 */
public interface FrequenciaAPI {

    @GET("deputado/frequencia/de={dataInicial}/ate={dataFinal}/deputadoMatricula={matricula}")
    Call<List<Frequencia>> getFrequencia(@Path("dataInicial") String dataInicial,
                                         @Path("dataFinal") String dataFinal,
                                         @Path("matricula") int matricula );

}

