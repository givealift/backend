package com.agh.givealift.model.response;

public class AuthenticationResponse {
    private long userId;
    private String token;

    public AuthenticationResponse(long id, String token) {
        this.userId = id;
        this.token = token;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
