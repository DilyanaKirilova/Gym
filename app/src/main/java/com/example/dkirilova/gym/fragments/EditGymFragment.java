package com.example.dkirilova.gym.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dkirilova.gym.R;
import com.example.dkirilova.gym.dialog_fragments.TakeOrSelectPhotoFragment;

public class EditGymFragment extends Fragment {

    private Button btnAddPhoto;
    public EditGymFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_edit_gym, container, false);

        btnAddPhoto = (Button) root.findViewById(R.id.btnAddPhoto);

        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TakeOrSelectPhotoFragment selectImageFragment = new TakeOrSelectPhotoFragment();
                selectImageFragment.show(getActivity().getSupportFragmentManager(), "selectImageFragment");
            }
        });

        return root;
    }


}
