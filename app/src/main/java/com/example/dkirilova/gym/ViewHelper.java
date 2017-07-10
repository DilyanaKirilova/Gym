package com.example.dkirilova.gym;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.method.KeyListener;
import android.widget.EditText;

import com.example.dkirilova.gym.dialog_fragments.TakeOrSelectPhotoFragment;

import java.util.ArrayList;

import model.gyms.Exercise;
import model.gyms.Gym;

/**
 * Created by dkirilova on 7/7/2017.
 */

public class ViewHelper {

    public static void changeStateEditable(ArrayList<EditText> editTexts, Boolean flag) {

        if (flag) {
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

    public static void takePhoto(Gym gym, FragmentActivity activity){

        Bundle bundle = new Bundle();
        bundle.putSerializable("gym", gym);
        TakeOrSelectPhotoFragment takeOrSelectPhotoFragment = new TakeOrSelectPhotoFragment();
        takeOrSelectPhotoFragment.setArguments(bundle);
        takeOrSelectPhotoFragment.show(activity.getSupportFragmentManager(), "selectImageFragment");
    }
    public static void takePhoto(Exercise exercise, FragmentActivity activity){

        Bundle bundle = new Bundle();
        bundle.putSerializable("exercise", exercise);
        TakeOrSelectPhotoFragment takeOrSelectPhotoFragment = new TakeOrSelectPhotoFragment();
        takeOrSelectPhotoFragment.setArguments(bundle);
        takeOrSelectPhotoFragment.show(activity.getSupportFragmentManager(), "selectImageFragment");
    }
}
