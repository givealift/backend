package com.agh.givealift.model.response;

import com.agh.givealift.model.entity.GalUser;

import java.util.Date;

public class GalUserPublicResponse {
    private long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String gender;
    public Double rate;
    private Date birthYear;
     private String description;
    private String car;


    public GalUserPublicResponse() {
    }

    public GalUserPublicResponse(GalUser galUser) {
        this.userId = galUser.getGalUserId();
        this.firstName = galUser.getFirstName();
        this.lastName = galUser.getLastName();
        this.email = galUser.getEmail();
        this.phone = galUser.getPhone();
        this.gender = galUser.getGender();
        this.rate = galUser.getRate();
        this.birthYear = galUser.getBirthDate();
        this.description = galUser.getDescription();
        this.car = galUser.getCar();
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }
}
