package com.agh.givealift.model.response;

import com.agh.givealift.model.entity.City;

import java.util.Date;

public class SubscriptionResponse {
    private String subscriber;
    private String email;
    private City from;
    private City to;
    private Date date;
    private Long routeId;

    public SubscriptionResponse() {
    }

    public String getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(String subscriber) {
        this.subscriber = subscriber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public City getFrom() {
        return from;
    }

    public void setFrom(City from) {
        this.from = from;
    }

    public City getTo() {
        return to;
    }

    public void setTo(City to) {
        this.to = to;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }
}
