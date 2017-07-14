package com.example.dkirilova.gym.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dkirilova.gym.R;
import com.example.dkirilova.gym.activities.MainActivity;
import com.example.dkirilova.gym.adapters.ExerciseAdapter;
import com.example.dkirilova.gym.dialog_fragments.EditOrDeleteFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import model.DeserializerJson;
import model.gyms.Exercise;
import model.singleton.FitnessManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import services.ApiService;
import services.RetrofitClient;

public class ExerciseFragment extends Fragment
implements ExerciseAdapter.IExerciseAdapterController,
        MainActivity.IExerciseController{

    private RecyclerView recyclerView;
    private ExerciseAdapter exerciseAdapter = new ExerciseAdapter(this);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);

        // todo call method only once
        loadExercises();
        showExercises();

        return root;
    }

    private void loadExercises(){
        Gson gsonExercise = new GsonBuilder().registerTypeAdapter(List.class, new DeserializerJson<List<Exercise>>("Exercises")).create();
        ApiService apiServiceExercise = RetrofitClient.getRetrofitClient(gsonExercise).create(ApiService.class);
        Call<List<Exercise>> callExercise = apiServiceExercise.getExercises();

        callExercise.enqueue(new Callback<List<Exercise>>() {
            @Override
            public void onResponse(Call<List<Exercise>> call, Response<List<Exercise>> response) {
                List<Exercise> exercises = new ArrayList<>();
                exercises = response.body();
                FitnessManager.getInstance().addExercises(exercises);
                notifyExerciseAdapter(exercises);
            }

            @Override
            public void onFailure(Call<List<Exercise>> call, Throwable t) {
            }
        });
    }

    @Override
    public void editOrDelete(Exercise exercise) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("exercise", exercise);
        EditOrDeleteFragment editOrDeleteFragment = new EditOrDeleteFragment();
        editOrDeleteFragment.setArguments(bundle);
        editOrDeleteFragment.show(getActivity().getSupportFragmentManager(), "fragment");
    }

    @Override
    public void openDetails(Exercise exercise) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("exercise", exercise);
        ExerciseDetailsFragment exerciseDetailsFragment = new ExerciseDetailsFragment();
        exerciseDetailsFragment.setArguments(bundle);
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentContainer, exerciseDetailsFragment).commit();
    }

    public void notifyExerciseAdapter(List<Exercise> exercises){
        exerciseAdapter.setExercises(exercises);
    }

    @Override
    public void showExercises() {
        notifyExerciseAdapter(FitnessManager.getInstance().getAllExercises());
        recyclerView.setAdapter(exerciseAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }

}
