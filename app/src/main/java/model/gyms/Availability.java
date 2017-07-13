package model.gyms;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


/**
 * Created by dkirilova on 7/5/2017.
 */

public class Availability implements Serializable{

    public enum DayOfWeek {
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY,
        THURSDAY, FRIDAY, SATURDAY
    }

    private int startTime;
    private int duration;
    @SerializedName("dayName")
    private String dayOfWeek;
    //private DayOfWeek dayOfWeek;


    public Availability(int startTime, int duration, String dayOfWeek) {

        setStartTime(startTime);
        setDuration(duration);
        setDayOfWeek(dayOfWeek);
    }

    public void setStartTime(int startTime) {
            this.startTime = startTime;
    }


    public void setDuration(int duration) {
        if(duration > 0 && duration < 2400) {
            this.duration = duration;
        }
    }

    public void setDayOfWeek(String  dayOfWeek) {
        if(dayOfWeek != null){
            this.dayOfWeek = dayOfWeek;
        }
    }
}
