package com.agh.givealift.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class GalUser {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long galUserId;
    private String login;
    private String password;

    public GalUser(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public GalUser() {
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
}
