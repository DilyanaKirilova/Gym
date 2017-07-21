package model.gyms;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Availability implements Serializable{

    public enum DayOfWeek {
        SUNDAY, MONDAY, TUESDAY, WEDNESDAY,
        THURSDAY, FRIDAY, SATURDAY
    }
    @SerializedName("startTime")
    private int startTime;
    @SerializedName("duration")
    private int duration;
    @SerializedName("dayName")
    private String dayOfWeek;


    public Availability(int startTime, int duration, String dayOfWeek) {

        setStartTime(startTime);
        setDuration(duration);
        setDayOfWeek(dayOfWeek);
    }

    private void setStartTime(int startTime) {
            this.startTime = startTime;
    }


    private void setDuration(int duration) {
        if(duration > 0 && duration < 2400) {
            this.duration = duration;
        }
    }

    private void setDayOfWeek(String dayOfWeek) {
        if(dayOfWeek != null){
            dayOfWeek = dayOfWeek.toUpperCase();
            for(DayOfWeek dayName : DayOfWeek.values()){
                if(dayOfWeek.equals(dayName.toString())){
                    this.dayOfWeek = dayOfWeek;
                    break;
                }
            }
        }
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Availability that = (Availability) o;

        return getDayOfWeek().equals(that.getDayOfWeek());
    }

    @Override
    public int hashCode() {
        return getDayOfWeek().hashCode();
    }
}
