package com.agh.givealift.model.entity;

import com.agh.givealift.model.enums.NotificationType;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Subscription {
    private Long subscriptionId;
    private String subscriber;
    private String email;
    private City from;
    private City to;
    private Date date;
    private Long userId;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    public Subscription() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Long subscriptionId) {
        this.subscriptionId = subscriptionId;
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

    @ManyToOne(cascade = CascadeType.MERGE)
    public City getFrom() {
        return from;
    }

    public void setFrom(City from) {
        this.from = from;
    }

    @ManyToOne(cascade = CascadeType.MERGE)
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

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
