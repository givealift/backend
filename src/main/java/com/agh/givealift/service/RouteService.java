package com.agh.givealift.service;


import com.agh.givealift.model.entity.Route;
import com.agh.givealift.model.request.NewPassengerRequest;
import com.agh.givealift.model.response.RouteResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public interface RouteService {

    List<Route> getAll();

    Optional<Route> add(Route route);

    List<Route> userRoute(long id, Pageable pageable);

    List<RouteResponse> search(Long from, Long to, Date date);

    Optional<RouteResponse> get(long id);

    Optional<Route> update(long id, Route route);

    void removeAll();

    Integer countUserRoute(long id);

    Optional<Route> addPassenger(long routeId, NewPassengerRequest newPassenger);

    List<List<RouteResponse>> searchInterchanges(Long from, Long to, Date date);

    void removePassenger(long routeId, long passengerId);

    List<RouteResponse> getUserRides(long userId);

    void deleteRoute(long id);
}
