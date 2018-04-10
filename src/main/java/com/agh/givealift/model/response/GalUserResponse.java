package com.agh.givealift.model.response;

import com.agh.givealift.model.entity.GalUser;

public class GalUserResponse {
    private Long galUserId;
    private String login;
    private String facebookId;

    public GalUserResponse(GalUser galUser) {
        this.galUserId = galUser.getGalUserId();
        this.login = galUser.getLogin();
        this.facebookId = galUser.getFacebookId();
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

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }
}
