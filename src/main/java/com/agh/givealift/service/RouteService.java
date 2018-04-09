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
    private final RouteRepository routeRepository;
    private final CityService cityService;

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

        routeRepository.save(route);
    }
}
