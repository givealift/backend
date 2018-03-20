package com.agh.givealift.service;


import com.agh.givealift.model.entity.Route;
import com.agh.givealift.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {
    @Autowired
    private RouteRepository routeRepository;

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
}
