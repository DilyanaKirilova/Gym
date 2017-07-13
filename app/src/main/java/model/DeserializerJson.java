package model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import model.gyms.Gym;

import static android.R.id.list;

/**
 * Created by dkirilova on 7/13/2017.
 */

public class DeserializerJson<T> implements JsonDeserializer<List<T>>{

    private String value;
    public DeserializerJson(String value){
        this.value = value;
    }
    @Override
    public List<T> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Type type = ((ParameterizedType) typeOfT).getActualTypeArguments()[0];
        List<T> list = new ArrayList<>();
        JsonElement jsonElement = json.getAsJsonObject().get(value);
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        for (int i = 0; i < jsonArray.size(); i++) {
            T el = (T) new Gson().fromJson(jsonArray.get(i), type);
            list.add(el);
        }
        return list;
    }
}
