package com.example.dkirilova.gym.dialog_fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dkirilova.gym.R;


public class TakeOrSelectPhotoFragment extends DialogFragment {

    private Button btnTakePhoto;
    private Button btnSelectPhoto;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_take_or_select_photo, container, false);

        btnTakePhoto = (Button) root.findViewById(R.id.btnTakePhoto);
        btnSelectPhoto = (Button) root.findViewById((R.id.btnSelectPhoto));

        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo open camera
            }
        });

        btnSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo open gallery
            }
        });

        return root;
    }
}
