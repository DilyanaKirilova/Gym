package model;

import model.validators.Validator;

/**
 * Created by dkirilova on 7/5/2017.
 */

public class Contact {
    private String address;
    private String phoneNumber;
    private String email;
    private String contactPerson;

    public Contact(String address, String phoneNumber, String email, String contactPerson) {
        if(Validator.isValidString(address)){
            this.address = address;
        }
        if(Validator.isValidString(phoneNumber)){
            this.phoneNumber = phoneNumber;
        }
        if(Validator.isValidString(email)){
            this.email = email;
        }
        if(Validator.isValidString(contactPerson)) {
            this.contactPerson = contactPerson;
        }
    }

    public void setAddress(String address) {
        if(Validator.isValidString(address)){
            this.address = address;
        }
    }

    public void setPhoneNumber(String phoneNumber) {
        if(Validator.isValidString(phoneNumber)){
            this.phoneNumber = phoneNumber;
        }
    }

    public void setEmail(String email) {
        if(Validator.isValidString(email)){
            this.email = email;
        }
    }

    public void setContactPerson(String contactPerson) {
        if(Validator.isValidString(contactPerson)){
            this.contactPerson = contactPerson;
        }
    }
}
