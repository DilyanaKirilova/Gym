package com.example.dkirilova.gym.dialog_fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dkirilova.gym.R;
import com.example.dkirilova.gym.fragments.ExerciseDetailsFragment;
import com.example.dkirilova.gym.fragments.GymDetailsFragment;
import com.example.dkirilova.gym.fragments.GymFragment;

import model.Exercise;
import model.Gym;
import model.GymManager;

public class EditOrDeleteGymFragment extends DialogFragment {

    private Button btnEdit;
    private Button btnDelete;
    private Gym gym;
    private Exercise exercise;

    public EditOrDeleteGymFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_edit_or_delete, container, false);

        btnEdit = (Button) root.findViewById(R.id.btnEdit);
        btnDelete = (Button) root.findViewById(R.id.btnDelete);

        if (getArguments() != null) {
            if (getArguments().getSerializable("gym") instanceof Gym) {
                gym = (Gym) getArguments().getSerializable("gym");
            }
            else if(getArguments().getSerializable("exercise") instanceof Exercise){
                exercise = (Exercise) getArguments().getSerializable("exercise");
            }
        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                if(gym != null) {
                    fragmentTransaction.replace(R.id.fragmentContainer, new GymDetailsFragment()).commit();
                }
                else if(exercise != null){
                    fragmentTransaction.replace(R.id.fragmentContainer, new ExerciseDetailsFragment()).commit();
                }

                dismiss();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                if (gym != null) {
                    GymManager.getInstance().deleteGym(gym);
                }
                else if(exercise != null){
                    // todo delete exercise
                    // todo choose different adapters
                }
                fragmentTransaction.replace(R.id.fragmentContainer, new GymFragment()).commit();
                dismiss();
            }
        });
        return root;
    }


}
