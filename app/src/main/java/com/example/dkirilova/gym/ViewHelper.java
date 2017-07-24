package com.example.dkirilova.gym;

import android.text.method.KeyListener;
import android.widget.EditText;

import java.util.ArrayList;


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
}
