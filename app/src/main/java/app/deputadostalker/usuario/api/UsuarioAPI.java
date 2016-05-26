package app.deputadostalker.usuario.api;

import app.deputadostalker.deputado.dominio.Deputado;
import app.deputadostalker.usuario.dominio.Usuario;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by Matheus Uehara on 25/05/2016.
 */
public interface UsuarioAPI {

    @Multipart
    @POST("usuario/")
    Call<Usuario> cadastraUsuario(@Part("id") String id,
                                  @Part("nome") String nome,
                                  @Part("profileUrl") String profileUrl,
                                  @Part("email") String email,
                                  @Part("redeSocial") String redeSocial,
                                  @Part("idTokenPush") String idTokenPush);

}

