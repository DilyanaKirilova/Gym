package com.example.dkirilova.gym.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dkirilova.gym.R;
import com.example.dkirilova.gym.adapters.GymAdapter;

import model.GymManager;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GymFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GymFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GymFragment extends Fragment {

    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_gym, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerViewGym);

        if(getActivity() instanceof AppCompatActivity) {
            GymAdapter gymAdapter = new GymAdapter((AppCompatActivity) getActivity(), GymManager.getInstance().getAllGyms());
            recyclerView.setAdapter(gymAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        }

        return root;
    }
}
