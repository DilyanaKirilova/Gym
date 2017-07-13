package services;

import java.util.List;

import model.gyms.Exercise;
import model.gyms.Gym;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by dkirilova on 7/12/2017.
 */

public interface ApiService {

    @GET("/getGyms")
    Call<List<Gym>> getGyms();

    @GET("getExercises")
    Call<List<Exercise>> getExercises();
}
