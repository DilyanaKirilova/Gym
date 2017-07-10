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

import java.util.List;

import model.gyms.Gym;
import model.singleton.FitnessManager;

public class MainFragment extends Fragment {

    private RecyclerView recyclerView;
    private GymAdapter gymAdapter;
    private ExerciseAdapter exerciseAdapter;
    private List<Gym> gyms;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerViewGym);

        if (getActivity() instanceof AppCompatActivity) {

            if(getArguments() != null) {
                if(getArguments().getString("recycler_view") != null){
                    if(getArguments().getString("recycler_view") != null){
                        if(FitnessManager.getInstance().getFavourites() != null) {
                            gyms = FitnessManager.getInstance().getFavourites();
                        }
                    }
                }else {
                    gyms = FitnessManager.getInstance().getAllGyms();
                }

            }
            else{
                gyms = FitnessManager.getInstance().getAllGyms();
            }
            gymAdapter = new GymAdapter((AppCompatActivity) getActivity(), gyms);
            recyclerView.setAdapter(gymAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        }
        return root;
    }
}
