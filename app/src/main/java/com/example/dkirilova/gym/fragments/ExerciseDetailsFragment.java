package com.example.dkirilova.gym.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.dkirilova.gym.R;
import com.example.dkirilova.gym.activities.MainActivity;
import com.example.dkirilova.gym.adapters.GymAdapter;

import java.util.ArrayList;

import model.gyms.Exercise;
import model.gyms.Gym;
import model.singleton.FitnessManager;
import model.validators.Validator;

import static com.example.dkirilova.gym.ViewHelper.changeStateEditable;


public class ExerciseDetailsFragment extends Fragment implements GymAdapter.IGymAdapterController{

    private EditText etName;
    private EditText etLevel;
    private EditText etDescription;
    private EditText etInstructor;
    private EditText etDuration;
    private Exercise exercise;
    private ArrayList<EditText> eTexts = new ArrayList<>();
    private RecyclerView recyclerView;
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
        recyclerView = (RecyclerView) root.findViewById(R.id.rvGyms);

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
            if (bundle.getSerializable("exercise") != null) {
                exercise = (Exercise) getArguments().getSerializable("exercise");
                setExerciseData();
            }
        }

        GymAdapter gymAdapter = new GymAdapter(this);
        gymAdapter.setGyms(FitnessManager.getInstance().getGyms(exercise));
        recyclerView.setAdapter(gymAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

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

                    Exercise exercise = new Exercise(null, duration, level, name, instructor, description);
                    ArrayList<Exercise> exercises = new ArrayList<>();
                    if (getArguments() != null) {
                        if (getArguments().getSerializable("array") != null) {
                            exercises = (ArrayList<Exercise>) getArguments().getSerializable("array");
                            exercises.add(exercise);
                        }
                        if(getArguments().getSerializable("gym") != null) {
                            Gym gym = (Gym) getArguments().getSerializable("gym");
                            gym.setExercise(exercise);
                        }
                    }
                    GymDetailsFragment gymDetailsFragment = new GymDetailsFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("array", exercises);
                    gymDetailsFragment.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, gymDetailsFragment).commit();
                }
            }
        });

        return root;
    }

    private void setExerciseData() {
        if(exercise != null) {
            etName.setText(exercise.getName());
            etLevel.setText(String.valueOf(exercise.getLevel()));
            etDescription.setText(exercise.getDescription());
            etDuration.setText(String.valueOf(exercise.getDuration()));
            etInstructor.setText(exercise.getInstructor());
        }
    }

    // empty
    @Override
    public void editOrDelete(Gym gym) {

    }

    @Override
    public void openDetails(Gym gym) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("gym", gym);
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra("gym", bundle);
        getActivity().startActivity(intent);
    }
}
