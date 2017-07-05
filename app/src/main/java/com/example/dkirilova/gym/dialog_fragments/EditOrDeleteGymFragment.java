package com.example.dkirilova.gym.dialog_fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dkirilova.gym.R;
import com.example.dkirilova.gym.fragments.EditGymFragment;

public class EditOrDeleteGymFragment extends DialogFragment {

    private Button btnEditGym;
    private Button btnDeleteGym;

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

        btnEditGym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo go to edit gym fragment

                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, new EditGymFragment()).commit();
            }
        });

        btnDeleteGym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo delete gym from allGyms
            }
        });
        return root;
    }


}
