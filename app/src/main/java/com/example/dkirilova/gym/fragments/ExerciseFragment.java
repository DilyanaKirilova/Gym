package com.example.dkirilova.gym.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dkirilova.gym.R;
import com.example.dkirilova.gym.activities.MainActivity;
import com.example.dkirilova.gym.adapters.ExerciseAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
        implements ExerciseAdapter.IExerciseAdapterController {

    private RecyclerView recyclerView;
    private ExerciseAdapter exerciseAdapter = new ExerciseAdapter(this);

    public interface IExerciseController {
        void showExercises();
    }

    IExerciseController iExerciseController = new IExerciseController() {
        @Override
        public void showExercises() {
            notifyExerciseAdapter(FitnessManager.getInstance().getAllExercises());
            recyclerView.setAdapter(exerciseAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recycler_view, container, false);

        ((MainActivity) getActivity()).setIExerciseController(iExerciseController);
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        loadExercises();
        iExerciseController.showExercises();

        return root;
    }

    private void loadExercises() {
        Gson gsonExercise = new GsonBuilder().registerTypeAdapter(List.class, new DeserializerJson<List<Exercise>>("Exercises")).create();
        ApiService apiServiceExercise = RetrofitClient.getRetrofitClient(gsonExercise).create(ApiService.class);
        Call<List<Exercise>> callExercise = apiServiceExercise.getExercises();

        callExercise.enqueue(new Callback<List<Exercise>>() {
            @Override
            public void onResponse(@NonNull Call<List<Exercise>> call, @NonNull Response<List<Exercise>> response) {
                List<Exercise> exercises;
                exercises = response.body();
                FitnessManager.getInstance().addExercises(exercises);
                notifyExerciseAdapter(exercises);
            }

            @Override
            public void onFailure(@NonNull Call<List<Exercise>> call, @NonNull Throwable t) {
            }
        });
    }

    @Override
    public void editOrDelete(Exercise exercise) {
        ((MainActivity) getActivity()).editOrDelete(null, exercise);
    }

    @Override
    public void openDetails(Exercise exercise) {
        ((MainActivity) getActivity()).openFragment(new ExerciseDetailsFragment(), exercise, "exercise", false, View.NO_ID);
    }

    public void notifyExerciseAdapter(List<Exercise> exercises) {
        exerciseAdapter.setExercises(exercises);
    }
}
