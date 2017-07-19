package com.example.dkirilova.gym.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dkirilova.gym.R;
import com.example.dkirilova.gym.activities.MainActivity;
import com.example.dkirilova.gym.adapters.GymAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import model.DeserializerJson;
import model.gyms.Gym;
import model.singleton.FitnessManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import services.ApiService;
import services.RetrofitClient;

public class GymFragment extends Fragment
        implements GymAdapter.IGymAdapterController {

    private GymAdapter gymsAdapter;
    private IGymController iGymController = new IGymController() {
        @Override
        public void showFavouritesGyms() {
            notifyGymAdapter(FitnessManager.getInstance().getFavourites());
        }

        @Override
        public void showAllGyms() {
            notifyGymAdapter(FitnessManager.getInstance().getAllGyms());
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        gymsAdapter = new GymAdapter(this);
        recyclerView.setAdapter(gymsAdapter);
        List<Gym> allGyms = FitnessManager.getInstance().getAllGyms();
        if (allGyms.size() == 0) {
            loadGyms();
        } else {
            notifyGymAdapter(allGyms);
        }
        ((MainActivity) getActivity()).setiGymController(iGymController);

        return root;
    }

    private void loadGyms() {
        Gson gsonGym = new GsonBuilder().registerTypeAdapter(List.class, new DeserializerJson<List<Gym>>("Gyms")).create();
        ApiService apiServiceGym = RetrofitClient.getRetrofitClient(gsonGym).create(ApiService.class);
        Call<List<Gym>> callGym = apiServiceGym.getGyms();

        callGym.enqueue(new Callback<List<Gym>>() {
            @Override
            public void onResponse(Call<List<Gym>> call, Response<List<Gym>> response) {
                if (response.isSuccessful()) {
                    List<Gym> gyms;
                    gyms = response.body();
                    for (Gym gym : gyms) {
                        FitnessManager.getInstance().addExercises(gym.getExercises());
                    }
                    FitnessManager.getInstance().addGyms(gyms);
                    notifyGymAdapter(gyms);
                }
            }

            @Override
            public void onFailure(Call<List<Gym>> call, Throwable t) {
            }
        });
    }

    @Override
    public void editOrDelete(Gym gym) {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).openEditOrDeleteFragment(gym);
        }
    }

    @Override
    public void openDetails(Gym gym) {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).openGymDetailsFragment(gym);
        }
    }

    public void notifyGymAdapter(List<Gym> gyms) {
        gymsAdapter.setGyms(gyms);
    }

    public interface IGymController {
        void showFavouritesGyms();

        void showAllGyms();
    }

}
