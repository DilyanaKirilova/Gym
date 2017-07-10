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

import java.util.ArrayList;

import static com.example.dkirilova.gym.ViewHelper.changeStateEditable;


public class ExerciseDetailsFragment extends Fragment {


    private ImageView ivImage;
    private EditText etName;
    private EditText etLevel;
    private EditText etDescription;
    private Button btnSaveChanges;
    private Button btnSelectPhoto;
    private ArrayList<EditText> eTexts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_exercise_details, container, false);

        ivImage = (ImageView) root.findViewById(R.id.ivEDImage);
        etName = (EditText) root.findViewById(R.id.etEDName);
        etLevel = (EditText) root.findViewById(R.id.etEDLevel);
        etDescription = (EditText) root.findViewById(R.id.etEDDescription);
        btnSaveChanges = (Button) root.findViewById(R.id.btnEDSave);
        btnSelectPhoto = (Button) root.findViewById(R.id.btnEDSelectPhoto);

        eTexts.add(etName);
        eTexts.add(etLevel);
        eTexts.add(etDescription);

        changeStateEditable(eTexts, false);
        btnSelectPhoto.setVisibility(View.GONE);
        btnSaveChanges.setVisibility(View.GONE);

        if (getArguments() != null) {

            Bundle b = getArguments();
            if (b.getString("edit") != null) {
                changeStateEditable(eTexts, true);
                btnSelectPhoto.setVisibility(View.VISIBLE);
                btnSaveChanges.setVisibility(View.VISIBLE);
            }
            if(getActivity().getIntent().getStringExtra("edit") != null){
                changeStateEditable(eTexts, true);
                btnSelectPhoto.setVisibility(View.VISIBLE);
                btnSaveChanges.setVisibility(View.VISIBLE);
            }
        }

        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo
            }
        });

        return root;
    }
}
