package com.example.dkirilova.gym.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dkirilova.gym.R;
import com.example.dkirilova.gym.activities.DetailsActivity;
import com.example.dkirilova.gym.adapters.ExerciseAdapter;
import com.example.dkirilova.gym.adapters.GymAdapter;
import com.example.dkirilova.gym.dialog_fragments.EditOrDeleteGymFragment;

import java.util.ArrayList;
import java.util.List;

import model.gyms.Exercise;
import model.gyms.Gym;
import model.singleton.FitnessManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import services.ApiService;
import services.RetrofitClient;

public class MainFragment extends Fragment implements GymAdapter.IGymAdapterController, ExerciseAdapter.IExerciseAdapterController{


    private GymAdapter gymsAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);

        gymsAdapter = new GymAdapter(this);
        GymAdapter favouritesGymsAdapter = new GymAdapter(this);
        ExerciseAdapter exerciseAdapter = new ExerciseAdapter(this, FitnessManager.getInstance().getAllExercises());

        ApiService apiService = RetrofitClient.getRetrofitClient().create(ApiService.class);
        Call<List<Gym>> call = apiService.getGyms();
        call.enqueue(new Callback<List<Gym>>() {
            @Override
            public void onResponse(Call<List<Gym>> call, Response<List<Gym>> response) {
                if(response.isSuccessful()) {
                    List<Gym> gyms = new ArrayList<>();
                    gyms = response.body();
                    FitnessManager.getInstance().addGyms(gyms);
                    notifyGymAdapter(gyms);
                }
            }
            @Override
            public void onFailure(Call<List<Gym>> call, Throwable t) {}
        });

        if (getArguments() != null) {
            if (getArguments().getString("recycler_view") != null) {
                if (getArguments().getString("recycler_view").equals("exercises")) {
                    recyclerView.setAdapter(exerciseAdapter);
                } else if (getArguments().getString("recycler_view").equals("all")) {
                    recyclerView.setAdapter(gymsAdapter);
                } else if (getArguments().getString("recycler_view").equals("favourites")) {
                    recyclerView.setAdapter(favouritesGymsAdapter);
                }
            }
        } else {
            recyclerView.setAdapter(gymsAdapter);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        return root;
    }

    @Override
    public void editOrDelete(Gym gym) {

        Bundle bundle = new Bundle();
        bundle.putSerializable("gym", gym);
        EditOrDeleteGymFragment editOrDeleteGymFragment = new EditOrDeleteGymFragment();
        editOrDeleteGymFragment.setArguments(bundle);
        editOrDeleteGymFragment.show(getActivity().getSupportFragmentManager(), "editOrDeleteGymFragment");
    }

    @Override
    public void openDetails(Gym gym) {

        Bundle bundle = new Bundle();
        bundle.putSerializable("gym", gym);
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra("gym", bundle);
        startActivity(intent);
    }

    @Override
    public void editOrDelete(Exercise exercise) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("exercise", exercise);
        EditOrDeleteGymFragment editOrDeleteGymFragment = new EditOrDeleteGymFragment();
        editOrDeleteGymFragment.setArguments(bundle);
        editOrDeleteGymFragment.show(getActivity().getSupportFragmentManager(), "editOrDeleteGymFragment");
    }

    @Override
    public void openDetails(Exercise exercise) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("exercise", exercise);
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra("exercise", bundle);
        startActivity(intent);
    }

    public void notifyGymAdapter(List<Gym> gyms) {
        gymsAdapter.setGyms(gyms);
    }
}
