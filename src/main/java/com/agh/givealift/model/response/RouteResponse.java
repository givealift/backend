package com.agh.givealift.model.response;

import com.agh.givealift.model.entity.GalUser;
import com.agh.givealift.model.entity.Localization;
import com.agh.givealift.model.entity.Route;

import java.util.List;

public class RouteResponse {
    private Long routeId;
    private GalUserPublicResponse galUserPublicResponse;
    private Localization from;
    private List<Localization> stops;
    private Localization to;
    private Integer numberOfSeats;
    private Integer numberOfOccupiedSeats;
    private Double price;

    public RouteResponse() {
    }

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public GalUserPublicResponse getGalUserPublicResponse() {
        return galUserPublicResponse;
    }

    public void setGalUserPublicResponse(GalUserPublicResponse galUserPublicResponse) {
        this.galUserPublicResponse = galUserPublicResponse;
    }

    public Localization getFrom() {
        return from;
    }

    public void setFrom(Localization from) {
        this.from = from;
    }

    public List<Localization> getStops() {
        return stops;
    }

    public void setStops(List<Localization> stops) {
        this.stops = stops;
    }

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

    public Integer getNumberOfOccupiedSeats() {
        return numberOfOccupiedSeats;
    }

    public void setNumberOfOccupiedSeats(Integer numberOfOccupiedSeats) {
        this.numberOfOccupiedSeats = numberOfOccupiedSeats;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
