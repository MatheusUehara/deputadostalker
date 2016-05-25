package app.deputadostalker.usuario.api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import app.deputadostalker.frequencia.dominio.Frequencia;
import app.deputadostalker.usuario.dominio.Usuario;

/**
 * Created by Matheus Uehara on 25/05/2016.
 */
public class UsuarioDes implements JsonDeserializer<Object> {

    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement usuario = json.getAsJsonObject();
        if (usuario.getAsJsonObject().get("") != null) {
            usuario = usuario.getAsJsonObject().get("");
        }
        Log.i("DEBUG", usuario.toString());
        return (new Gson().fromJson(usuario, Usuario.class));
    }
}