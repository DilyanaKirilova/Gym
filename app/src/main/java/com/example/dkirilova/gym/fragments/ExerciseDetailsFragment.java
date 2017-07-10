package com.example.dkirilova.gym.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.dkirilova.gym.R;

import java.util.ArrayList;

import model.gyms.Exercise;
import model.validators.Validator;

import static com.example.dkirilova.gym.ViewHelper.changeStateEditable;


public class ExerciseDetailsFragment extends Fragment {

    private EditText etName;
    private EditText etLevel;
    private EditText etDescription;
    private EditText etInstructor;
    private EditText etDuration;
    private ArrayList<EditText> eTexts = new ArrayList<>();

    //todo recycler view for gyms
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_exercise_details, container, false);

        etName = (EditText) root.findViewById(R.id.etEDName);
        etLevel = (EditText) root.findViewById(R.id.etEDLevel);
        etDescription = (EditText) root.findViewById(R.id.etEDDescription);
        etDuration = (EditText) root.findViewById(R.id.etEDDuration);
        etInstructor = (EditText) root.findViewById(R.id.etEDInstructor);
        Button btnSaveChanges = (Button) root.findViewById(R.id.btnEDSave);
        ImageView ivSelectPhoto = (ImageView) root.findViewById(R.id.ivEDSelectPhoto);

        eTexts.add(etName);
        eTexts.add(etLevel);
        eTexts.add(etDescription);
        eTexts.add(etDuration);
        eTexts.add(etInstructor);

        changeStateEditable(eTexts, false);
        ivSelectPhoto.setVisibility(View.GONE);
        btnSaveChanges.setVisibility(View.GONE);

        if (getArguments() != null) {

            Bundle bundle = getArguments();

            if (bundle.getString("edit") != null || getActivity().getIntent().getStringExtra("edit") != null) {
                changeStateEditable(eTexts, true);
                ivSelectPhoto.setVisibility(View.VISIBLE);
                btnSaveChanges.setVisibility(View.VISIBLE);
            }
        }

        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = etName.getText().toString().trim();
                String description = etDescription.getText().toString().trim();
                String instructor = etInstructor.getText().toString().trim();
                int duration = 0;
                int level = 0;
                if (!etLevel.getText().toString().isEmpty()) {
                    level = Integer.valueOf(etLevel.getText().toString());
                }

                if (!etDuration.getText().toString().isEmpty()) {
                    level = Integer.valueOf(etLevel.getText().toString());
                }

                if (!Validator.isEmptyField(name, etName) && !Validator.isEmptyField(description, etDescription)
                        && !Validator.isEmptyField(String.valueOf(level), etLevel) &&
                        !Validator.isEmptyField(String.valueOf(duration), etDuration) &&
                        !Validator.isEmptyField(instructor, etInstructor)) {

                    Exercise exercise = new Exercise(duration, level, name, instructor, description);

                    ArrayList<Exercise> exercises = new ArrayList<Exercise>();
                    if (getArguments() != null) {
                        if (getArguments().getSerializable("array") != null) {
                            exercises = (ArrayList<Exercise>) getArguments().getSerializable("array");
                            exercises.add(exercise);
                        }
                    }
                    GymDetailsFragment gymDetailsFragment = new GymDetailsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("array", exercises);
                    gymDetailsFragment.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainerDetails, gymDetailsFragment).commit();
                }
            }
        });

        return root;
    }
}
