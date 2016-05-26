package app.deputadostalker.deputado.api;

import java.util.List;

import app.deputadostalker.deputado.dominio.Deputado;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by igormlgomes on 10/05/16.
 */
public interface DeputadoAPI {
    @GET("deputado/")
    Call<List<Deputado>> getDeputados();


    @GET("deputado/maisBuscados")
    Call<List<Deputado>> maisBuscados();

    @Multipart
    @POST("deputado/addBusca")
    Call<Deputado> addBusca(@Part("ideCadastro") int ideCadastro);
}
