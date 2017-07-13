package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import model.GymDeserializer;
import model.gyms.Gym;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;

/**
 * Created by dkirilova on 7/12/2017.
 */

public class RetrofitClient {

    public static final String BASE_URL = "https://demo7551154.mockable.io";
    public static Retrofit retrofit = null;

    public static Retrofit getRetrofitClient() {
        okhttp3.OkHttpClient.Builder builder = new okhttp3.OkHttpClient.Builder();
        builder.addInterceptor(new HttpLoggingInterceptor().setLevel(BODY));
        okhttp3.OkHttpClient client = builder.build();
        if (retrofit == null) {

            Gson gson = new GsonBuilder().registerTypeAdapter(Gym.class, new GymDeserializer<Gym>()).
                    registerTypeAdapter(List.class, new GymDeserializer<List<Gym>>()).create();

            retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
