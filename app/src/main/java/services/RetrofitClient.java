package services;

import com.google.gson.Gson;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;

public class RetrofitClient {

    private static final String BASE_URL = "https://demo6072753.mockable.io";
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofitClient(Gson gson) {
        okhttp3.OkHttpClient.Builder builder = new okhttp3.OkHttpClient.Builder();
        builder.addInterceptor(new HttpLoggingInterceptor().setLevel(BODY));
        okhttp3.OkHttpClient client = builder.build();
        if (retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
