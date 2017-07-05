package com.example.dkirilova.gym.dialog_fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dkirilova.gym.R;
import com.example.dkirilova.gym.fragments.EditGymFragment;
import com.example.dkirilova.gym.fragments.GymFragment;

import model.Gym;
import model.GymManager;

public class EditOrDeleteGymFragment extends DialogFragment {

    private Button btnEditGym;
    private Button btnDeleteGym;
    private Gym gym;

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
        View root = inflater.inflate(R.layout.fragment_edit_or_delete_gym, container, false);

        btnEditGym = (Button) root.findViewById(R.id.btnEditGym);
        btnDeleteGym = (Button) root.findViewById(R.id.btnDeleteGym);

        if (getArguments() != null) {
            if (getArguments().getSerializable("gym") instanceof Gym) {
                gym = (Gym) getArguments().getSerializable("gym");
            }
        }

        btnEditGym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, new EditGymFragment()).commit();
                dismiss();
            }
        });

        btnDeleteGym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (gym != null) {
                    GymManager.getInstance().deleteGym(gym);
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, new GymFragment()).commit();
                    dismiss();
                }
            }
        });
        return root;
    }


}
