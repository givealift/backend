package com.agh.givealift.controller;

import com.agh.givealift.model.entity.City;
import com.agh.givealift.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/api/city")
public class CityController {
    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }


    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<?> search(
            @RequestParam String search,
            @RequestParam int limit
    ) {
        return new ResponseEntity<>(cityService.search(search, limit), HttpStatus.OK);
    }


    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<City>> list() {
        return new ResponseEntity<>(cityService.list(), HttpStatus.OK);
    }


    @RequestMapping(value = "/generate", method = RequestMethod.POST)
    public ResponseEntity<?> generate(@RequestParam(value = "limit", required = false, defaultValue = "100") int limit) {
        cityService.generate(limit);
        return new ResponseEntity<>(cityService.list(), HttpStatus.OK);
    }

    @Deprecated
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody List<City> cities) {
        cityService.saveAll(cities);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
