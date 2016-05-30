package app.deputadostalker.partido.api;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import app.deputadostalker.partido.dominio.Partido;

/**
 * Created by Matheus Uehara on 06/05/2016.
 */
public class PartidoDes implements JsonDeserializer<Object> {

    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement partido = json.getAsJsonObject();
        if (partido.getAsJsonObject().get("")!= null){
            partido = partido.getAsJsonObject().get("");
        }
        return (new Gson().fromJson( partido , Partido.class));
    }
}