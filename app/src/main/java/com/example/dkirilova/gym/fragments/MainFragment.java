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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recyclerViewGym);

        //adapters
        GymAdapter gymsAdapter = new GymAdapter((AppCompatActivity) getActivity(), FitnessManager.getInstance().getAllGyms());
        GymAdapter favouritesGymsAdapter = new GymAdapter((AppCompatActivity) getActivity(), FitnessManager.getInstance().getFavourites());;
        ExerciseAdapter exerciseAdapter = new ExerciseAdapter((AppCompatActivity) getActivity(), FitnessManager.getInstance().getAllExercises());

        if (getActivity() instanceof AppCompatActivity) {

            recyclerView.setAdapter(gymsAdapter);
            if (getArguments() != null) {

                if (getArguments().getString("replace_fragment") != null) {
                    if (getArguments().getString("replace_fragment").equals("exercises")) {
                        recyclerView.setAdapter(exerciseAdapter);
                    } else {
                        //fragment map
                    }
                }
                if (getArguments().getString("recycler_view") != null) {
                    if (getArguments().getString("recycler_view").equals("favourites")) {
                        if (FitnessManager.getInstance().getFavourites() != null) {
                           recyclerView.setAdapter(favouritesGymsAdapter);
                        }
                    }
                }
            }
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        }
        return root;
    }
}
