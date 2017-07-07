package com.example.dkirilova.gym.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dkirilova.gym.R;
import com.example.dkirilova.gym.adapters.ExerciseAdapter;
import com.example.dkirilova.gym.adapters.GymAdapter;

import model.singleton.FitnessManager;

public class MainFragment extends Fragment {

    private RecyclerView recyclerView;
    private GymAdapter gymAdapter;
    private ExerciseAdapter exerciseAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerViewGym);

        if (getActivity() instanceof AppCompatActivity) {

            gymAdapter = new GymAdapter((AppCompatActivity) getActivity(), FitnessManager.getInstance().getAllGyms());
            recyclerView.setAdapter(gymAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

            if(getArguments() != null) {
                Bundle bundle = getArguments();

                if (bundle.getString("replace_fragment") != null && bundle.getString("replace_fragment").equals("exercises")) {
                    exerciseAdapter = new ExerciseAdapter((AppCompatActivity) getActivity(), FitnessManager.getInstance().getAllExercises());
                    recyclerView.setAdapter(exerciseAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                }
            }
        }
        return root;
    }
}
