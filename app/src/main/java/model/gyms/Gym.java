package model.gyms;

import java.io.Serializable;
import java.util.ArrayList;
import model.validators.Validator;

/**
 * Created by dkirilova on 7/5/2017.
 */

public class Gym implements Serializable {
    private boolean isFavourite;
    private String image;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Gym gym = (Gym) o;

        if (isFavourite() != gym.isFavourite()) return false;
        if (getCurrentCapacity() != gym.getCurrentCapacity()) return false;
        if (getCapacity() != gym.getCapacity()) return false;
        if (Double.compare(gym.latitude, latitude) != 0) return false;
        if (Double.compare(gym.longitude, longitude) != 0) return false;
        if (getImage() != null ? !getImage().equals(gym.getImage()) : gym.getImage() != null)
            return false;
        if (!getName().equals(gym.getName())) return false;
        if (getAddress() != null ? !getAddress().equals(gym.getAddress()) : gym.getAddress() != null)
            return false;
        return getDescription() != null ? getDescription().equals(gym.getDescription()) : gym.getDescription() == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (isFavourite() ? 1 : 0);
        result = 31 * result + (getImage() != null ? getImage().hashCode() : 0);
        result = 31 * result + getCurrentCapacity();
        result = 31 * result + getCapacity();
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + getName().hashCode();
        result = 31 * result + (getAddress() != null ? getAddress().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        return result;
    }

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
    }

    ;

    public Gym(int currentCapacity, int capacity, double latitude, double longitude, String name, String address, String description,
               Contact contact, ArrayList<Availability> availabilities, ArrayList<Exercise> exercises) {

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

    public void setExercises(ArrayList<Exercise> exercises) {
        if (exercises != null) {
            this.exercises = exercises;
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
}