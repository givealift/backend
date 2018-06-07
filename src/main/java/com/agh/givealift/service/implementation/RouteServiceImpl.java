package com.agh.givealift.service.implementation;


import com.agh.givealift.configuration.Configuration;
import com.agh.givealift.model.builder.RouteResponseBuilder;
import com.agh.givealift.model.entity.City;
import com.agh.givealift.model.entity.Localization;
import com.agh.givealift.model.entity.Route;
import com.agh.givealift.model.request.NewPassengerRequest;
import com.agh.givealift.model.response.RouteResponse;
import com.agh.givealift.repository.RouteRepository;
import com.agh.givealift.service.CityService;
import com.agh.givealift.service.RouteService;
import com.agh.givealift.service.SubscriptionService;
import com.agh.givealift.service.UserService;
import com.agh.givealift.util.UnknownCityException;
import com.stefanik.cod.controller.COD;
import com.stefanik.cod.controller.CODFactory;
import com.stefanik.cod.controller.CODGlobal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RouteServiceImpl implements RouteService {
    private static final COD cod = CODFactory.get();
    private final RouteRepository routeRepository;
    private final CityService cityService;
    private final UserService userService;
    private final SubscriptionService subscriptionService;

    @Autowired
    public RouteServiceImpl(
            RouteRepository routeRepository,
            CityService cityService,
            UserService userService,
            SubscriptionService subscriptionService
    ) {
        this.routeRepository = routeRepository;
        this.cityService = cityService;
        this.userService = userService;
        this.subscriptionService = subscriptionService;
        CODGlobal.setImmersionLevel(3);
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
            route.setPassengers(new ArrayList<>());

            route = routeRepository.save(route);
            cod.i("ADDED ROUTE: ", route);
            subscriptionService.checkAndNotify(route);
            return Optional.of(route);
        }
        return Optional.empty();
    }

    public List<Route> userRoute(long id, Pageable pageable) {
        return routeRepository.findByOwnerId(id, pageable);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<RouteResponse> search(Long from, Long to, Date date) {
        List<Route> result = Collections.emptyList();
        try {
            if (date.compareTo(new SimpleDateFormat(Configuration.DATE_SEARCH_PATTERN).parse("0001-01-01")) != 0) {
                Date fromDate = Date.from(date.toInstant().minus(Duration.ofSeconds(Configuration.SEARCH_BEFORE_SEC)));
                Date toDate = Date.from(date.toInstant().plus(Duration.ofSeconds(Configuration.SEARCH_AFTER_SEC)));
                cod.i("ROUTE search: " + "\n\t| cFrom: " + from + "\n\t| cTo: " + to + "\n\t| inputDate: " + new SimpleDateFormat(Configuration.DATA_PATTERN).format(date) + "\n\t| fromDate: " + new SimpleDateFormat(Configuration.DATA_PATTERN).format(fromDate) + "\n\t| toDate: " + new SimpleDateFormat(Configuration.DATA_PATTERN).format(toDate) + "\n\t");
                result = routeRepository.findRoutesDateFromTo(fromDate, toDate, from, to);
            } else {
                cod.i("ROUTE search: " + "\n\t| cFrom: " + from + "\n\t| cTo: " + to + "\n\t| date: null" + "\n\t");
                result = routeRepository.findRoutesFromTo(from, to);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cod.i("ROUTE search result", result);

        return result.stream()
                .map(r -> new RouteResponseBuilder(r).withGalUser(userService.getUserPublicInfo(r.getOwnerId())).build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<List<RouteResponse>> searchInterchanges(Long from, Long to, Date date) {
        List<RouteResponse> wi = search(from, to, date);
        if (!wi.isEmpty()) {
            return wi.stream().map(Arrays::asList).collect(Collectors.toList());
        }

        Date fromDate = Date.from(date.toInstant().minus(Duration.ofSeconds(Configuration.SEARCH_BEFORE_SEC)));
        Date toDate = Date.from(date.toInstant().plus(Duration.ofSeconds(Configuration.SEARCH_AFTER_SEC)));
        cod.i("ROUTE search: " + "\n\t| cFrom: " + from + "\n\t| fromDate: " + new SimpleDateFormat(Configuration.DATA_PATTERN).format(fromDate) + "\n\t| toDate: " + new SimpleDateFormat(Configuration.DATA_PATTERN).format(toDate) + "\n\t");
        List<Route> firstParts = routeRepository.findRoutesDateFrom(fromDate, toDate, from);


        List<List<RouteResponse>> result = new ArrayList<>();
        CODGlobal.setImmersionLevel(5);
        cod.i("firstParts", firstParts);
        for (Route fp : firstParts) {
            for (Localization l : getPotentialInterchange(from, fp)) {
                cod.i("LOCALIZATIONS", l);
                Date toInterchangeDate = Date.from(l.getDate().toInstant().plus(Duration.ofSeconds(Configuration.SEARCH_INTERCHANGE_AFTER_SEC)));
                cod.i("INTERCHANGE search: " + "\n\t| cFrom: " + l.getCity().getCityId() + "\n\t| cTo: " + to + "\n\t| fromDate: " + new SimpleDateFormat(Configuration.DATA_PATTERN).format(l.getDate()) + "\n\t| toDate: " + new SimpleDateFormat(Configuration.DATA_PATTERN).format(toInterchangeDate) + "\n\t");

                List<Route> secondPart = routeRepository.findRoutesDateFromTo(l.getDate(), toInterchangeDate, l.getCity().getCityId(), to);
                cod.i("secondPart: ", secondPart);
                for (Route sp : secondPart) {
                    result.add(
                            Arrays.asList(
                                    new RouteResponseBuilder(fp).withGalUser(userService.getUserPublicInfo(fp.getOwnerId())).build(),
                                    new RouteResponseBuilder(sp).withGalUser(userService.getUserPublicInfo(sp.getOwnerId())).build()
                            )
                    );
                }
            }
        }
        return result;
    }


    private List<Localization> getPotentialInterchange(Long from, Route r) {
        List<Localization> potentialInterchange = new ArrayList<>();
        boolean fromFlag = false;
        for (Localization l : getRouteCities(r)) {
            if (fromFlag) {
                potentialInterchange.add(l);
            }
            if (l.getCity().getCityId().equals(from)) {
                fromFlag = true;
            }
        }
        return potentialInterchange;
    }

    private List<Localization> getRouteCities(Route route) {
        List<Localization> cityIds = new ArrayList<>();
        cityIds.add(route.getFrom());
        cityIds.addAll(route.getStops());
        cityIds.add(route.getTo());
        return cityIds;
    }


    public Optional<RouteResponse> get(long id) {
        return Optional.ofNullable(routeRepository.findByRouteId(id))
                .map(r -> new RouteResponseBuilder(r).withGalUser(userService.getUserPublicInfo(r.getOwnerId())).build());
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

    @Override
    public Optional<Route> addPassenger(long routeId, NewPassengerRequest newPassenger) {
        Route nullableRoute = routeRepository.findByRouteId(routeId);

        if (nullableRoute != null && newPassenger != null && userService.getUserPublicInfo(newPassenger.getPassengerId()) != null) {
            if (nullableRoute.getNumberOfSeats() > nullableRoute.getNumberOfOccupiedSeats()) {
                nullableRoute.setNumberOfOccupiedSeats(nullableRoute.getNumberOfOccupiedSeats() + 1);
                nullableRoute.getPassengers().add(newPassenger.getPassengerId());
                Route route = routeRepository.save(nullableRoute);
                cod.i("ADD PASSENGER", route);
                return Optional.of(route);
            }
        }
        return Optional.empty();
    }

    @Override
    public void removePassenger(long routeId, long passengerId) {
        Route nullableRoute = routeRepository.findByRouteId(routeId);

        if (nullableRoute != null) {
            nullableRoute.setPassengers(
                    nullableRoute.getPassengers().stream()
                            .filter(p -> !p.equals(passengerId))
                            .collect(Collectors.toList())
            );
            nullableRoute.setNumberOfOccupiedSeats(nullableRoute.getNumberOfOccupiedSeats()-1);
            Route route = routeRepository.save(nullableRoute);
            cod.i("REMOVE PASSENGER", route);
        }
    }

    @Override
    public List<RouteResponse> getUserRides(long userId) {
        return routeRepository.findAll().stream()
                .filter(r -> r.getPassengers().stream().anyMatch(p -> p.equals(userId)))
                .map(r -> new RouteResponseBuilder(r).withGalUser(userService.getUserPublicInfo(r.getOwnerId())).build())
                .collect(Collectors.toList());
    }


}
