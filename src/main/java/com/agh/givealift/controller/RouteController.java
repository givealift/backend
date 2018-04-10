package com.agh.givealift.controller;

import com.agh.givealift.Configuration;
import com.agh.givealift.model.entity.Route;
import com.agh.givealift.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/route")
public class RouteController {
    private final RouteService routeService;

    @Autowired
    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<List<Route>> list() {
        return new ResponseEntity<>(routeService.getAll(), HttpStatus.OK);
    }


    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<List<Route>> searchIds(@RequestParam Long from,
                                                 @RequestParam Long to,
                                                 @RequestParam @DateTimeFormat(pattern =Configuration.DATE_SEARCH_PATTERN) Date date) {
        return new ResponseEntity<>(routeService.search(from, to, date), HttpStatus.OK);
    }

//    @RequestMapping(value = "/search", method = RequestMethod.GET)
//    public ResponseEntity<List<Route>> search(@RequestParam String from,
//                                              @RequestParam String to,
//                                              @RequestParam Date date) {
//        return new ResponseEntity<>(routeService.search(from, to, date), HttpStatus.OK);
//    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<List<Route>> add(@RequestBody Route route) {
        routeService.add(route);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
