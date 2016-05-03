package app.deputadostalker.api;

import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Delando on 03/05/2016.
 */

//classe do okhttp que faz a requisição a uma url e retorna como string o conteudo da pagina enviado.
public class Client {


    OkHttpClient client = new OkHttpClient();

    public String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        //Log.i("LOG",response.body().string());
        return response.body().string();
    }

}
