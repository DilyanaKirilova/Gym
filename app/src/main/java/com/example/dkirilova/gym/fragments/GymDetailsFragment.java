package com.example.dkirilova.gym.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.dkirilova.gym.R;
import com.example.dkirilova.gym.activities.MainActivity;

import java.util.ArrayList;

import model.gyms.Contact;
import model.gyms.Exercise;
import model.gyms.Gym;
import model.singleton.FitnessManager;
import model.validators.Validator;

import static com.example.dkirilova.gym.ViewHelper.changeStateEditable;
import static com.example.dkirilova.gym.ViewHelper.takePhoto;

public class GymDetailsFragment extends Fragment {


    private ImageView ivImage;
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
    private ArrayList<EditText> eTexts = new ArrayList<>();

    //todo recyclerview for exercises

    private Button btnSelectPhoto;
    private Button btnSaveChanges;
    private Gym gym;
    private Bundle bundle = new Bundle();

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_gym_details, container, false);

        ivImage = (ImageView) root.findViewById(R.id.ivDGImage);
        btnSelectPhoto = (Button) root.findViewById(R.id.btnDGAddPhoto);
        btnSaveChanges = (Button) root.findViewById(R.id.btnDGSave);
        etName = (EditText) root.findViewById(R.id.etDGName);
        etAddress = (EditText) root.findViewById(R.id.etDGAddress);
        etCapacity = (EditText) root.findViewById(R.id.etCapacity);
        etCurrentCapacity = (EditText) root.findViewById(R.id.etCurrentCapacity);
        etContactPerson = (EditText) root.findViewById(R.id.etCPerson);
        etContactAddress = (EditText) root.findViewById(R.id.etCAddress);
        etContactEmail = (EditText) root.findViewById(R.id.etCEmail);
        etContactPhoneNum = (EditText) root.findViewById(R.id.etCPhone);
        etDescription = (EditText) root.findViewById(R.id.etDGDescription);

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
        btnSelectPhoto.setVisibility(View.GONE);
        btnSaveChanges.setVisibility(View.GONE);

        if (getArguments() != null) {
            bundle = getArguments();

            if (bundle.getSerializable("gym") != null || bundle.getString("edit") != null) {
                gym = (Gym) getArguments().getSerializable("gym");
                setGymData();
            }
            if(bundle.getSerializable("gym") == null || bundle.getString("edit") != null){
                changeStateEditable(eTexts, true);
                btnSelectPhoto.setVisibility(View.VISIBLE);
                btnSaveChanges.setVisibility(View.VISIBLE);
            }
        }

        btnSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto(gym, getActivity());
            }
        });

        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(gym != null){
                    FitnessManager.getInstance().delete(gym);
                    Toast.makeText(getActivity(), "DELETED", Toast.LENGTH_SHORT).show();
                }

                gym = new Gym();
                name = etName.getText().toString().trim();
                address = etAddress.getText().toString().trim();
                description = etDescription.getText().toString().trim();
                contactPerson = etContactPerson.getText().toString().trim();
                contactAddress = etContactAddress.getText().toString().trim();
                contactEmail = etContactEmail.getText().toString().trim();
                contactPhoneNum = etContactPhoneNum.getText().toString().trim();

                if (etCapacity.getText().toString().isEmpty()) {
                    etCapacity.setError("..");
                    etCapacity.requestFocus();
                } else {
                    capacity = Integer.valueOf(etCapacity.getText().toString());
                }

                if (etCurrentCapacity.getText().toString().isEmpty()) {
                    etCurrentCapacity.setError("..");
                    etCurrentCapacity.requestFocus();
                } else {
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

    private void setGymData(){
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
