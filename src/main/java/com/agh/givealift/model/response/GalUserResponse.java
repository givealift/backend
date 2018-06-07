package com.agh.givealift.model.response;

import com.agh.givealift.model.entity.GalUser;

import java.util.Date;

public class GalUserResponse {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String gender;
    private Date birthYear;
    private String address;
    private Double rate;
    private String description;

    public GalUserResponse(GalUser galUser) {
        this.firstName = galUser.getFirstName();
        this.lastName = galUser.getLastName();
        this.email = galUser.getEmail();
        this.phone = galUser.getPhone();
        this.gender = galUser.getGender();
        this.address = galUser.getAddress();
        this.birthYear = galUser.getBirthDate();
        this.rate = galUser.getRate();
        this.description = galUser.getDescription();
    }

    public GalUserResponse() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public Date getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Date birthYear) {
        this.birthYear = birthYear;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

