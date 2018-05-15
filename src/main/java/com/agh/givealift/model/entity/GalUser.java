package com.agh.givealift.model.entity;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
public class GalUser {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long galUserId;

    @NonNull
    private String password;
    @Column(unique = true)
    private String facebookId;
    private String firstName;
    @Column(unique = true, nullable = false)
    @Email
    private String email;
    private String lastName;
    private Integer birthYear;
    private String address;
    private String phone;
    private Double rate;
    private Long rateAmount;
    private String gender;
    private String role;
    @Basic(fetch = FetchType.LAZY)
    private byte[] photo;


    public GalUser() {
    }

    public GalUser(String password, String firstName, String lastName, String email, String phone, String gender, Integer birthYear, String address) {

        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.birthYear = birthYear;
        this.address = address;
    }

    public Long getGalUserId() {
        return galUserId;
    }

    public void setGalUserId(Long galUserId) {
        this.galUserId = galUserId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public String getAddress() {
        return address;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "GalUser{" +
                "galUserId=" + galUserId +
                ", password='" + password + '\'' +
                ", facebookId='" + facebookId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }

    public Long getRateAmount() {
        return rateAmount;
    }

    public void setRateAmount(Long rateAmount) {
        this.rateAmount = rateAmount;
    }
}
