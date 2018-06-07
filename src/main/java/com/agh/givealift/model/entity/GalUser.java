package com.agh.givealift.model.entity;



import com.agh.givealift.service.init.UniqueLogin;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.util.Date;

@Entity
public class GalUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long galUserId;

 @NotNull(message = "Pole Hasło nie może być puste")
    private String password;
    @Column(unique = true)
    private String facebookId;
    private String firstName;
   // @UniqueLogin(message = "Podany email istnieje w bazie")
    @Column(unique = true,nullable = false)
    @Email(message = "Niepoprawny format email")
    private String email;
    private String lastName;
    private Date birthDate;
    private String address;
  @Pattern(regexp="[\\d]{6,10}",message = "Niepoprawny format numeru")
    private String phone;
   @DecimalMin(value = "0",message = "Niepoprawna ocena")
   @DecimalMax(value = "5",message = "Niepoprawna ocena")
    private Double rate;
    private Long rateAmount;
    private String gender;
    private String role;
    @Basic(fetch = FetchType.LAZY)
    private byte[] photo;


    public GalUser() {
    }

    public GalUser(String password, String firstName, String lastName, String email, String phone, String gender, Date birthDate, String address) {

        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.birthDate = birthDate;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
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
