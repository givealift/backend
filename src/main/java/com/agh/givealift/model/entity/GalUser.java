package com.agh.givealift.model.entity;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
public class GalUser {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long galUserId;
    @Column(unique = true)
    private String login;
    @NonNull
    private String password;
    @Column(unique = true)
    private String facebookId;
    private String firstName;
    private String lastName;
    @Email
    private String email;
    private String phone;
    private String gender;
    private String role;

    public GalUser() {
    }

    public GalUser(String login, String password, String facebookId, String firstName, String lastName, String email, String phone, String gender) {

        this.password = password;
        this.facebookId = facebookId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
    }

    public Long getGalUserId() {
        return galUserId;
    }

    public void setGalUserId(Long galUserId) {
        this.galUserId = galUserId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    @Override
    public String toString() {
        return "GalUser{" +
                "galUserId=" + galUserId +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", facebookId='" + facebookId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
