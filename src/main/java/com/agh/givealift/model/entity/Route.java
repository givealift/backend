package com.agh.givealift.model.entity;

import javax.persistence.*;

@Entity
public class Route {
    private Long routeId;
    private Localization from;
    private Localization to;

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
}
