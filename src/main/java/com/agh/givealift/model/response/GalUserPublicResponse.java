package com.agh.givealift.model.response;

import com.agh.givealift.model.entity.GalUser;

import java.util.Date;

public class GalUserPublicResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String gender;
    public Double rate;
    private Date birthYear;


    public GalUserPublicResponse() {
    }

    public GalUserPublicResponse(GalUser galUser) {
        this.firstName = galUser.getFirstName();
        this.lastName = galUser.getLastName();
        this.email = galUser.getEmail();
        this.phone = galUser.getPhone();
        this.gender = galUser.getGender();
        this.rate = galUser.getRate();
        this.birthYear = galUser.getBirthDate();
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

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Date getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Date birthYear) {
        this.birthYear = birthYear;
    }
}
