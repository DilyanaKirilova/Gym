package com.example.dkirilova.gym.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;


import com.example.dkirilova.gym.R;
import com.example.dkirilova.gym.activities.MainActivity;
import com.example.dkirilova.gym.adapters.ExerciseAdapter;

import java.util.ArrayList;

import model.gyms.Contact;
import model.gyms.Exercise;
import model.gyms.Gym;
import model.singleton.FitnessManager;
import model.validators.Validator;

import static com.example.dkirilova.gym.ViewHelper.changeStateEditable;
import static com.example.dkirilova.gym.ViewHelper.takePhoto;

public class GymDetailsFragment extends Fragment {

    private EditText etName;
    private EditText etAddress;
    private EditText etDescription;
    private EditText etContactEmail;
    private EditText etContactAddress;
    private EditText etContactPhoneNum;
    private EditText etContactPerson;
    private EditText etCapacity;
    private EditText etCurrentCapacity;
    private String name;
    private String address;
    private String description;
    private int capacity;
    private int currentCapacity;
    private String contactAddress;
    private String contactPhoneNum;
    private String contactEmail;
    private String contactPerson;
    private Contact contact;
    private ArrayList<Exercise> newExercises = new ArrayList<>();
    private ArrayList<EditText> eTexts = new ArrayList<>();

    private Gym gym;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_gym_details, container, false);
        ImageView ivSelectPhoto = (ImageView) root.findViewById(R.id.ivDGAddPhoto);
        Button btnSaveChanges = (Button) root.findViewById(R.id.btnDGSave);
        ImageButton ibAddExercise = (ImageButton) root.findViewById(R.id.ibAddExercise);
        etName = (EditText) root.findViewById(R.id.etDGName);
        etAddress = (EditText) root.findViewById(R.id.etDGAddress);
        etCapacity = (EditText) root.findViewById(R.id.etCapacity);
        etCurrentCapacity = (EditText) root.findViewById(R.id.etCurrentCapacity);
        etContactPerson = (EditText) root.findViewById(R.id.etCPerson);
        etContactAddress = (EditText) root.findViewById(R.id.etCAddress);
        etContactEmail = (EditText) root.findViewById(R.id.etCEmail);
        etContactPhoneNum = (EditText) root.findViewById(R.id.etCPhone);
        etDescription = (EditText) root.findViewById(R.id.etDGDescription);
        RecyclerView rvExercises = (RecyclerView) root.findViewById(R.id.rvExercises);

        eTexts.add(etName);
        eTexts.add(etAddress);
        eTexts.add(etDescription);
        eTexts.add(etContactEmail);
        eTexts.add(etContactAddress);
        eTexts.add(etContactPerson);
        eTexts.add(etContactPhoneNum);
        eTexts.add(etCapacity);
        eTexts.add(etCurrentCapacity);

        changeStateEditable(eTexts, false);
        ivSelectPhoto.setVisibility(View.GONE);
        ibAddExercise.setVisibility(View.GONE);
        btnSaveChanges.setVisibility(View.GONE);


        if (getArguments() != null) {
            Bundle bundle = getArguments();

            if (bundle.getSerializable("gym") != null || bundle.getString("edit") != null) {
                gym = (Gym) getArguments().getSerializable("gym");
                setGymData();
                newExercises.addAll(gym.getExercises());
            }
            if (bundle.getSerializable("gym") == null || bundle.getString("edit") != null) {
                changeStateEditable(eTexts, true);
                ivSelectPhoto.setVisibility(View.VISIBLE);
                ibAddExercise.setVisibility(View.VISIBLE);
                btnSaveChanges.setVisibility(View.VISIBLE);
            }

            if(bundle.getSerializable("array") != null){
                newExercises = (ArrayList<Exercise>) bundle.getSerializable("array");
            }

            if (getActivity() instanceof AppCompatActivity) {

                ExerciseAdapter exerciseAdapter = new ExerciseAdapter((AppCompatActivity) getActivity(), newExercises);
                rvExercises.setAdapter(exerciseAdapter);
                rvExercises.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            }
        }

        ivSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto(gym, getActivity());
            }
        });

        ibAddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("edit", "exercise");
                bundle.putSerializable("array", newExercises);
                ExerciseDetailsFragment exerciseDetailsFragment = new ExerciseDetailsFragment();
                exerciseDetailsFragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.fragmentContainerDetails, exerciseDetailsFragment).commit();
            }
        });

        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FitnessManager.getInstance().delete(gym);

                gym = new Gym();
                gym.setExercises(newExercises);
                FitnessManager.getInstance().addExercises(newExercises);
                name = etName.getText().toString().trim();
                address = etAddress.getText().toString().trim();
                description = etDescription.getText().toString().trim();
                contactPerson = etContactPerson.getText().toString().trim();
                contactAddress = etContactAddress.getText().toString().trim();
                contactEmail = etContactEmail.getText().toString().trim();
                contactPhoneNum = etContactPhoneNum.getText().toString().trim();

                if (!etCapacity.getText().toString().isEmpty()) {
                    capacity = Integer.valueOf(etCapacity.getText().toString());
                }

                if (!etCurrentCapacity.getText().toString().isEmpty()) {
                    currentCapacity = Integer.valueOf(etCurrentCapacity.getText().toString());
                }

                // todo add the regex validation
                if (!(Validator.isEmptyField(name, etName) ||
                        Validator.isEmptyField(address, etAddress) ||
                        Validator.isEmptyField(String.valueOf(capacity), etCapacity) ||
                        Validator.isEmptyField(String.valueOf(currentCapacity), etCurrentCapacity) ||
                        Validator.isEmptyField(description, etDescription) ||
                        Validator.isEmptyField(contactPerson, etContactPerson) ||
                        Validator.isEmptyField(contactAddress, etContactAddress) ||
                        Validator.isEmptyField(contactEmail, etContactEmail) ||
                        Validator.isEmptyField(contactPhoneNum, etContactPhoneNum))) {


                    //todo save the image here!

                    contact = new Contact(contactAddress, contactPhoneNum, contactEmail, contactPerson);
                    gym.setName(name);
                    gym.setAddress(address);
                    gym.setCapacity(capacity);
                    gym.setCurrentCapacity(currentCapacity);
                    gym.setDescription(description);
                    gym.setContact(contact);

                    FitnessManager.getInstance().add(gym);

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        return root;
    }

    private void setGymData() {
        //todo set image
        etName.setText(gym.getName());
        etAddress.setText(gym.getAddress());
        etDescription.setText(gym.getDescription());
        etCapacity.setText(String.valueOf(gym.getCapacity()));
        etCurrentCapacity.setText(String.valueOf(gym.getCurrentCapacity()));
        etContactEmail.setText(gym.getContactEmail());
        etContactAddress.setText(gym.getContactAddress());
        etContactPhoneNum.setText(gym.getContactPhoneNumber());
        etContactPerson.setText(gym.getContactPerson());
    }
}
