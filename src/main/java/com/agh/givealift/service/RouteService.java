package com.agh.givealift.service;


import com.agh.givealift.Configuration;
import com.agh.givealift.model.entity.City;
import com.agh.givealift.model.entity.Route;
import com.agh.givealift.repository.CityRepository;
import com.agh.givealift.repository.RouteRepository;
import com.stefanik.cod.controller.COD;
import com.stefanik.cod.controller.CODFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RouteService {
    private final RouteRepository routeRepository;
    private final CityService cityService;
    private final COD cod = CODFactory.get();

    @Autowired
    public RouteService(RouteRepository routeRepository, CityService cityService) {
        this.routeRepository = routeRepository;
        this.cityService = cityService;
    }


    public List<Route> getAll() {
        return routeRepository.findAll();
    }

    public void add(Route route) {
        City fromCity = cityService.getOrCreate(route.getFrom().getCity());
        City toCity = cityService.getOrCreate(route.getTo().getCity());
        route.getFrom().setCity(fromCity);
        route.getTo().setCity(toCity);
        cod.i("ADD ROUTE: ", route);
        routeRepository.save(route);
    }

    public List<Route> search(Long from, Long to, Date date) {
        Instant beforeInstant = date.toInstant().minus(Duration.ofHours(Configuration.SEARCH_BEFORE_HOURS));
        Instant afterInstant = date.toInstant().plus(Duration.ofHours(Configuration.SEARCH_AFTER_HOURS));
        cod.i("ROUTE search", from, to, date.toString(), Date.from(beforeInstant).toString(), Date.from(afterInstant).toString());
        List<Route> result = routeRepository.findByDepartureTimeBetweenAndFromCityCityIdAndToCityCityId(
                Date.from(beforeInstant),
                Date.from(afterInstant),
                from, to);

        cod.i("ROUTE search result", result);
        return result;

    }
}
