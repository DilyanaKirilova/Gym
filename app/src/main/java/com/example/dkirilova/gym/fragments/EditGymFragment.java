package com.example.dkirilova.gym.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.dkirilova.gym.R;
import com.example.dkirilova.gym.dialog_fragments.TakeOrSelectPhotoFragment;

import model.Gym;

public class EditGymFragment extends Fragment {

    private ImageView ivImage;
    private EditText etName;
    private EditText etAddress;
    private EditText description;

    private Button btnAddPhoto;
    private Gym gym;

    public EditGymFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_edit_gym, container, false);

        if (getArguments() != null) {
            if (getArguments().getSerializable("gym") instanceof Gym) {
                gym = (Gym) getArguments().getSerializable("gym");
            }
        }
        btnAddPhoto = (Button) root.findViewById(R.id.btnAddPhoto);

        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TakeOrSelectPhotoFragment takeOrSelectPhotoFragment = new TakeOrSelectPhotoFragment();
                takeOrSelectPhotoFragment.setArguments(getArguments());
                takeOrSelectPhotoFragment.show(getActivity().getSupportFragmentManager(), "selectImageFragment");
            }
        });

        return root;
    }


}
