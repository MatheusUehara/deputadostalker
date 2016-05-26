package app.deputadostalker.proposicao.api;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import app.deputadostalker.partido.dominio.Partido;
import app.deputadostalker.proposicao.dominio.Proposicao;

/**
 * Created by igormlgomes on 25/05/16.
 */
public class ProposicaoDes  implements JsonDeserializer<Object> {
    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement proposicao = json.getAsJsonObject();
        if (proposicao.getAsJsonObject().get("proposicao")!= null){
            proposicao = proposicao.getAsJsonObject().get("proposicao");
        }
        return (new Gson().fromJson( proposicao , Proposicao.class));
    }
}
