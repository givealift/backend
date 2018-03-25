package com.agh.givealift.controller;

import com.agh.givealift.model.entity.City;
import com.agh.givealift.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


@Controller
@RequestMapping("/city")
public class CityController {
    @Autowired
    CityService cityService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<List<City>> list() {
        return new ResponseEntity<>(cityService.list(), HttpStatus.OK);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody List<City> cities) {
        cityService.saveAll(cities);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/search/{name}", method = RequestMethod.GET)
    public ResponseEntity<?> search(@PathVariable("name") String name) {

        return new ResponseEntity<>(cityService.search(name),HttpStatus.OK);
    }

    @RequestMapping(value = "/generate", method = RequestMethod.GET)
    public ResponseEntity<?> generate() {
        cityService.generate();
        return new ResponseEntity<>(cityService.list(),HttpStatus.OK);
    }

}
