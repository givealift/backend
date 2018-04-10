package com.agh.givealift.model.response;

import com.agh.givealift.model.AuthToken;
import com.agh.givealift.model.entity.GalUser;

public class AuthenticationResponse {
    private GalUserResponse galUserResponse;
    private AuthToken authToken;

    public AuthenticationResponse(GalUser galUser, AuthToken authToken) {
        this.galUserResponse = new GalUserResponse(galUser);
        this.authToken = authToken;
    }

    public GalUserResponse getGalUserResponse() {
        return galUserResponse;
    }

    public void setGalUserResponse(GalUserResponse galUserResponse) {
        this.galUserResponse = galUserResponse;
    }

    public AuthToken getAuthToken() {
        return authToken;
    }

    public void setAuthToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
