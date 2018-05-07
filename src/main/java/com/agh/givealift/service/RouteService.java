package com.agh.givealift.service;


import com.agh.givealift.Configuration;
import com.agh.givealift.model.entity.City;
import com.agh.givealift.model.entity.Localization;
import com.agh.givealift.model.entity.Route;
import com.agh.givealift.repository.RouteRepository;
import com.agh.givealift.util.UnknownCityException;
import com.stefanik.cod.controller.COD;
import com.stefanik.cod.controller.CODFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RouteService {
    private final RouteRepository routeRepository;
    private final CityService cityService;
    private final COD cod = CODFactory.get();

    @Autowired
    public RouteService(RouteRepository routeRepository, CityService cityService) {
        this.routeRepository = routeRepository;
        this.cityService = cityService;
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

    public List<Route> search(Long from, Long to, Date date) {
        Instant beforeInstant = date.toInstant().minus(Duration.ofHours(Configuration.SEARCH_BEFORE_HOURS));
        Instant afterInstant = date.toInstant().plus(Duration.ofHours(Configuration.SEARCH_AFTER_HOURS));
        cod.i("ROUTE search", from, to, date.toString(), Date.from(beforeInstant).toString(), Date.from(afterInstant).toString());
        List<Route> result = routeRepository.findByDepartureTimeBetweenAndFromCityCityIdAndToCityCityId(
                Date.from(beforeInstant),
                Date.from(afterInstant),
                from, to);

        cod.i("ROUTE search result", result);
        return result;

    }

    public Optional<Route> get(long id) {
        return Optional.ofNullable(routeRepository.findByRouteId(id));
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
        if (route.getDepartureTime() != null) {
            nullableRoute.setDepartureTime(route.getDepartureTime());
        }
        if (route.getNumberOfOccupiedSeats() != null) {
            nullableRoute.setNumberOfOccupiedSeats(route.getNumberOfOccupiedSeats());
        }
        if (route.getNumberOfSeats() != null) {
            nullableRoute.setNumberOfSeats(route.getNumberOfSeats());
        }
        if (route.getPrice() != null) {
            nullableRoute.setPrice(route.getPrice());
        }
        modifyLocalization(route.getFrom(), nullableRoute.getFrom());
        modifyLocalization(route.getTo(), nullableRoute.getTo());
    }

    private void modifyLocalization(Localization newFrom, Localization from) throws UnknownCityException {
        if (newFrom != null) {
            if (newFrom.getBuildingNumber() != null) {
                from.setBuildingNumber(newFrom.getBuildingNumber());
            }
            if (newFrom.getStreet() != null) {
                from.setStreet(newFrom.getStreet());
            }
            if (newFrom.getCity() != null && newFrom.getCity().getCityId() != null) {
                Optional<City> newCity = cityService.get(newFrom.getCity().getCityId());
                if (newCity.isPresent()) {
                    from.setCity(newCity.get());
                } else {
                    throw new UnknownCityException();
                }
            }
        }
    }
}
