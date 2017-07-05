package model.validators;

/**
 * Created by dkirilova on 7/5/2017.
 */

public class Validator {
    public static final String LATITUDE_LONGITUDE_REGEX = "^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?),\\s*[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$";

    public static boolean isValidString(String string){
        return string != null && !string.trim().isEmpty();
    }

    //todo regex for phone number and email (Contact)
}
