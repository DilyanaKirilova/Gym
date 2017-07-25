package com.example.dkirilova.gym.fragments;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.KeyListener;
import android.util.DisplayMetrics;
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
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import model.gyms.Contact;
import model.gyms.Exercise;
import model.gyms.Gym;
import model.singleton.FitnessManager;
import model.validators.Validator;

import static android.app.Activity.RESULT_OK;

public class GymDetailsFragment extends Fragment
        implements ExerciseAdapter.IExerciseAdapterController {

    private static final int PICK_IMAGE_REQUEST = 150;
    private static final int REQUEST_IMAGE_CAPTURE = 160;
    private static final int CAMERA_PERMISSIONS_REQUESTS = 170;
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 180;

    public void getData() {
        name = etName.getText().toString().trim();
        address = etAddress.getText().toString().trim();
        description = etDescription.getText().toString().trim();
        contactPerson = etContactPerson.getText().toString().trim();
        contactAddress = etContactAddress.getText().toString().trim();
        contactEmail = etContactEmail.getText().toString().trim();
        contactPhoneNum = etContactPhoneNum.getText().toString().trim();
    }

    public interface IGymDetailsController {
        void editGym();
    }

    IGymDetailsController iGymDetailsController = new IGymDetailsController() {
        @Override
        public void editGym() {
            changeStateEditable(eTexts, true);
            ivSelectPhoto.setVisibility(View.VISIBLE);
            ibAddExercise.setVisibility(View.VISIBLE);
            ibAddAvailability.setVisibility(View.VISIBLE);
            btnSaveChanges.setVisibility(View.VISIBLE);
        }
    };

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

    private Gym gym;
    private ImageButton ibAddExercise;
    private ImageButton ibAddAvailability;
    private Button btnSaveChanges;
    private ImageView ivSelectPhoto;
    private Uri imageUri;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        ((MainActivity) getActivity()).setIGymDetailsController(iGymDetailsController);

        View root = inflater.inflate(R.layout.fragment_gym_details, container, false);
        ivSelectPhoto = (ImageView) root.findViewById(R.id.ivDGAddPhoto);
        btnSaveChanges = (Button) root.findViewById(R.id.btnDGSave);
        ibAddExercise = (ImageButton) root.findViewById(R.id.ibAddExercise);
        ibAddAvailability = (ImageButton) root.findViewById(R.id.ibAddAvailability);
        ImageButton ibPlaceAutocomplete = (ImageButton) root.findViewById(R.id.autocomplete);
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
        ibAddAvailability.setVisibility(View.GONE);
        btnSaveChanges.setVisibility(View.GONE);


        if (getArguments() != null) {
            Bundle bundle = getArguments();

            if (bundle.getSerializable("gym") != null) {
                gym = (Gym) getArguments().getSerializable(getString(R.string.gym));
                setGymData();

                final AvailabilityAdapter availabilityAdapter = new AvailabilityAdapter();
                availabilityAdapter.setAvailabilities(gym.getAvailabilities());
                rvAvailabilities.setAdapter(availabilityAdapter);
                rvAvailabilities.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

                ExerciseAdapter exerciseAdapter = new ExerciseAdapter(this);
                exerciseAdapter.setExercises(gym.getExercises());
                rvExercises.setAdapter(exerciseAdapter);
                rvExercises.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            }
        }

        ivSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });

        ibAddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
                setData(gym);
                ((MainActivity) getActivity()).openFragment(new ExerciseDetailsFragment(), gym, getString(R.string.gym), false, View.NO_ID);

            }
        });

        ibPlaceAutocomplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).build(getActivity());
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        ibAddAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
                setData(gym);
                ((MainActivity) getActivity()).openFragment(new AvailabilityDetailsFragment(), gym,getString(R.string.gym), false, View.NO_ID);
            }
        });

        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getData();
                if (!etCapacity.getText().toString().isEmpty()) {
                    capacity = Integer.valueOf(etCapacity.getText().toString());
                }

                if (!etCurrentCapacity.getText().toString().isEmpty()) {
                    currentCapacity = Integer.valueOf(etCurrentCapacity.getText().toString());
                }

                if (!(Validator.isEmptyField(name, etName) ||
                        Validator.isEmptyField(address, etAddress) ||
                        Validator.isEmptyField(String.valueOf(capacity), etCapacity) ||
                        Validator.isEmptyField(String.valueOf(currentCapacity), etCurrentCapacity) ||
                        Validator.isEmptyField(description, etDescription) ||
                        Validator.isEmptyField(contactPerson, etContactPerson) ||
                        Validator.isEmptyField(contactAddress, etContactAddress) ||
                        Validator.isEmptyField(contactEmail, etContactEmail) ||
                        Validator.isEmptyField(contactPhoneNum, etContactPhoneNum))) {

                    contact = new Contact(contactAddress, contactPhoneNum, contactEmail, contactPerson);

                    setData(gym);
                    gym.setLatLong(getContext());
                    FitnessManager.getInstance().add(gym);
                    ((MainActivity) getActivity()).openGymFragment();
                }
            }
        });
        return root;
    }

    private void showAlertDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.choose_photo);
        builder.setPositiveButton(R.string.gellery, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent();
                intent.setType(getString(R.string.image_type));
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture)), PICK_IMAGE_REQUEST);
            }
        });

        builder.setNegativeButton(R.string.camera, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                            ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUESTS);
                    } else {
                        takePicture();
                    }
                } else {
                    takePicture();
                }
            }
        });
        builder.show();
    }

    private void setData(Gym gym) {
        gym.setName(name);
        gym.setAddress(address);
        gym.setCapacity(capacity);
        gym.setCurrentCapacity(currentCapacity);
        gym.setDescription(description);
        contact = new Contact(contactAddress, contactPhoneNum, contactEmail, contactPerson);
        gym.setContact(contact);
    }


    private void setGymData() {
        ivSelectPhoto.setVisibility(View.VISIBLE);
        if (gym != null) {
            if (gym.getName() == null) {
                changeStateEditable(eTexts, true);
                ibAddExercise.setVisibility(View.VISIBLE);
                ibAddAvailability.setVisibility(View.VISIBLE);
                btnSaveChanges.setVisibility(View.VISIBLE);
                return;
            }
            setImage(gym.getImage(), ivSelectPhoto);
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

    public void setImage(String strUri, ImageView imageView) {
        if (imageView == null || !Validator.isValidString(strUri)) {
            return;
        }
        Uri uri = Uri.parse(strUri);
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int height = displayMetrics.heightPixels / 2;
            int width = displayMetrics.widthPixels;
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            String strUri = data.getData().toString();
            setImage(strUri, ivSelectPhoto);
            gym.setImage(strUri);

        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            String strUri = imageUri.toString();
            setImage(strUri, ivSelectPhoto);
            gym.setImage(strUri);
        } else if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE && resultCode == RESULT_OK) {
            Place place = PlaceAutocomplete.getPlace(getActivity(), data);
            Toast.makeText(getActivity(), "" + place, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSIONS_REQUESTS) {
            if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                takePicture();
            }
        }
    }

    private void takePicture() {
        Calendar cal = Calendar.getInstance();
        File file = new File(Environment.getExternalStorageDirectory(), (cal.getTimeInMillis() + ".jpg"));
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        imageUri = Uri.fromFile(file);

        Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        i.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(i, REQUEST_IMAGE_CAPTURE);
    }

    public static void changeStateEditable(ArrayList<EditText> editTexts, Boolean editable) {
        if (editable) {
            for (EditText editText : editTexts) {
                editText.setKeyListener((KeyListener) editText.getTag());
            }
        } else {
            for (EditText editText : editTexts) {
                editText.setTag(editText.getKeyListener());
                editText.setKeyListener(null);
            }
        }
    }
}
