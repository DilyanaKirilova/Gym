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
import com.example.dkirilova.gym.fragments.MainFragment;

import model.gyms.Exercise;
import model.gyms.Gym;
import model.singleton.FitnessManager;

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
            if (getArguments().getSerializable("gym") != null) {
                gym = (Gym) getArguments().getSerializable("gym");
            } else if (getArguments().getSerializable("exercise") != null) {
                exercise = (Exercise) getArguments().getSerializable("exercise");
            }
        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                if (gym != null) {
                    GymDetailsFragment gymDetailsFragment = new GymDetailsFragment();
                    gymDetailsFragment.setArguments(getArguments());
                    fragmentTransaction.replace(R.id.fragmentContainer, gymDetailsFragment).commit();
                } else if (exercise != null) {
                    ExerciseDetailsFragment exerciseDetailsFragment = new ExerciseDetailsFragment();
                    exerciseDetailsFragment.setArguments(getArguments());
                    fragmentTransaction.replace(R.id.fragmentContainer, exerciseDetailsFragment).commit();
                }

                dismiss();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainFragment mainFragment = new MainFragment();
                Bundle bundle = new Bundle();
                bundle.putString("replace_fragment", "exercises");
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                if (gym != null) {
                    FitnessManager.getInstance().delete(gym);
                    bundle.putString("replace_fragment", "gym");
                } else if (exercise != null) {
                    FitnessManager.getInstance().delete(exercise);
                    bundle.putString("replace_fragment", "exercises");
                }
                mainFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragmentContainer, mainFragment).commit();
                dismiss();
            }
        });
        return root;
    }


}
