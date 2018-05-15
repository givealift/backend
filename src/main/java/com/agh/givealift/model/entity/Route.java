package com.agh.givealift.model.entity;

import com.agh.givealift.Configuration;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Route {
    private Long routeId;
    private Long ownerId;
    private String description;
    private Localization from;
    private List<Localization> stops;
    private Localization to;
    private Integer numberOfSeats;
    private Integer numberOfOccupiedSeats;
    private Double price;
    private List<Long> passengers;

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

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
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

    public Integer getNumberOfOccupiedSeats() {
        return numberOfOccupiedSeats;
    }

    public void setNumberOfOccupiedSeats(Integer numberOfOccupiedSeats) {
        this.numberOfOccupiedSeats = numberOfOccupiedSeats;
    }

    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    public List<Localization> getStops() {
        return stops;
    }

    public void setStops(List<Localization> stops) {
        this.stops = stops;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ElementCollection
    public List<Long> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Long> passengers) {
        this.passengers = passengers;
    }
}
