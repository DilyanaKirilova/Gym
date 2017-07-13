package model.gyms;

import com.google.gson.annotations.SerializedName;

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

    @SerializedName("currentCapacity")
    private int currentCapacity;
    @SerializedName("capacity")
    private int capacity;
    @SerializedName("latitude")
    private double latitude;
    @SerializedName("longitude")
    private double longitude;
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("address")
    private String address;
    @SerializedName("description")
    private String description;
    @SerializedName("contact")
    private Contact contact;
    @SerializedName("availabilities")
    private ArrayList<Availability> availabilities;
    @SerializedName("exercises")
    private ArrayList<Exercise> exercises;

    public Gym() {
        this.id = UUID.randomUUID().toString();
        this.exercises = new ArrayList<>();
    }

    public Gym(int currentCapacity, int capacity, double latitude, double longitude, String id, String name, String address, String description, Contact contact, ArrayList<Availability> availabilities, ArrayList<Exercise> exercises) {
        setId(id);
        setName(name);
        setCurrentCapacity(currentCapacity);
        setCapacity(capacity);
        setLatitude(latitude);
        setLongitude(longitude);
        setAddress(address);
        setDescription(description);
        setContact(contact);
        setAvailabilities(availabilities);
        setExercises(exercises);
    }

    ;

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

    /*
    public void setLatitude(double latitude) {
        if (String.valueOf(latitude).matches(Validator.LATITUDE_LONGITUDE_REGEX)) {
            this.latitude = latitude;
        }
    }

    public void setLongitude(double longitude) {
        if (String.valueOf(longitude).matches(Validator.LATITUDE_LONGITUDE_REGEX)) {
            this.longitude = longitude;
        }
    }*/

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
            this.longitude = longitude;
    }

    public void setId(String id) {
        if (Validator.isValidString(id)) {
            this.id = id;
        }else{
            this.id = UUID.randomUUID().toString();
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

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
