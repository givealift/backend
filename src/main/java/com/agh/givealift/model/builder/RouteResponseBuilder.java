package com.agh.givealift.model.builder;

import com.agh.givealift.model.entity.GalUser;
import com.agh.givealift.model.entity.Route;
import com.agh.givealift.model.response.GalUserPublicResponse;
import com.agh.givealift.model.response.RouteResponse;

public class RouteResponseBuilder {
    private GalUser galUser;
    private Route route;

    public RouteResponseBuilder(Route route) {
        this.route = route;
    }

    public RouteResponseBuilder withGalUser(GalUser galUser) {
        this.galUser = galUser;
        return this;
    }

    public RouteResponse build() {
        return getRouteResponse(getGalUserPublicResponse());
    }

    private RouteResponse getRouteResponse(GalUserPublicResponse user) {
        RouteResponse rr = new RouteResponse();
        rr.setRouteId(route.getRouteId());
        rr.setGalUserPublicResponse(user);
        rr.setPrice(route.getPrice());
        rr.setNumberOfSeats(route.getNumberOfSeats());
        rr.setNumberOfOccupiedSeats(route.getNumberOfOccupiedSeats());
        rr.setFrom(route.getFrom());
        rr.setStops(route.getStops());
        rr.setTo(route.getTo());
        return rr;
    }

    private GalUserPublicResponse getGalUserPublicResponse() {
        GalUserPublicResponse user = new GalUserPublicResponse(galUser);
//        user.setEmail(galUser.getEmail());
//        user.setFirstName(galUser.getFirstName());
//        user.setLastName(galUser.getLastName());
//        user.setGender(galUser.getGender());
//        user.setPhone(galUser.getPhone());
        return user;
    }
}
