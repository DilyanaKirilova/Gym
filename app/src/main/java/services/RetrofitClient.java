package services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by dkirilova on 7/12/2017.
 */

public class RetrofitClient {

    public static final String BASE_URL = "https://demo7551154.mockable.io";
    public static Retrofit retrofit = null;

    public static Retrofit getRetrofitClient(){

        if(retrofit == null){

            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
