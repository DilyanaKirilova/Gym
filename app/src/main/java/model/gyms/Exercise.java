package model.gyms;

import java.io.Serializable;
import java.util.UUID;

import model.validators.Validator;

/**
 * Created by dkirilova on 7/5/2017.
 */

public class Exercise implements Serializable{
    private int duration;
    private int level;
    private String image;
    private String id;
    private String name;
    private String instructor;
    private String description;

    public Exercise(int duration, int experienceLevel, String name, String instructor, String description) {

        this.id = UUID.randomUUID().toString();

        if(duration > 0){
            this.duration = duration;
        }
        if(experienceLevel > 0 && experienceLevel <= 10) {
            this.level = experienceLevel;
        }
        if(Validator.isValidString(name)) {
            this.name = name;
        }
        if(Validator.isValidString(instructor)) {
            this.instructor = instructor;
        }

        if(Validator.isValidString(description)) {
            this.description = description;
        }
    }

    public void setDuration(int duration) {
        if(duration > 0){
            this.duration = duration;
        }
    }

    public void setExperienceLevel(int experienceLevel) {
        if(experienceLevel > 0 && experienceLevel <= 10) {
            this.level = experienceLevel;
        }
    }

    public void setId(String id) {
        if(Validator.isValidString(id)){
            this.id = id;
        }
    }

    public void setName(String name) {
        if(Validator.isValidString(name)) {
            this.name = name;
        }
    }

    public void setInstructor(String instructor) {
        if(Validator.isValidString(instructor)){
            this.instructor = instructor;
        }
    }

    public void setDescription(String description) {
        if(Validator.isValidString(description)){
            this.description = description;
        }
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public int getDuration() {
        return duration;
    }

    public String getInstructor() {
        return instructor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Exercise exercise = (Exercise) o;

        if (getDuration() != exercise.getDuration()) return false;
        if (getLevel() != exercise.getLevel()) return false;
        if (getImage() != null ? !getImage().equals(exercise.getImage()) : exercise.getImage() != null)
            return false;
        if (!getName().equals(exercise.getName())) return false;
        if (!getInstructor().equals(exercise.getInstructor())) return false;
        return getDescription().equals(exercise.getDescription());

    }

    @Override
    public int hashCode() {
        int result = getDuration();
        result = 31 * result + getLevel();
        result = 31 * result + (getImage() != null ? getImage().hashCode() : 0);
        result = 31 * result + getName().hashCode();
        result = 31 * result + getInstructor().hashCode();
        result = 31 * result + getDescription().hashCode();
        return result;
    }
}


