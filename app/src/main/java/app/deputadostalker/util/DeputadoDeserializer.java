package app.deputadostalker.util;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import app.deputadostalker.deputado.dominio.Deputado;

/**
 * Created by Delando on 02/05/2016.
 */
public class DeputadoDeserializer implements JsonDeserializer<Object> {
    @Override
    public Object deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement deputado = json.getAsJsonObject();
        Log.i("TAG", "json:   " + json.getAsJsonObject().getAsString());
        Log.i("TAG", "chega aqui???");
        if (json.getAsJsonObject().get("deputado") != null) {
            deputado = json.getAsJsonObject().get("deputado");
        }


        return (new Gson().fromJson(deputado, Deputado.class));
    }
}
