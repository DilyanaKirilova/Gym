package model;

import java.io.Serializable;

import model.validators.Validator;

/**
 * Created by dkirilova on 7/5/2017.
 */

public class Availability implements Serializable{
    private int startTime;
    private int duration;
    private String dayName;

    public Availability(int startTime, int duration, String dayName) {

        if(startTime >= 0 && startTime < 2400){
            this.startTime = startTime;
        }

        if(duration > 0 && duration < 2400) {
            this.duration = duration;
        }

        if( Validator.isValidString(dayName)){
            this.dayName = dayName;
        }
    }

    public void setStartTime(int startTime) {
        if(startTime >= 0 && startTime < 2400){
            this.startTime = startTime;
        }
    }

    public void setDuration(int duration) {
        if(duration > 0 && duration < 2400) {
            this.duration = duration;
        }
    }

    public void setDayName(String dayName) {
        if( Validator.isValidString(dayName)){
            this.dayName = dayName;
        }
    }
}
