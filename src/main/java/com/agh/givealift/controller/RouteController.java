package com.agh.givealift.controller;

import com.agh.givealift.Configuration;
import com.agh.givealift.model.entity.Route;
import com.agh.givealift.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/api/route")
public class RouteController {
    private final RouteService routeService;

    @Autowired
    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<List<Route>> add(@RequestBody Route route, UriComponentsBuilder ucBuilder) {
        //TODO  VALIDATION
        route = routeService.add(route);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/route/{id}").buildAndExpand(route.getRouteId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Route> get(@PathVariable("id") long id) {
        return new ResponseEntity<>(routeService.get(id), HttpStatus.OK);
    }
//
//    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
//    public ResponseEntity<Route> update(
//            @PathVariable("id") long id,
//            @RequestBody Route route,
//            UriComponentsBuilder ucBuilder
//    ) {
//        //TODO  VALIDATION
//        route = routeService.update(id, route);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(ucBuilder.path("/api/route/{id}").buildAndExpand(route.getRouteId()).toUri());
//        return new ResponseEntity<>(headers, HttpStatus.CREATED);
//    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<List<Route>> search(
            @RequestParam Long from,
            @RequestParam Long to,
            @RequestParam @DateTimeFormat(pattern = Configuration.DATE_SEARCH_PATTERN) Date date
    ) {
        return new ResponseEntity<>(routeService.search(from, to, date), HttpStatus.OK);
    }


    @Deprecated
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<Route>> list() {
        return new ResponseEntity<>(routeService.getAll(), HttpStatus.OK);
    }

}
