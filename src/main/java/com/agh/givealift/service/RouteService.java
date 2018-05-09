package com.agh.givealift.service;


import com.agh.givealift.Configuration;
import com.agh.givealift.model.builder.RouteResponseBuilder;
import com.agh.givealift.model.entity.City;
import com.agh.givealift.model.entity.Localization;
import com.agh.givealift.model.entity.Route;
import com.agh.givealift.model.response.RouteResponse;
import com.agh.givealift.repository.RouteRepository;
import com.agh.givealift.repository.UserRepository;
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
public interface RouteService {

    List<Route> getAll();

    Optional<Route> add(Route route);

    List<Route> userRoute(long id, Pageable pageable);

    List<RouteResponse> search(Long from, Long to, Date date);

    Optional<RouteResponse> get(long id);

    Optional<Route> update(long id, Route route);

    void removeAll();
}
