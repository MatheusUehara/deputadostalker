package app.deputadostalker.api;

import java.util.List;

import app.deputadostalker.deputado.dominio.Deputado;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Delando on 30/04/2016.
 */

//interface criada para gerenciar os metodos de requisição.
public interface DeputadoApi {
    @GET("deputado/{metodo}")
    Call<List<Deputado>> getDeputados(@Path("metodo") String metodo);
    //@Path("metodo") String metodo

    /*@FormUrlEncoded
    @POST("deputado")
    Call<List<Deputado>> getDeputados(@Field("metodo") String metodo);
*/
}
