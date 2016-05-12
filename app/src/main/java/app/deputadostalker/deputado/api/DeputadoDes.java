package app.deputadostalker.deputado.api;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import app.deputadostalker.deputado.dominio.Deputado;

/**
 * Created by igormlgomes on 10/05/16.
 */

public class DeputadoDes implements JsonDeserializer<Object> {

    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement deputado = json.getAsJsonObject();
        if (deputado.getAsJsonObject().get("deputado")!= null){
            deputado = deputado.getAsJsonObject().get("deputado");
        }
        return (new Gson().fromJson( deputado , Deputado.class));
    }
}
