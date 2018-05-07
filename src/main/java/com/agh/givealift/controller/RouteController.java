package com.agh.givealift.controller;

import com.agh.givealift.Configuration;
import com.agh.givealift.model.entity.Route;
import com.agh.givealift.service.RouteService;
import com.stefanik.cod.controller.COD;
import com.stefanik.cod.controller.CODFactory;
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
import java.util.Optional;


@Controller
@RequestMapping("/api/route")
public class RouteController {
    private static final COD cod = CODFactory.get();
    private final RouteService routeService;

    @Autowired
    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<List<Route>> add(@RequestBody Route route, UriComponentsBuilder ucBuilder) {
        //TODO  VALIDATION
        cod.i(route);
        if (routeService.add(route).isPresent()) {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/api/route/{id}").buildAndExpand(route.getRouteId()).toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<List<Route>> update(@RequestBody Route route, @PathVariable("id") long id) {
        //TODO  VALIDATION

        if (routeService.update(id, route).isPresent()) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Route> get(@PathVariable("id") long id) {
        Optional<Route> optionalRoute = routeService.get(id);
        return optionalRoute
                .map(route -> new ResponseEntity<>(route, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_GATEWAY));
    }


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

}
