package com.agh.givealift.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Service
public class Init {
    private final CityService cityService;
    private final RouteService routeService;
    private final UserService userService;


    @Autowired
    public Init(CityService cityService, RouteService routeService, UserService userService) {
        this.cityService = cityService;
        this.routeService = routeService;
        this.userService = userService;
    }


    @Transactional
    @PostConstruct
    public void init() {
        cityService.generate(400);
    }
}
