package com.example.dkirilova.gym.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.dkirilova.gym.R;
import com.example.dkirilova.gym.activities.MainActivity;
import com.example.dkirilova.gym.adapters.AvailabilityAdapter;
import com.example.dkirilova.gym.adapters.ExerciseAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import model.HttpDataHandler;
import model.gyms.Contact;
import model.gyms.Exercise;
import model.gyms.Gym;
import model.singleton.FitnessManager;
import model.validators.Validator;

import static com.example.dkirilova.gym.ViewHelper.changeStateEditable;
import static com.example.dkirilova.gym.ViewHelper.takePhoto;

public class GymDetailsFragment extends Fragment
        implements ExerciseAdapter.IExerciseAdapterController{

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
    private ImageButton ibAddExercise;
    private ImageButton ibAddAvailability;
    private Button btnSaveChanges;
    ImageView ivSelectPhoto;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_gym_details, container, false);
        ivSelectPhoto = (ImageView) root.findViewById(R.id.ivDGAddPhoto);
        btnSaveChanges = (Button) root.findViewById(R.id.btnDGSave);
        ibAddExercise = (ImageButton) root.findViewById(R.id.ibAddExercise);
        ibAddAvailability = (ImageButton) root.findViewById(R.id.ibAddAvailability);
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
        RecyclerView rvAvailabilities = (RecyclerView) root.findViewById(R.id.rvAvailabilities);

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

            if (bundle.getSerializable("gym") != null) {
                gym = (Gym) getArguments().getSerializable("gym");
                setGymData();
                newExercises.addAll(gym.getExercises());

                final AvailabilityAdapter availabilityAdapter = new AvailabilityAdapter();
                availabilityAdapter.setAvailabilities(gym.getAvailabilities());
                rvAvailabilities.setAdapter(availabilityAdapter);
                rvAvailabilities.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            }
            if (bundle.getSerializable("gym") == null || bundle.getString("edit") != null) {
                changeStateEditable(eTexts, true);
                ivSelectPhoto.setVisibility(View.VISIBLE);
                ibAddExercise.setVisibility(View.VISIBLE);
                btnSaveChanges.setVisibility(View.VISIBLE);
            }

            if (bundle.getSerializable("array") != null) {
                newExercises = (ArrayList<Exercise>) bundle.getSerializable("array");
            }

        }

        ExerciseAdapter exerciseAdapter = new ExerciseAdapter(this);
        exerciseAdapter.setExercises(FitnessManager.getInstance().getAllExercises());
        rvExercises.setAdapter(exerciseAdapter);
        rvExercises.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

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

                fragmentTransaction.replace(R.id.fragmentContainer, exerciseDetailsFragment).commit();
            }
        });

        ibAddAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("gym", gym);
                AvailabilityDetailsFragment availabilityDetailsFragment = new AvailabilityDetailsFragment();
                availabilityDetailsFragment.setArguments(bundle);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragmentContainer, availabilityDetailsFragment).commit();
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

                    new getCoordinates().execute(address.replace(" ", " "));

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        return root;
    }

    private void setGymData() {
        //todo set image
        if (gym != null) {
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

    @Override
    public void editOrDelete(Exercise exercise) {
    }

    @Override
    public void openDetails(Exercise exercise) {

    }

    private class getCoordinates extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog = new ProgressDialog(getActivity());

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Please wait...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            String response;
            try {
                String address = params[0];
                HttpDataHandler httpDataHandler = new HttpDataHandler();
                String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?address=%s", address);
                response = httpDataHandler.getHttpData(url);
                return response;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                String lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry").
                        getJSONObject("location").get("lat").toString();
                String lng = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry").
                        getJSONObject("location").get("lng").toString();

                gym.setLatitude(Double.valueOf(lat));
                gym.setLongitude(Double.valueOf(lng));

                Toast.makeText(getActivity(), "Coordinates long  " + lng + "   lat  " + lat, Toast.LENGTH_LONG).show();

                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
