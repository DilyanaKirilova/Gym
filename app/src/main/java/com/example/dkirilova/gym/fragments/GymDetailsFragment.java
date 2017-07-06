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
import com.example.dkirilova.gym.dialog_fragments.TakeOrSelectPhotoFragment;

import model.Contact;
import model.Gym;
import model.GymManager;
import model.validators.Validator;

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

    //todo recyclerview for exercises

    private Button btnAddPhoto;
    private Button btnSave;
    private Gym gym = new Gym();

    public GymDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_gym_details, container, false);

        if (getArguments() != null) {
                gym = (Gym) getArguments().getSerializable("gym");
        }

        btnAddPhoto = (Button) root.findViewById(R.id.btnDGAddPhoto);
        btnSave = (Button) root.findViewById(R.id.btnDGSave);
        etName = (EditText) root.findViewById(R.id.etDGName);
        etAddress = (EditText) root.findViewById(R.id.etDGAddress);
        etCapacity = (EditText) root.findViewById(R.id.etCapacity);
        etCurrentCapacity = (EditText) root.findViewById(R.id.etCurrentCapacity);
        etContactPerson = (EditText) root.findViewById(R.id.etCPerson);
        etContactAddress = (EditText) root.findViewById(R.id.etCAddress);
        etContactEmail = (EditText) root.findViewById(R.id.etCEmail);
        etContactPhoneNum = (EditText) root.findViewById(R.id.etCPhone);
        etDescription = (EditText) root.findViewById(R.id.etDGDescription);

        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("gym", gym);
                TakeOrSelectPhotoFragment takeOrSelectPhotoFragment = new TakeOrSelectPhotoFragment();
                takeOrSelectPhotoFragment.setArguments(bundle);
                takeOrSelectPhotoFragment.show(getActivity().getSupportFragmentManager(), "selectImageFragment");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = etName.getText().toString().trim();
                address = etAddress.getText().toString().trim();
                description = etDescription.getText().toString().trim();
                contactPerson = etContactPerson.getText().toString().trim();
                contactAddress = etContactAddress.getText().toString().trim();
                contactEmail = etContactEmail.getText().toString().trim();
                contactPhoneNum = etContactPhoneNum.getText().toString().trim();

                if(etCapacity.getText().toString().isEmpty()){
                    etCapacity.setError("..");
                    etCapacity.requestFocus();
                } else{
                    capacity = Integer.valueOf(etCapacity.getText().toString());
                }

                if(etCurrentCapacity.getText().toString().isEmpty()){
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
                        Validator.isEmptyField(contactPhoneNum, etContactPhoneNum))){



                    contact = new Contact(contactAddress, contactPhoneNum, contactEmail, contactPerson);
                    gym.setName(name);
                    gym.setAddress(address);
                    gym.setCapacity(capacity);
                    gym.setCurrentCapacity(currentCapacity);
                    gym.setDescription(description);
                    gym.setContact(contact);
                    // add to gym list

                    GymManager.getInstance().addGym(gym);
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, new GymFragment()).commit();
                }
            }
        });

        return root;
    }


}
