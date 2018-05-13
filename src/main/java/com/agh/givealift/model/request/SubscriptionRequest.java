package com.agh.givealift.model.request;

import java.util.Date;

public class SubscriptionRequest {
    private String subscriber;
    private String email;
    private Long fromCityId;
    private Long toCityId;
    private Date date;

    public SubscriptionRequest() {
    }

    public String getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(String subscriber) {
        this.subscriber = subscriber;
    }

    public Long getFromCityId() {
        return fromCityId;
    }

    public void setFromCityId(Long fromCityId) {
        this.fromCityId = fromCityId;
    }

    public Long getToCityId() {
        return toCityId;
    }

    public void setToCityId(Long toCityId) {
        this.toCityId = toCityId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
