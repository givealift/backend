package com.agh.givealift.service;


import com.agh.givealift.model.entity.City;
import com.agh.givealift.model.entity.Route;
import com.agh.givealift.repository.CityRepository;
import com.agh.givealift.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RouteService {
    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private CityService cityService;

    public List<Route> getAll() {
        return routeRepository.findAll();
    }


    //TODO delete
    public void generate() {
        for (int i = 0; i < 3; i++) {
            Route r1 = new Route();
            routeRepository.save(r1);
        }
    }

    public void add(Route route) {
        City fromCity = cityService.getOrCreate(route.getFrom().getCity());
        City toCity = cityService.getOrCreate(route.getTo().getCity());
        route.getFrom().setCity(fromCity);
        route.getTo().setCity(toCity);

        routeRepository.save(route);
    }
}
