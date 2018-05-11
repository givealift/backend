package com.agh.givealift.service.implementation;


import com.agh.givealift.Configuration;
import com.agh.givealift.model.builder.RouteResponseBuilder;
import com.agh.givealift.model.entity.City;
import com.agh.givealift.model.entity.Localization;
import com.agh.givealift.model.entity.Route;
import com.agh.givealift.model.response.RouteResponse;
import com.agh.givealift.repository.RouteRepository;
import com.agh.givealift.service.CityService;
import com.agh.givealift.service.RouteService;
import com.agh.givealift.service.UserService;
import com.agh.givealift.util.UnknownCityException;
import com.stefanik.cod.controller.COD;
import com.stefanik.cod.controller.CODFactory;
import com.stefanik.cod.controller.CODGlobal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RouteServiceImpl implements RouteService {
    private static final COD cod = CODFactory.get();
    private final RouteRepository routeRepository;
    private final CityService cityService;
    private final UserService userService;

    @Autowired
    public RouteServiceImpl(RouteRepository routeRepository, CityService cityService, UserService userService) {
        this.routeRepository = routeRepository;
        this.cityService = cityService;
        this.userService = userService;
        CODGlobal.setImmersionLevel(4);
    }

    public List<Route> getAll() {
        return routeRepository.findAll();
    }

    public Optional<Route> add(Route route) {
        Optional<City> fromCity = cityService.get(route.getFrom().getCity().getCityId());
        Optional<City> toCity = cityService.get(route.getTo().getCity().getCityId());

        if (fromCity.isPresent() && toCity.isPresent()) {
            route.getFrom().setCity(fromCity.get());
            route.getTo().setCity(toCity.get());

            route = routeRepository.save(route);
            cod.i("ADDED ROUTE: ", route);
            return Optional.of(route);
        }
        return Optional.empty();
    }

    public List<Route> userRoute(long id, Pageable pageable) {
        return routeRepository.findByOwnerId(id, pageable);
    }

    public List<RouteResponse> search(Long from, Long to, Date date) {
        Instant beforeInstant = date.toInstant().minus(Duration.ofHours(Configuration.SEARCH_BEFORE_HOURS));
        Instant afterInstant = date.toInstant().plus(Duration.ofHours(Configuration.SEARCH_AFTER_HOURS));
        cod.e("ROUTE search", from, to, date.toString(), Date.from(beforeInstant).toString(), Date.from(afterInstant).toString());
        List<Route> result = routeRepository.findRoutes(
                Date.from(beforeInstant),
                Date.from(afterInstant),
                from,
                to
        );

        cod.i("ROUTE search result", result);

        return result.stream()
                .map(r -> new RouteResponseBuilder(r).withGalUser(userService.getUserPublicInfo(r.getOwnerId())).build())
                .collect(Collectors.toList());
//        return null;
    }

    public Optional<RouteResponse> get(long id) {
        return Optional.ofNullable(routeRepository.findByRouteId(id))
                .map(r -> new RouteResponseBuilder(r).withGalUser(userService.getUserPublicInfo(r.getOwnerId())).build());

//        return null;
    }

    public Optional<Route> update(long id, Route route) {
        Route nullableRoute = routeRepository.findByRouteId(id);
        if (nullableRoute != null) {
            try {
                modifyRoute(route, nullableRoute);
                cod.i("UPDATED ROUTE: ", nullableRoute);

                return Optional.of(routeRepository.save(nullableRoute));
            } catch (UnknownCityException e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

    private void modifyRoute(Route route, Route nullableRoute) throws UnknownCityException {

        if (route.getNumberOfOccupiedSeats() != null) {
            nullableRoute.setNumberOfOccupiedSeats(route.getNumberOfOccupiedSeats());
        }
        if (route.getNumberOfSeats() != null) {
            nullableRoute.setNumberOfSeats(route.getNumberOfSeats());
        }
        if (route.getPrice() != null) {
            nullableRoute.setPrice(route.getPrice());
        }
        if (route.getStops() != null) {
            nullableRoute.setStops(route.getStops());
        }
        modifyLocalization(route.getFrom(), nullableRoute.getFrom());
        modifyLocalization(route.getTo(), nullableRoute.getTo());
    }

    private void modifyLocalization(Localization newLocalization, Localization localization) throws UnknownCityException {
        if (newLocalization != null) {
            if (newLocalization.getPlaceOfMeeting() != null) {
                localization.setPlaceOfMeeting(newLocalization.getPlaceOfMeeting());
            }
            if (newLocalization.getCity() != null && newLocalization.getCity().getCityId() != null) {
                Optional<City> newCity = cityService.get(newLocalization.getCity().getCityId());
                if (newCity.isPresent()) {
                    localization.setCity(newCity.get());
                } else {
                    throw new UnknownCityException();
                }
            }
            if (newLocalization.getDate() != null) {
                localization.setDate(newLocalization.getDate());
            }
        }
    }

    public void removeAll() {
        routeRepository.deleteAll();
    }


    public Integer countUserRoute(long id) {
        return routeRepository.countByOwnerId(id);
    }
}
