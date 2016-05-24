package app.deputadostalker.frequencia.api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import app.deputadostalker.frequencia.dominio.Frequencia;

/**
 * Created by Delando on 22/05/2016.
 */
public class FrequenciaDes implements JsonDeserializer<Object> {

    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement frequencia = json.getAsJsonObject();
        if (frequencia.getAsJsonObject().get("")!= null){
            frequencia = frequencia.getAsJsonObject().get("");
        }
        Log.i("DEBUG",frequencia.toString());
        return (new Gson().fromJson( frequencia , Frequencia.class));
    }
}
