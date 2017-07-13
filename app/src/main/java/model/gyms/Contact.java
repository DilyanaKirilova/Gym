package model.gyms;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import model.validators.Validator;

/**
 * Created by dkirilova on 7/5/2017.
 */

public class Contact implements Serializable{
    @SerializedName("address")
    private String address;
    @SerializedName("phoneNumber")
    private String phoneNumber;
    @SerializedName("email")
    private String email;
    @SerializedName("contactPerson")
    private String person;

    public Contact(String address, String phoneNumber, String email, String contactPerson) {

        setAddress(address);
        setPhoneNumber(phoneNumber);
        setEmail(email);
        setContactPerson(contactPerson);
    }

    private void setAddress(String address) {
        if(Validator.isValidString(address)){
            this.address = address;
        }
    }

    private void setPhoneNumber(String phoneNumber) {
        if(Validator.isValidString(phoneNumber)){
            this.phoneNumber = phoneNumber;
        }
    }

    private void setEmail(String email) {
        if(Validator.isValidString(email)){
            this.email = email;
        }
    }

    private void setContactPerson(String contactPerson) {
        if(Validator.isValidString(contactPerson)){
            this.person = contactPerson;
        }
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPerson() {
        return person;
    }
}
