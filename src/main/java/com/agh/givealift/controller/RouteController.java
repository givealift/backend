package com.agh.givealift.controller;

import com.agh.givealift.Configuration;
import com.agh.givealift.model.entity.Localization;
import com.agh.givealift.model.entity.Route;
import com.agh.givealift.model.request.NewPassengerRequest;
import com.agh.givealift.model.response.PushNotificationResponse;
import com.agh.givealift.model.response.RouteResponse;
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

import java.time.Duration;
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

        //WTF SPRING?!
        route.getFrom().setDate(Date.from(route.getFrom().getDate().toInstant().minus(Duration.ofHours(Configuration.HOURS_DIFFERENCE))));
        route.getTo().setDate(Date.from(route.getTo().getDate().toInstant().minus(Duration.ofHours(Configuration.HOURS_DIFFERENCE))));
        for (Localization s : route.getStops()) {
            s.setDate(Date.from(s.getDate().toInstant().minus(Duration.ofHours(Configuration.HOURS_DIFFERENCE))));
        }

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
    public ResponseEntity<RouteResponse> get(@PathVariable("id") long id) {
        Optional<RouteResponse> optionalRoute = routeService.get(id);
        return optionalRoute
                .map(route -> new ResponseEntity<>(route, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_GATEWAY));
    }


    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ResponseEntity<List<RouteResponse>> search(
            @RequestParam Long from,
            @RequestParam Long to,
            @RequestParam @DateTimeFormat(pattern = Configuration.DATE_SEARCH_PATTERN) Date date
    ) {
//        date = Date.from(date.toInstant().minus(Duration.ofHours(Configuration.HOURS_DIFFERENCE)));
        return new ResponseEntity<>(routeService.search(from, to, date), HttpStatus.OK);
    }


    @RequestMapping(value = "/{routeId}/passenger", method = RequestMethod.POST)
    public ResponseEntity<List<Route>> addPassenger(
            @PathVariable("routeId") long routeId,
            @RequestBody NewPassengerRequest newPassenger,
            UriComponentsBuilder ucBuilder
    ) {

        Optional<Route> route = routeService.addPassenger(routeId, newPassenger);
        if (route.isPresent()) {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/api/route/{id}").buildAndExpand(route.get().getRouteId()).toUri());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @Deprecated
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<Route>> list() {
        return new ResponseEntity<>(routeService.getAll(), HttpStatus.OK);
    }

//    @RequestMapping(value = "/testBot", method = RequestMethod.POST)
//    public ResponseEntity<Route> testBot(
//            @RequestBody List<SubscriptionResponse> sr
//    ) {
//        cod.i(sr);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }


//    @RequestMapping(value = "/testWeb", method = RequestMethod.POST)
//    public ResponseEntity<Route> testWeb(
//            @RequestBody List<PushNotificationResponse> request,
//            @RequestHeader HttpHeaders headers
//    ) {
//        cod.i(request, headers.get("Authorization").get(0));
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @RequestMapping(value = "/testMobile", method = RequestMethod.POST)
//    public ResponseEntity<Route> testMobile(
//            @RequestBody List<PushNotificationResponse> request,
//            @RequestHeader HttpHeaders headers
//    ) {
//        cod.i(request, headers.get("Authorization").get(0));
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

}
