package com.agh.givealift.model.entity;

import com.agh.givealift.Configuration;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Route {
    private Long routeId;
    private Long ownerId;
    private Localization from;
    private Localization to;
    private Date departureTime;
    private int numberOfSeats;
    private int numberOfOccupiedSeats;
    private Double price;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }


    @ManyToOne(cascade = CascadeType.ALL)
    public Localization getFrom() {
        return from;
    }

    public void setFrom(Localization from) {
        this.from = from;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Localization getTo() {
        return to;
    }

    public void setTo(Localization to) {
        this.to = to;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Configuration.DATA_PATTERN)
    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getNumberOfOccupiedSeats() {
        return numberOfOccupiedSeats;
    }

    public void setNumberOfOccupiedSeats(int numberOfOccupiedSeats) {
        this.numberOfOccupiedSeats = numberOfOccupiedSeats;
    }
}
