package com.example.dkirilova.gym.dialog_fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dkirilova.gym.R;
import com.example.dkirilova.gym.activities.MainActivity;

import model.gyms.Exercise;
import model.gyms.Gym;
import model.singleton.FitnessManager;

public class EditOrDeleteFragment extends DialogFragment{

    private Gym gym;
    private Exercise exercise;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_edit_or_delete, container, false);

        Button btnEdit = (Button) root.findViewById(R.id.btnEdit);
        Button btnDelete = (Button) root.findViewById(R.id.btnDelete);

        if (getArguments() != null) {
            if (getArguments().getSerializable("gym") != null) {
                gym = (Gym) getArguments().getSerializable("gym");
            } else if (getArguments().getSerializable("exercise") != null) {
                exercise = (Exercise) getArguments().getSerializable("exercise");
            }
        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (gym != null) {
                    if(getActivity() instanceof MainActivity) {
                        ((MainActivity) getActivity()).openGymDetailsFragment(gym);
                    }
                } else if (exercise != null) {
                    if(getActivity() instanceof MainActivity) {
                        ((MainActivity) getActivity()).openExerciseDetailsFragment(exercise);
                    }
                }

                dismiss();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // todo notifyDataSetChanged
                if (gym != null) {
                    FitnessManager.getInstance().delete(gym);

                } else if (exercise != null) {
                    FitnessManager.getInstance().delete(exercise);
                }
                dismiss();
            }
        });
        return root;
    }
}
