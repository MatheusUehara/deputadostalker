package app.deputadostalker.comissao.api;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import app.deputadostalker.partido.dominio.Partido;

/**
 * Created by igormlgomes on 10/05/16.
 */
public class ComissaoDes implements JsonDeserializer<Object> {

    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement comissao = json.getAsJsonObject();
        if (comissao.getAsJsonObject().get("comissao")!= null){
            comissao = comissao.getAsJsonObject().get("comissao");
        }
        return (new Gson().fromJson( comissao , Partido.class));
    }

}
