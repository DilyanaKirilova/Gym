package model.gyms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import model.validators.Validator;

/**
 * Created by dkirilova on 7/5/2017.
 */

public class Gym implements Serializable {
    private boolean isFavourite;
    private String image;

    private int currentCapacity;
    private int capacity;
    private double latitude;
    private double longitude;
    private String id;
    private String name;
    private String address;
    private String description;
    private Contact contact;
    private ArrayList<Availability> availabilities;
    private ArrayList<Exercise> exercises;

    public Gym() {
        this.id = UUID.randomUUID().toString();
        this.exercises = new ArrayList<>();
    }

    ;

    /*
    public Gym(int currentCapacity, int capacity, double latitude, double longitude, String name, String address, String description,
               Contact contact, ArrayList<Availability> availabilities, ArrayList<Exercise> exercises) {

        this.id = UUID.randomUUID().toString();

        if (Validator.isValidString(name)) {
            this.name = name;
        }

        if (Validator.isValidString(address)) {
            this.address = address;
        }

        if (String.valueOf(latitude).matches(Validator.LATITUDE_LONGITUDE_REGEX)) {
            this.latitude = latitude;
        }

        if (String.valueOf(longitude).matches(Validator.LATITUDE_LONGITUDE_REGEX)) {
            this.longitude = longitude;
        }

        if (capacity > 0) {
            this.capacity = capacity;
        }
        if (currentCapacity > 0 && currentCapacity <= capacity) {
            this.currentCapacity = currentCapacity;
        }

        if (Validator.isValidString(description)) {
            this.description = description;
        }

        if (contact != null) {
            this.contact = contact;
        }

        if (availabilities != null) {
            this.availabilities = availabilities;
        }

        if (exercises != null) {
            this.exercises = exercises;
        }
    }
    */

    public void setCurrentCapacity(int currentCapacity) {
        if (currentCapacity > 0 && currentCapacity <= this.capacity) {
            this.currentCapacity = currentCapacity;
        }
    }

    public void setCapacity(int capacity) {
        if (capacity > 0) {
            this.capacity = capacity;
        }
    }

    public void setLatitude(double latitude) {
        if (String.valueOf(latitude).matches(Validator.LATITUDE_LONGITUDE_REGEX)) {
            this.latitude = latitude;
        }
    }

    public void setLongitude(double longitude) {
        if (String.valueOf(longitude).matches(Validator.LATITUDE_LONGITUDE_REGEX)) {
            this.longitude = longitude;
        }
    }

    public void setId(String id) {
        if (Validator.isValidString(id)) {
            this.id = id;
        }
    }

    public void setName(String name) {
        if (Validator.isValidString(name)) {
            this.name = name;
        }
    }

    public void setAddress(String address) {
        if (Validator.isValidString(address)) {
            this.address = address;
        }
    }

    public void setDescription(String description) {
        if (Validator.isValidString(description)) {
            this.description = description;
        }
    }

    public void setContact(Contact contact) {
        if (contact != null) {
            this.contact = contact;
        }
    }

    public void setAvailabilities(ArrayList<Availability> availabilities) {
        if (availabilities != null) {
            this.availabilities = availabilities;
        }
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setIsFavourite(boolean isFavourite) {
        this.isFavourite = isFavourite;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {

        //if(Validator.isValidString(image)){
        this.image = image;
        //}
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCurrentCapacity() {
        return currentCapacity;
    }

    public String getContactEmail(){
        if (this.contact != null) {
            return contact.getEmail();
        }
        return null;
    }

    public String getContactAddress() {
        if (this.contact != null) {
            return contact.getAddress();
        }
        return null;
    }

    public String getContactPhoneNumber() {
        if (this.contact != null) {
            return contact.getPhoneNumber();
        }
        return null;
    }

    public String getContactPerson() {
        if (this.contact != null) {
            return contact.getPerson();
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Gym gym = (Gym) o;

        return id.equals(gym.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public List<Exercise> getExercises() {
        return Collections.unmodifiableList(exercises);
    }

    public void setExercises(ArrayList<Exercise> exercises) {
        if(exercises != null) {
            this.exercises = exercises;
        }
    }

    public void removeExercise(Exercise exercise) {
        this.exercises.remove(exercise);
    }
}
