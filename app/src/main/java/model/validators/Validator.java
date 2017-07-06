package model.validators;

import android.widget.EditText;

/**
 * Created by dkirilova on 7/5/2017.
 */

public class Validator {
    public static final String LATITUDE_LONGITUDE_REGEX = "^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?),\\s*[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$";
    public final static String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    public static boolean isValidString(String string){
        return string != null && !string.trim().isEmpty();
    }

    public static boolean isEmptyField(String txt, EditText et){
        if (txt.trim().isEmpty()) {
            et.setError("Please fill out this field");
            et.requestFocus();
            return true;
        }
        return false;
    }

    //todo regex for phone number (Contact)
}
