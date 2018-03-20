package com.agh.givealift.controller;

import com.agh.givealift.model.entity.Route;
import com.agh.givealift.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


@Controller
@RequestMapping("/route")
public class RouteController {
    @Autowired
    RouteService routeService;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ResponseEntity<List<Route>> getAll() {
        return new ResponseEntity<>(routeService.getAll(), HttpStatus.OK);
    }

    //TODO delete
    @RequestMapping(value = "/gen", method = RequestMethod.GET)
    public ResponseEntity<?> gen() {
        routeService.generate();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
