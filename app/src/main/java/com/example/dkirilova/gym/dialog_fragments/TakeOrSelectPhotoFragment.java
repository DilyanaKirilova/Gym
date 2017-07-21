package com.example.dkirilova.gym.dialog_fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.dkirilova.gym.R;

import java.io.IOException;

import model.gyms.Gym;
import model.validators.Validator;

import static android.app.Activity.RESULT_OK;


public class TakeOrSelectPhotoFragment extends DialogFragment {


    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private View root;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_take_or_select_photo, container, false);

        Button btnTakePhoto = (Button) root.findViewById(R.id.btnTakePhoto);
        Button btnSelectPhoto = (Button) root.findViewById((R.id.btnSelectPhoto));

        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
                dismiss();
            }
        });

        btnSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPhotoFromGallery();
                dismiss();
            }
        });

        return root;
    }

    private void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void selectPhotoFromGallery() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            String strUri = data.getData().toString();
            ImageView imageView = (ImageView) root.findViewById(R.id.img);

            setImage(strUri, imageView);

            if (getArguments() != null) {
                Gym gym = (Gym) getArguments().getSerializable("gym");
                if (gym != null) {
                    gym.setImage(strUri);
                }
            }
        }
    }

    public void setImage(String strUri, ImageView imageView) {

        if (imageView == null || !Validator.isValidString(strUri)) {
            return;
        }
        Uri uri = Uri.parse(strUri);

        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

