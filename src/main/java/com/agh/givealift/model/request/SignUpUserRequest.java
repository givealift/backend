package com.agh.givealift.model.request;

import com.agh.givealift.model.entity.GalUser;

import java.util.Date;


public class SignUpUserRequest {


    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String gender;
    private Date birthYear;
    private String address;
    private Double rate;
    private String description;
    private String car;

    public GalUser mapToGalUserWithoutPassword(GalUser user) {
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setGender(gender);
        user.setAddress(address);
        user.setBirthDate(birthYear);
        user.setRate(rate);
        user.setDescription(description);
        user.setCar(car);

        return user;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
